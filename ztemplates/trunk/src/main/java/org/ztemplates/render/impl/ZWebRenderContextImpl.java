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

import org.ztemplates.render.ZIRenderedObject;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.ZRenderApplication;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.ZScriptDependency;
import org.ztemplates.render.script.ZICssProcessor;
import org.ztemplates.render.script.ZIJavaScriptProcessor;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.script.css.ZICssPreprocessor;
import org.ztemplates.web.script.javascript.ZIJavaScriptGroupingStrategy;
import org.ztemplates.web.script.javascript.ZIJavaScriptPreprocessor;

public class ZWebRenderContextImpl implements ZIWebRenderContext
{
  private final ZRenderApplication application;

  private final String contextPath;

  private int renderCnt = 0;

  private String scriptExposedBy = null;

  private final ZIJavaScriptProcessor javaScriptProcessor;

  private final ZICssProcessor cssProcessor;

  private final List<List<String>> javaScriptExposed = new ArrayList<List<String>>();

  private final List<List<String>> cssExposed = new ArrayList<List<String>>();

  // private final SortedSet<ZCssExposed> cssExposed = new
  // TreeSet<ZCssExposed>();

  private final ZRendererRepository rendererRepository;


  public ZWebRenderContextImpl(ZRenderApplication application,
      String contextPath,
      ZIJavaScriptProcessor javaScriptProcessor,
      ZICssProcessor cssProcessor)
  {
    this.application = application;
    this.contextPath = contextPath;
    this.javaScriptProcessor = javaScriptProcessor;
    this.cssProcessor = cssProcessor;
    this.rendererRepository = new ZRendererRepository(
        application.getApplicationContext(),
        application.getTemplateNameRepository());
  }


  public List<ZIExposedValue> getExposedValues(Class clazz) throws Exception
  {
    return application.getExposedMethodRepository().getExposedValues(clazz);
  }


  public int getRenderCallCounter()
  {
    return renderCnt;
  }


  public void incRenderCallCounter()
  {
    renderCnt++;
  }


  public ZICssIdRepository getCssIdRepository()
  {
    return application.getCssIdRepository();
  }


  public ZIRenderer getRenderer(Class<? extends ZIRenderer> clazz)
      throws Exception
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


  private String computeHtmlJavaScriptTags(
      ZIJavaScriptPreprocessor javaScriptPreprocessor,
      ZIJavaScriptGroupingStrategy groupingStrategy) throws Exception
  {
    List<String> javaScript = mergeScripts(javaScriptExposed);
    String ret = javaScriptProcessor.computeHtmlTags(javaScript,
        javaScriptPreprocessor, groupingStrategy);
    return ret;
  }


  private String computeHtmlCssTags(ZICssPreprocessor cssPreprocessor) throws Exception
  {
    List<String> css = mergeScripts(cssExposed);
    String ret = cssProcessor.computeHtmlTags(css, cssPreprocessor);
    return ret;
  }


  private List<String> mergeScripts(List<List<String>> exposed)
  {
    List<String> ret = new ArrayList<String>();
    for (List<String> list : exposed)
    {
      for (String crt : list)
      {
        if (!ret.contains(crt))
        {
          ret.add(crt);
        }
      }
    }
    return ret;
  }


  public void registerScripts(Object obj, Map<String, Object> exposed)
      throws Exception
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
      ZScriptDependency runtimeScripts = (ZScriptDependency) ZWebRenderContextImpl
          .callGetter(obj, propName);
      dep.add(runtimeScripts);
    }

    // compile-time script references
    ZReplaceUtil.replaceListList(dep.getCss(), exposed);
    cssExposed.addAll(dep.getCss());

    ZReplaceUtil.replaceListList(dep.getJavaScript(), exposed);
    javaScriptExposed.addAll(dep.getJavaScript());
  }


  public List<List<String>> getJavaScriptExposed()
  {
    return javaScriptExposed;
  }


  public List<List<String>> getCssExposed()
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


  public void beforeRender(Object obj, Map<String, Object> exposed)
      throws Exception
  {
    ZRenderer rendererAnnot = obj.getClass().getAnnotation(ZRenderer.class);

    incRenderCallCounter();

    if (exposed.get("cssId") == null)
    {
      String cssId = getCssIdRepository().getCssId(obj.getClass());
      exposed.put("cssId", cssId);
    }

    if (exposed.get("contextPath") == null)
    {
      exposed.put("contextPath", getContextPath());
    }

    exposed.put("renderService", ZTemplates.getRenderService());

    // register scripts, needs exposed values
    registerScripts(obj, exposed);

    if (rendererAnnot != null)
    {
      if (rendererAnnot.zscript() && exposed.get("zscript") == null)
      {
        if (getScriptExposedBy() != null)
        {
          throw new Exception(
              "zscript can only be exposed once per request, but is exposed by \n"
                  + getScriptExposedBy()
                  + "\n"
                  + ZWebRenderContextImpl
                      .computeZscriptExposedBy(obj));
        }
        setScriptExposedBy(ZWebRenderContextImpl
            .computeZscriptExposedBy(obj));
        ZIJavaScriptGroupingStrategy groupingStrategy = rendererAnnot
            .zscriptJavaScriptGroupingStrategy().newInstance();
        ZIJavaScriptPreprocessor javaScriptPreprocessor = rendererAnnot
            .zscriptJavaScriptPreprocessor().newInstance();
        ZICssPreprocessor cssPreprocessor = rendererAnnot
            .zscriptCssPreprocessor().newInstance();
        String zjavascript = computeHtmlJavaScriptTags(javaScriptPreprocessor,
            groupingStrategy);
        String zcss = computeHtmlCssTags(cssPreprocessor);
        String zscript = zcss + "\n" + zjavascript;
        if (exposed.get("zcss") == null)
        {
          exposed.put("zcss", zcss);
        }
        if (exposed.get("zjavascript") == null)
        {
          exposed.put("zjavascript", zjavascript);
        }
        exposed.put("zscript", zscript);
      }
    }
  }


  private static String computeZscriptExposedBy(Object obj)
  {
    return obj.getClass().getSimpleName() + "[" + obj + "]";
  }

}
