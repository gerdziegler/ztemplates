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
package org.ztemplates.web.velocity;

import java.io.File;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.apache.velocity.app.VelocityEngine;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.render.velocity.ZVelocityRendererFactory;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.application.ZApplicationRepository;

public class ZVelocityContextListener implements ServletContextListener
{

  private static final Logger log = Logger.getLogger(ZVelocityContextListener.class);


  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    log.info("initializing velocity...");
    ServletContext servletContext = servletContextEvent.getServletContext();
    try
    {
      String KEY = "file.resource.loader.path";
      log.info("initializing standalone Velocity...");
      Properties prop = getVelocityProperties(servletContext);
      String templatePath = prop.getProperty(KEY);
      if (templatePath != null)
      {
        String realTemplatePath = servletContext.getRealPath(templatePath) + "," + templatePath;
        log.info("converting " + KEY + " - [" + templatePath + "] to [" + realTemplatePath + "]");
        prop.setProperty(KEY, realTemplatePath);
      }
      prop.list(System.out);
      ZApplicationRepository applicationRepository = new ZApplicationRepository(servletContext);
      ZApplication application = applicationRepository.getApplication();
      VelocityEngine ve = new VelocityEngine();
      ve.init(prop);
      ZVelocityRenderer.setVelocityEngine(application.getRenderApplication()
          .getApplicationContext(), ve);
      log.info("velocity initialized");
    }
    catch (Exception e)
    {
      log.error("error while initializing velocity:", e);
    }
  }


  public void contextDestroyed(ServletContextEvent arg0)
  {
    log.info("velocity destroyed");
  }


  private static Properties getVelocityProperties(ServletContext servletContext) throws Exception
  {
    log.info("loading velocity properties...");

    String propFile = "WEB-INF/velocity.properties";

    Properties ret = getVelocityPropertiesFromFile(servletContext, propFile);
    if (ret != null)
    {
      return ret;
    }

    ret = getVelocityPropertiesFromResourcePath(servletContext, propFile);
    if (ret != null)
    {
      return ret;
    }

    ret = getVelocityPropertiesFromResourcePath(servletContext, "/" + propFile);
    if (ret != null)
    {
      return ret;
    }

    return ZVelocityRendererFactory.getVelocityProperties();
  }


  private static Properties getVelocityPropertiesFromFile(ServletContext servletContext, String path)
      throws Exception
  {
    log.info("try loading velocity properties from " + new File(path).getAbsolutePath() + ") ...");
    Properties ret = ZVelocityRendererFactory.getVelocityProperties(path);
    return ret;
  }


  private static Properties getVelocityPropertiesFromResourcePath(ServletContext servletContext,
      String path) throws Exception
  {
    log.info("try loading velocity properties from ServletContext.getResourceAsStream(\"" + path
        + "\") ...");
    InputStream in = servletContext.getResourceAsStream(path);
    if (in != null)
    {
      try
      {
        log.info("OK");
        Properties ret = new Properties();
        ret.load(in);
        return ret;
      }
      finally
      {
        in.close();
      }
    }
    else
    {
      return null;
    }
  }
}
