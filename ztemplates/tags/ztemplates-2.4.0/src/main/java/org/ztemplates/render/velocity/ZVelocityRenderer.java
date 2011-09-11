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

package org.ztemplates.render.velocity;

import java.io.InputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import org.ztemplates.render.ZIRenderApplicationContext;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.ZITemplateNameRepository;
import org.ztemplates.render.ZRenderer;

public class ZVelocityRenderer implements ZIRenderer
{
  private static final String GLOBAL_PROPERTY_CONFIGURATION = ZVelocityRenderer.class.getSimpleName() + ".CONFIGURATION";

  protected static Logger log = Logger.getLogger(ZVelocityRenderer.class);

  private ZITemplateNameRepository templateNameRepository;

  private VelocityEngine velocityEngine;

  private String encoding;


  public static VelocityEngine getVelocityEngine(ZIRenderApplicationContext applicationContext) throws Exception
  {
    VelocityEngine cfg = (VelocityEngine) applicationContext.getAttribute(ZVelocityRenderer.GLOBAL_PROPERTY_CONFIGURATION);
    if (cfg == null)
    {
      throw new Exception("missing Velocity Engine (global property '" + ZVelocityRenderer.GLOBAL_PROPERTY_CONFIGURATION
          + "') call setVelocityEngine() or init()");
    }
    return cfg;
  }


  public static void setVelocityEngine(ZIRenderApplicationContext applicationContext, VelocityEngine cfg) throws Exception
  {
    applicationContext.setAttribute(ZVelocityRenderer.GLOBAL_PROPERTY_CONFIGURATION, cfg);
  }


  public void init(ZIRenderApplicationContext applicationContext, ZITemplateNameRepository templateNameRepository) throws Exception
  {
    this.templateNameRepository = templateNameRepository;
    velocityEngine = ZVelocityRenderer.getVelocityEngine(applicationContext);
    encoding = applicationContext.getEncoding();
  }


  public String render(Class clazz, Map<String, Object> values) throws Exception
  {
    ZRenderer rend = (ZRenderer) clazz.getAnnotation(ZRenderer.class);
    String encoding;
    if (rend.encoding().length() > 0)
    {
      encoding = rend.encoding();
    }
    else
    {
      encoding = this.encoding;
    }
    String template = templateNameRepository.getTemplateName(clazz) + ".vm";
    Template t = velocityEngine.getTemplate(template, encoding);
    if (t == null)
    {
      throw new Exception("velocity template not found: " + template);
    }

    VelocityContext vctx = new VelocityContext();
    for (Map.Entry<String, Object> entry : values.entrySet())
    {
      vctx.put(entry.getKey(), entry.getValue());
    }

    StringWriter pw = new StringWriter();
    try
    {
      t.merge(vctx, pw);
    }
    catch (Exception e)
    {
      log.error("error while rendering [" + template + "] - " + e.getMessage());
      throw e;
    }

    return pw.toString();
  }


  public static void init(ZIRenderApplicationContext applicationContext) throws Exception
  {
    VelocityEngine ve = new VelocityEngine();

    Properties prop = ZVelocityRenderer.getProperties(applicationContext);
    String encoding = applicationContext.getEncoding();
    prop.setProperty("input.encoding", encoding);
    log.info("--- Velocity Properties ---");
    log.info(prop);
    ve.init(prop);
    applicationContext.setAttribute(ZVelocityRenderer.GLOBAL_PROPERTY_CONFIGURATION, ve);
    log.info("--- velocity initialized ---");
  }


  private static Properties getProperties(ZIRenderApplicationContext applicationContext) throws Exception
  {
    Properties prop = ZVelocityRenderer.getPropertiesFromApplicationContext(applicationContext);
    if (prop == null)
    {
      log.info("using default Velocity properties as defined in " + ZVelocityRenderer.class.getName());
      prop = ZVelocityRenderer.getDefaultProperties();
    }
    boolean noCache = "true".equals(applicationContext.getInitParameter("ztemplates.velocity.nocache"));
    if (noCache || applicationContext.isDevMode())
    {
      List<String> keys = new ArrayList<String>();
      for (Object o : prop.keySet())
      {
        String s = (String) o;
        if (s.endsWith(".resource.loader.cache"))
        {
          keys.add(s);
        }
      }
      for (String s : keys)
      {
        prop.setProperty(s, "false");
      }
    }
    return prop;
  }


  private static Properties getDefaultProperties()
  {
    Properties prop = new Properties();
    prop.setProperty("directive.foreach.counter.initial.value", "0");
    prop.setProperty("resource.loader", "classpath,file");
    prop.setProperty("file.resource.loader.class", FileResourceLoader.class.getName());
    prop.setProperty("file.resource.loader.cache", "true");
    prop.setProperty("file.resource.loader.path", "templates");
    prop.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    prop.setProperty("classpath.resource.loader.cache", "true");
    prop.setProperty("velocimacro.permissions.allowInline", "true");
    prop.setProperty("velocimacro.permissions.allowInlineToOverride", "false");
    prop.setProperty("velocimacro.context.localscope", "true");
    prop.setProperty("input.encoding", "UTF-8");
    return prop;
  }


  private static Properties getPropertiesFromApplicationContext(ZIRenderApplicationContext applicationContext) throws Exception
  {
    String[] locations =
    {
        "/WEB-INF/velocity.properties", "WEB-INF/velocity.properties", "/velocity.properties", "velocity.properties"
    };
    for (String loc : locations)
    {
      Properties prop = ZVelocityRenderer.loadPropertiesFromApplicationContext(applicationContext, loc);
      if (prop != null)
      {
        return prop;
      }
    }
    return null;
  }


  private static Properties loadPropertiesFromApplicationContext(ZIRenderApplicationContext applicationContext, String propFile) throws Exception
  {
    log.debug("try loading velocity properties from path " + propFile + " ...");
    InputStream in = applicationContext.getResourceAsStream(propFile);
    if (in != null)
    {
      try
      {
        Properties ret = new Properties();
        ret.load(in);
        log.info("loaded velocity properties from path " + propFile);
        return ret;
      }
      finally
      {
        in.close();
      }
    }
    log.debug("could not load velocity properties from path " + propFile);
    return null;
  }
}
