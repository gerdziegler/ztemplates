/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * @author www.gerdziegler.de
 */

package org.ztemplates.render.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZIRenderEngine;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.ZRenderedObject;
import org.ztemplates.render.ZRenderer;

public class ZRenderEngine implements ZIRenderEngine
{
  protected static Logger log = Logger.getLogger(ZRenderEngine.class);


  public String render(Object obj, ZIRenderContext ctx) throws Exception
  {
    if (obj == null)
    {
      return null;
    }

    ctx.incRenderCallCounter();
    
    if(obj instanceof ZRenderedObject)
    {
	ZRenderedObject ro = (ZRenderedObject)obj;	
	ctx.getCssExposed().addAll(ro.getCssExposed());
	ctx.getJavaScriptExposed().addAll(ro.getJavaScriptExposed());	
	return ro.getText();
    }

    // always compute this to get script
    Map<String, Object> exposed = getExposed(obj, ctx);

    // register scripts from annotation
    ctx.registerScripts(obj, exposed);

    ZRenderer rendererAnnot = obj.getClass().getAnnotation(ZRenderer.class);
    if (rendererAnnot == null)
    {
      return obj.toString();
    }

    if (rendererAnnot.cssId() && exposed.get("cssId") == null)
    {
      String cssId = ctx.getCssIdRepository().getCssId(obj.getClass());
      exposed.put("cssId", cssId);
    }

    if (rendererAnnot.contextPath() && exposed.get("contextPath") == null)
    {
      exposed.put("contextPath", ctx.getContextPath());
    }

    if (rendererAnnot.zscript() && exposed.get("zscript") == null)
    {
      if (ctx.getScriptExposedBy() != null)
      {
        throw new Exception("zscript can only be exposed once per request, but is exposed by \n"
            + ctx.getScriptExposedBy() + "\n" + computeZscriptExposedBy(obj));
      }
      ctx.setScriptExposedBy(computeZscriptExposedBy(obj));

      String zscript = ctx.computeHtmlScriptTags();
      exposed.put("zscript", zscript);
    }

    if (rendererAnnot.renderService() && exposed.get("renderService") == null)
    {
      //zscript masks renderService, only write warning if really used in template
      if (rendererAnnot.zscript())
      {
        String msg = "[" + obj.getClass().getName() + " --- invalid values in annotation "
            + ZRenderer.class.getSimpleName()
            + " --- cannot expose both 'zscript' and 'renderService']";
        exposed.put("renderService", msg);
      }
      else
      {
        exposed.put("renderService", this);
      }
    }

    ZIRenderer renderer = ctx.getRenderer(rendererAnnot.value());
    long time = System.currentTimeMillis();
    String ret = renderer.render(obj.getClass(), exposed);
    long delta = System.currentTimeMillis() - time;
    if (delta > 15)
    {
      log.info("    engine " + obj.getClass().getName() + " [" + delta + " ms]");
    }
    return ret;
  }


  private static String computeZscriptExposedBy(Object obj)
  {
    return obj.getClass().getSimpleName() + "[" + obj + "]";
  }


  public Map<String, Object> getExposed(Object obj, ZIRenderContext ctx) throws Exception
  {
    Map<String, Object> values = new HashMap<String, Object>();

    List<ZExposedMethod> exposedMethods = ctx.getExposedMethods(obj.getClass());

    for (ZExposedMethod m : exposedMethods)
    {
      ZExpose exp = m.getMethod().getAnnotation(ZExpose.class);
      Object val = m.getMethod().invoke(obj);
      if (exp.render())
      {
        if (val instanceof Collection)
        {
          Collection oldVal = (Collection) val;
          Collection newVal = new ArrayList();
          for (Object crt : oldVal)
          {
            String rendered = ctx.getRenderEngine(crt).render(crt, ctx);
            newVal.add(rendered);
          }
          val = newVal;
        }
        else
        {
          if (val != null)
          {
            String rendered = ctx.getRenderEngine(val).render(val, ctx);
            val = rendered;
          }
        }
      }
      values.put(m.getName(), val);
    }

    return values;
  }
}
