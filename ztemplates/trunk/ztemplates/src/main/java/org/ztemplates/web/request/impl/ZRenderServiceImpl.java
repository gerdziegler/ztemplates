/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de) Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. @author
 * www.gerdziegler.de
 */

package org.ztemplates.web.request.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.ztemplates.render.ZRenderApplication;
import org.ztemplates.render.ZRenderedObject;
import org.ztemplates.render.impl.ZIRenderContext;
import org.ztemplates.render.impl.ZRenderContextImpl;
import org.ztemplates.render.script.ZICssProcessor;
import org.ztemplates.render.script.ZIJavaScriptProcessor;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.application.ZMergeUtil;
import org.ztemplates.web.script.css.ZCachingCssProcessor;
import org.ztemplates.web.script.css.ZCachingCssProcessorData;
import org.ztemplates.web.script.javascript.ZCachingJavaScriptProcessor;
import org.ztemplates.web.script.javascript.ZCachingJavaScriptProcessorData;
import org.ztemplates.web.script.javascript.ZICachingJavaScriptProcessorContext;
import org.ztemplates.web.script.javascript.ZJavaScriptLoaderAction;

public class ZRenderServiceImpl implements ZIRenderService
{
  static final Logger log = Logger.getLogger(ZRenderServiceImpl.class);

  private final ZRenderApplication application;

  private final ZIRenderContext ctx;

  private int crtId = 0;


  public ZRenderServiceImpl(final ZRenderApplication application, final String contextPath)
  {
    this.application = application;

    ZCachingCssProcessorData cachingCssProcessorData = ZCachingCssProcessorData
        .getInstance(application.getApplicationContext());
    ZICssProcessor cssProcessor = new ZCachingCssProcessor(cachingCssProcessorData);

    final ZICachingJavaScriptProcessorContext cachingJavaScriptProcessorContext = new ZICachingJavaScriptProcessorContext()
    {
      public byte[] mergeWebResources(List<String> urls) throws Exception
      {
        return ZMergeUtil.mergeWebResources(urls);
      }


      public ZCachingJavaScriptProcessorData getData()
      {
        return ZCachingJavaScriptProcessorData.getInstance(application.getApplicationContext());
      }


      public String getContextPath()
      {
        return contextPath;
      }


      public String createUrl(String js) throws Exception
      {
        String url = ZJavaScriptLoaderAction.createUrl(js);
        return url;
      }
    };
    ZIJavaScriptProcessor javaScriptProcessor = new ZCachingJavaScriptProcessor(cachingJavaScriptProcessorContext);
    ctx = new ZRenderContextImpl(application, contextPath, javaScriptProcessor, cssProcessor);
  }


  public String render(Object obj) throws Exception
  {
    if (obj == null)
    {
      return null;
    }
    if(obj instanceof String)
    {
      return (String)obj;
    }
    
    long time = System.currentTimeMillis();
    String ret = ctx.getRenderEngine(obj).render(obj, ctx);
    long delta = System.currentTimeMillis() - time;
    int cnt = ctx.getRenderCallCounter();
    if (delta > 20)
    {
      log.info("rendered " + obj.getClass().getName() + " [" + delta + " ms] " + cnt + " calls "
          + (delta / cnt));
    }
    return ret;
  }
  
  public ZRenderedObject prerender(Object obj) throws Exception
  {
      if (obj == null)
      {
        return null;
      }
      if(obj instanceof String)
      {
        return new ZRenderedObject((String)obj);
      }
      long time = System.currentTimeMillis();
      String rendered = ctx.getRenderEngine(obj).render(obj, ctx);
      long delta = System.currentTimeMillis() - time;
      int cnt = ctx.getRenderCallCounter();
      if (delta > 20)
      {
        log.info("rendered " + obj.getClass().getName() + " [" + delta + " ms] " + cnt + " calls "
            + (delta / cnt));
      }      
      ZRenderedObject ret = new ZRenderedObject(rendered, ctx.getJavaScriptExposed(), ctx.getCssExposed());
      return ret;      
  }


  public String renderZtemplatesCss() throws Exception
  {
    return application.getCssEngine().getCss();
  }


  public String getCssId(Class clazz)
  {
    return application.getCssIdRepository().getCssId(clazz);
  }


  public String createJavaScriptId()
  {
    return "zid" + (crtId++);
  }
}
