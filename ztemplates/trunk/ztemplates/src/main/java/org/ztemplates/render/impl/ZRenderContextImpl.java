/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * @author www.gerdziegler.de
 */
package org.ztemplates.render.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ztemplates.actions.util.ZReflectionUtil;
import org.ztemplates.render.ZCss;
import org.ztemplates.render.ZIRenderEngine;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderApplication;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.ZScriptDependency;
import org.ztemplates.render.script.ZCssExposed;
import org.ztemplates.render.script.ZICssProcessor;
import org.ztemplates.render.script.ZIJavaScriptProcessor;
import org.ztemplates.render.script.ZIScriptRepository;
import org.ztemplates.render.script.ZJavaScriptExposed;

public class ZRenderContextImpl implements ZIRenderContext {
    private final ZRenderApplication application;

    private final String contextPath;

    private final List<ZIRenderer> rendererCache = new ArrayList<ZIRenderer>();

    private int renderCnt = 0;

    private String scriptExposedBy = null;

    private final ZIJavaScriptProcessor javaScriptProcessor;

    private final ZICssProcessor cssProcessor;

    private final SortedSet<ZJavaScriptExposed> javaScriptExposed = new TreeSet<ZJavaScriptExposed>();

    private final SortedSet<ZCssExposed> cssExposed = new TreeSet<ZCssExposed>();

    public ZRenderContextImpl(ZRenderApplication application,
	    String contextPath, ZIJavaScriptProcessor javaScriptProcessor,
	    ZICssProcessor cssProcessor) {
	this.application = application;
	this.contextPath = contextPath;
	this.javaScriptProcessor = javaScriptProcessor;
	this.cssProcessor = cssProcessor;
    }

    public List<ZExposedMethod> getExposedMethods(Class clazz) throws Exception {
	return application.getExposedMethodRepository().getExposed(clazz);
    }

    public int getRenderCallCounter() {
	return renderCnt;
    }

    public void incRenderCallCounter() {
	renderCnt++;
    }

    public ZIScriptRepository getScriptRepository() {
	return application.getScriptRepository();
    }

    public ZExposedMethodRepository getExposedMethodRepository() {
	return application.getExposedMethodRepository();
    }

    public ZICssIdRepository getCssIdRepository() {
	return application.getCssIdRepository();
    }

    public ZIRenderer getRenderer(Class<? extends ZIRenderer> clazz)
	    throws Exception {
	for (ZIRenderer r : rendererCache) {
	    if (clazz.equals(r.getClass())) {
		return r;
	    }
	}
	ZIRenderer renderer = clazz.newInstance();
	renderer.init(application);
	rendererCache.add(renderer);
	return renderer;
    }

    public ZRenderApplication getApplication() {
	return application;
    }

    public String getContextPath() {
	return contextPath;
    }

    public String getScriptExposedBy() {
	return scriptExposedBy;
    }

    public void setScriptExposedBy(String scriptExposedBy) {
	this.scriptExposedBy = scriptExposedBy;
    }

    public void addJavaScriptExposed(ZJavaScriptExposed jse) {
	javaScriptExposed.add(jse);
    }

    public void addCssExposed(ZCssExposed csse) {
	cssExposed.add(csse);
    }

    public String computeHtmlScriptTags() throws Exception {
	String ret = javaScriptProcessor.computeHtmlTags(javaScriptExposed);
	ret += "\n" + cssProcessor.computeHtmlTags(cssExposed);
	return ret;
    }

    public void registerScripts(Object obj, Map<String, Object> exposed)
	    throws Exception {
	ZScript scriptAnn = ZReflectionUtil.getAnnotation(obj.getClass(),
		ZScript.class);
	if (scriptAnn == null) {
	    return;
	}

	ZScriptDependency dep = new ZScriptDependency();
	dep.add(scriptAnn);

	// runtime script references
	if (scriptAnn.property().length() > 0) {
	    Method getter = ZReflectionUtil.getGetter(obj.getClass(), scriptAnn
		    .property());
	    ZScriptDependency runtimeScripts = (ZScriptDependency) getter
		    .invoke(obj);
	    dep.add(runtimeScripts);
	}

	ZIScriptRepository scriptRepository = getScriptRepository();
	// compile-time script references
	for (ZCss css : dep.getCss()) {
	    String s = css.value();
	    int idx = scriptRepository.getCssIndex(css);
	    String val = exposed != null ? ZReplaceUtil.replace(s, exposed) : s;
	    addCssExposed(new ZCssExposed(idx, val, css.prefix(), css.merge()));
	}

	for (ZJavaScript js : dep.getJavaScript()) {
	    String s = js.value();
	    int idx = scriptRepository.getJavaScriptIndex(s);
	    String val = exposed != null ? ZReplaceUtil.replace(s, exposed) : s;
	    addJavaScriptExposed(new ZJavaScriptExposed(idx, js.prefix(), val,
		    js.standalone(), js.merge()));
	}
    }

    public ZIRenderEngine getRenderEngine(Object obj) throws Exception {
	return application.getRenderEngineRepository().getRenderEngine(obj,
		this);
    }

    public SortedSet<ZJavaScriptExposed> getJavaScriptExposed() {
	return javaScriptExposed;
    }

    public SortedSet<ZCssExposed> getCssExposed() {
	return cssExposed;
    }

}
