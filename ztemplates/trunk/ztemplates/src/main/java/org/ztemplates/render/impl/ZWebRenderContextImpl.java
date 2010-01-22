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
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;

import org.ztemplates.render.ZCss;
import org.ztemplates.render.ZIRenderedObject;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderApplication;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.ZScriptDependency;
import org.ztemplates.render.script.ZCssExposed;
import org.ztemplates.render.script.ZICssProcessor;
import org.ztemplates.render.script.ZIJavaScriptProcessor;
import org.ztemplates.render.script.ZIScriptRepository;
import org.ztemplates.render.script.ZJavaScriptExposed;

public class ZWebRenderContextImpl implements ZIWebRenderContext
{
  private final ZRenderApplication application;

  private final String contextPath;

  private int renderCnt = 0;

  private String scriptExposedBy = null;

  private final ZIJavaScriptProcessor javaScriptProcessor;

  private final ZICssProcessor cssProcessor;

  private final SortedSet<ZJavaScriptExposed> javaScriptExposed = new TreeSet<ZJavaScriptExposed>();

  private final SortedSet<ZCssExposed> cssExposed = new TreeSet<ZCssExposed>();

  private final ZRendererRepository rendererRepository;


  public ZWebRenderContextImpl(ZRenderApplication application, String contextPath, ZIJavaScriptProcessor javaScriptProcessor, ZICssProcessor cssProcessor)
  {
    this.application = application;
    this.contextPath = contextPath;
    this.javaScriptProcessor = javaScriptProcessor;
    this.cssProcessor = cssProcessor;
    this.rendererRepository = new ZRendererRepository(application.getApplicationContext(), application.getTemplateNameRepository());
  }


  public List<ZExposedMethod> getExposedMethods(Class clazz) throws Exception
  {
    return application.getExposedMethodRepository().getExposedMethods(clazz);
  }


  public int getRenderCallCounter()
  {
    return renderCnt;
  }


  public void incRenderCallCounter()
  {
    renderCnt++;
  }


  public ZIScriptRepository getScriptRepository()
  {
    return application.getScriptRepository();
  }


  //
  // public ZExposedMethodRepository getExposedMethodRepository()
  // {
  // return application.getExposedMethodRepository();
  // }

  public ZICssIdRepository getCssIdRepository()
  {
    return application.getCssIdRepository();
  }


  public ZIRenderer getRenderer(Class<? extends ZIRenderer> clazz) throws Exception
  {
    return rendererRepository.getRenderer(clazz);
  }


  public ZRenderApplication getApplication()
  {
    return application;
  }


  public String getContextPath()
  {
    return contextPath;
  }


  public String getScriptExposedBy()
  {
    return scriptExposedBy;
  }


  public void setScriptExposedBy(String scriptExposedBy)
  {
    this.scriptExposedBy = scriptExposedBy;
  }


  public void addJavaScriptExposed(ZJavaScriptExposed jse)
  {
    javaScriptExposed.add(jse);
  }


  public void addCssExposed(ZCssExposed csse)
  {
    cssExposed.add(csse);
  }


  public String computeHtmlScriptTags() throws Exception
  {
    String ret = javaScriptProcessor.computeHtmlTags(javaScriptExposed);
    ret += "\n" + cssProcessor.computeHtmlTags(cssExposed);
    return ret;
  }


  public void registerScripts(Object obj, Map<String, Object> exposed) throws Exception
  {
    if (obj instanceof ZIRenderedObject)
    {
      ZIRenderedObject ro = (ZIRenderedObject) obj;
      cssExposed.addAll(ro.getCssExposed());
      javaScriptExposed.addAll(ro.getJavaScriptExposed());
      return;
    }

    ZScript scriptAnn = obj.getClass().getAnnotation(ZScript.class);
    if (scriptAnn == null)
    {
      return;
    }

    ZScriptDependency dep = new ZScriptDependency();
    dep.add(scriptAnn);

    // runtime script references
    if (scriptAnn.property().length() > 0)
    {
      String propName = scriptAnn.property();
      ZScriptDependency runtimeScripts = (ZScriptDependency) callGetter(obj, propName);
      dep.add(runtimeScripts);
    }

    ZIScriptRepository scriptRepository = getScriptRepository();
    // compile-time script references
    for (ZCss css : dep.getCss())
    {
      String s = css.value();
      int idx = scriptRepository.getCssIndex(css);
      String val = exposed != null ? ZReplaceUtil.replace(s, exposed) : s;
      addCssExposed(new ZCssExposed(idx, val));
    }

    for (ZJavaScript js : dep.getJavaScript())
    {
      String s = js.value();
      int idx = scriptRepository.getJavaScriptIndex(s);
      String val = exposed != null ? ZReplaceUtil.replace(s, exposed) : s;
      addJavaScriptExposed(new ZJavaScriptExposed(idx, val, js.standalone(), js.merge()));
    }
  }


  public SortedSet<ZJavaScriptExposed> getJavaScriptExposed()
  {
    return javaScriptExposed;
  }


  public SortedSet<ZCssExposed> getCssExposed()
  {
    return cssExposed;
  }


  private static Object callGetter(Object obj, String name) throws Exception
  {
    int nameLen = name.length();
    StringBuffer sb = new StringBuffer(3 + nameLen);
    sb.append("get");
    sb.append(Character.toUpperCase(name.charAt(0)));
    sb.append(name.substring(1));
    String getterName = sb.toString();
    Method getter = obj.getClass().getMethod(getterName);
    Object ret = getter.invoke(obj);
    return ret;
  }


  public void beforeRender(Object obj, Map<String, Object> exposed) throws Exception
  {
    ZRenderer rendererAnnot = obj.getClass().getAnnotation(ZRenderer.class);

    incRenderCallCounter();

    // register scripts, needs exposed values
    registerScripts(obj, exposed);

    if (rendererAnnot != null)
    {
      if (rendererAnnot.cssId() && exposed.get("cssId") == null)
      {
        String cssId = getCssIdRepository().getCssId(obj.getClass());
        exposed.put("cssId", cssId);
      }

      if (rendererAnnot.contextPath() && exposed.get("contextPath") == null)
      {
        exposed.put("contextPath", getContextPath());
      }

      if (rendererAnnot.zscript() && exposed.get("zscript") == null)
      {
        if (getScriptExposedBy() != null)
        {
          throw new Exception("zscript can only be exposed once per request, but is exposed by \n" + getScriptExposedBy() + "\n" + computeZscriptExposedBy(obj));
        }
        setScriptExposedBy(computeZscriptExposedBy(obj));

        String zscript = computeHtmlScriptTags();
        exposed.put("zscript", zscript);
      }
    }
  }


  private static String computeZscriptExposedBy(Object obj)
  {
    return obj.getClass().getSimpleName() + "[" + obj + "]";
  }

}
