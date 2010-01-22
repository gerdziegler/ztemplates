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
import org.ztemplates.render.ZIRenderedObject;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.ZRenderer;

public class ZRenderEngine implements ZIRenderEngine
{
  protected static Logger log = Logger.getLogger(ZRenderEngine.class);

  private ZIExposedMethodRepository exposedMethodRepository;

  private ZIRendererRepository rendererRepository;

  private ZIRenderEngineListener listener;


  public ZRenderEngine(ZIRenderContext ctx)
  {
    // this.ctx = ctx;
    this.listener = ctx;
    this.exposedMethodRepository = ctx;
    this.rendererRepository = ctx;
  }


  public ZRenderEngine(ZIExposedMethodRepository exposedMethodRepository, ZIRendererRepository rendererRepository, ZIRenderEngineListener listener)
  {
    this.exposedMethodRepository = exposedMethodRepository;
    this.rendererRepository = rendererRepository;
    this.listener = listener;
  }


  public ZRenderEngine(ZIExposedMethodRepository exposedMethodRepository, ZIRendererRepository rendererRepository)
  {
    this.exposedMethodRepository = exposedMethodRepository;
    this.rendererRepository = rendererRepository;
    this.listener = null;
  }


  public String render(Object obj) throws Exception
  {
    if (obj == null)
    {
      return null;
    }

    // always compute this to get scripts
    Map<String, Object> exposed = getExposed(obj);

    if (listener != null)
    {
      listener.beforeRender(obj, exposed);
    }

    if (obj instanceof ZIRenderedObject)
    {
      ZIRenderedObject ro = (ZIRenderedObject) obj;
      return ro.getText();
    }

    ZRenderer rendererAnnot = obj.getClass().getAnnotation(ZRenderer.class);
    if (rendererAnnot == null)
    {
      return obj.toString();
    }
    else
    {
      ZIRenderer renderer = rendererRepository.getRenderer(rendererAnnot.value());
      long time = System.currentTimeMillis();
      String ret = renderer.render(obj.getClass(), exposed);
      long delta = System.currentTimeMillis() - time;
      if (delta > 15)
      {
        log.info("    engine " + obj.getClass().getName() + " [" + delta + " ms]");
      }
      return ret;
    }
  }


  public Map<String, Object> getExposed(Object obj) throws Exception
  {
    Map<String, Object> values = new HashMap<String, Object>();

    List<ZExposedMethod> exposedMethods = exposedMethodRepository.getExposedMethods(obj.getClass());

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
            String rendered = render(crt);
            newVal.add(rendered);
          }
          val = newVal;
        }
        else
        {
          if (val != null)
          {
            String rendered = render(val);
            val = rendered;
          }
        }
      }
      values.put(m.getName(), val);
    }

    return values;
  }
}
