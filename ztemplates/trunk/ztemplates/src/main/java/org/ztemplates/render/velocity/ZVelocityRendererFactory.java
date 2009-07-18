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

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.ztemplates.render.ZRenderApplication;
import org.ztemplates.render.impl.ZIRenderContext;
import org.ztemplates.render.impl.ZRenderContextImpl;
import org.ztemplates.render.script.ZCssProcessor;
import org.ztemplates.render.script.ZICssProcessor;
import org.ztemplates.render.script.ZIJavaScriptProcessor;
import org.ztemplates.render.script.ZJavaScriptProcessor;

public class ZVelocityRendererFactory
{
  protected static Logger log = Logger.getLogger(ZVelocityRendererFactory.class);


  public static RuntimeInstance createDefaultRuntimeInstance() throws Exception
  {
    Properties prop = new Properties();
    prop.put("resource.loader", "classpath");
    prop.put("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    RuntimeInstance ve = new RuntimeInstance();
    ve.init(prop);
    return ve;
  }


  public static ZIRenderContext createStandaloneRenderEngine(ZRenderApplication application)
      throws Exception
  {
    Properties prop = new Properties();
    prop.put("resource.loader", "classpath");
    prop.put("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
    return createStandaloneRenderEngine(application, prop);
  }


  public static ZIRenderContext createStandaloneRenderEngine(ZRenderApplication application,
      Properties prop) throws Exception
  {
    VelocityEngine ve = new VelocityEngine();
    ve.init(prop);
    ZVelocityRenderer.setVelocityEngine(application.getApplicationContext(), ve);

    String contextPath = null;
    ZIJavaScriptProcessor javaScriptProcessor = new ZJavaScriptProcessor(contextPath);
    ZICssProcessor cssProcessor = new ZCssProcessor(contextPath);
    ZIRenderContext ctx = new ZRenderContextImpl(application,
        contextPath,
        javaScriptProcessor,
        cssProcessor);
    return ctx;
  }


  public static Properties getVelocityProperties() throws Exception
  {
    Properties prop = getVelocityProperties("velocity.properties");
    if (prop == null)
    {
      log.info("velocity properties not found, using defaults...");
      String res = "/" + ZVelocityRendererFactory.class.getName().replace('.', '/') + ".properties";
      log.warn("loading velocity properties from jar: " + res);
      InputStream in = ZVelocityRendererFactory.class.getResourceAsStream(res);
      if (in != null)
      {
        prop = new Properties();
        prop.load(in);
        in.close();
      }
    }
    return prop;
  }


  public static Properties getVelocityProperties(String propsFile) throws Exception
  {
    InputStream in = null;
    try
    {
      in = ZVelocityRendererFactory.class.getResourceAsStream(propsFile);
      if (in == null)
      {
        log.info("not found velocity properties in classloader " + propsFile);
        File inFile = new File(propsFile);
        if (inFile.exists())
        {
          log.info("loading velocity properties from file " + inFile.getAbsolutePath());
          in = new FileInputStream(inFile);
        }
        else
        {
          log.info("not found velocity properties in file " + inFile.getAbsolutePath());
        }
      }
      else
      {
        log.info("loading velocity properties from classloader " + propsFile);
      }

      if (in != null)
      {
        Properties ret = new Properties();
        ret.load(in);
        return ret;
      }
      return null;
    }
    finally
    {
      if (in != null)
      {
        try
        {
          in.close();
        }
        catch (Exception e)
        {
          log.error(propsFile, e);
        }
      }
    }
  }
}
