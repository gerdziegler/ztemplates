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
import org.ztemplates.render.ZIRenderEngine;
import org.ztemplates.render.ZIRenderedObject;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.web.script.zscript.ZScriptDefinition;

public class ZRenderEngine implements ZIRenderEngine
{
  protected static Logger log = Logger.getLogger(ZRenderEngine.class);

  private ZIExposedMethodRepository exposedMethodRepository;

  private ZIRendererRepository rendererRepository;

  private ZIRenderEngineListener listener;

  private final boolean debugRenderComments;

  private int depth = 0;


  public ZRenderEngine(ZIRenderContext ctx)
  {
    this.listener = ctx;
    this.exposedMethodRepository = ctx;
    this.rendererRepository = ctx;
    this.debugRenderComments = ctx.isDebugRenderComments();
  }


  public ZRenderEngine(ZIExposedMethodRepository exposedMethodRepository,
      ZIRendererRepository rendererRepository,
      ZIRenderEngineListener listener,
      boolean debugRenderComments)
  {
    this.exposedMethodRepository = exposedMethodRepository;
    this.rendererRepository = rendererRepository;
    this.listener = listener;
    this.debugRenderComments = debugRenderComments;
  }


  public ZRenderEngine(ZIExposedMethodRepository exposedMethodRepository,
      ZIRendererRepository rendererRepository,
      boolean debugRenderComments)
  {
    this.exposedMethodRepository = exposedMethodRepository;
    this.rendererRepository = rendererRepository;
    this.listener = null;
    this.debugRenderComments = debugRenderComments;
  }


  public String render(Object obj) throws Exception
  {
    if (obj == null)
    {
      return null;
    }
    if (obj instanceof String)
    {
      return (String) obj;
    }

    depth++;

    // always compute this to get scripts
    Map<String, Object> exposed = getExposed(obj);

    if (listener != null)
    {
      listener.beforeRender(obj, exposed);
    }

    if (obj instanceof ZIRenderedObject)
    {
      ZIRenderedObject ro = (ZIRenderedObject) obj;
      depth--;
      return ro.getText();
    }

    Class<?> clazz = obj.getClass();
    ZRenderer rendererAnnot = obj.getClass().getAnnotation(ZRenderer.class);
    if (rendererAnnot == null)
    {
      depth--;
      return obj.toString();
    }
    else
    {
      ZIRenderer renderer = rendererRepository.getRenderer(rendererAnnot.value());
      StringBuffer ret = new StringBuffer();
      long time = 0;
      String commentPrefix = null;
      String commentSuffix = null;
      if (debugRenderComments)
      {
        ZScriptDefinition script = clazz.getAnnotation(ZScriptDefinition.class);
        if (script != null || "text/javascript".equals(rendererAnnot.mimeType()) || "application/x-javascript".equals(rendererAnnot.mimeType()))
        {
          commentPrefix = "\n//";
          commentSuffix = "\n";
        }
        else if ("text/html".equals(rendererAnnot.mimeType()) || "text/xml".equals(rendererAnnot.mimeType()))
        {
          commentPrefix = "\n" + indent() + "<!-- " + depth + " ";
          commentSuffix = "-->\n";
        }
        if (commentPrefix != null)
        {
          time = System.currentTimeMillis();
          ret.append(commentPrefix);
          ret.append(" BEGIN ");
          ret.append(clazz.getName());
          ret.append(commentSuffix);
        }
      }
      ret.append(renderer.render(obj.getClass(), exposed));
      if (debugRenderComments)
      {
        if (commentPrefix != null)
        {
          long delta = System.currentTimeMillis() - time;
          time = System.currentTimeMillis();
          ret.append(commentPrefix);
          ret.append(" END ");
          ret.append(clazz.getName());
          ret.append(" [" + delta + " ms]");
          ret.append(commentSuffix);
        }
      }
      depth--;
      return ret.toString();
    }
  }


  private String indent()
  {
    StringBuffer sb = new StringBuffer(" ");
    for (int i = 0; i < depth; i++)
    {
      sb.append("    ");
    }
    return sb.toString();
  }


  public Map<String, Object> getExposed(Object obj) throws Exception
  {
    Map<String, Object> values = new HashMap<String, Object>();

    List<ZIExposedValue> exposedMethods = exposedMethodRepository.getExposedValues(obj.getClass());

    for (ZIExposedValue m : exposedMethods)
    {
      Object val = m.getValue(obj);
      values.put(m.getName() + "Bean", val);
      if (m.isRender())
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
