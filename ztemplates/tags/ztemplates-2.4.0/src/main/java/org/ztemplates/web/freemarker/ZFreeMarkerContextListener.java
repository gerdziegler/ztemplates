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
package org.ztemplates.web.freemarker;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.ztemplates.render.freemarker.ZFreeMarkerRenderer;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.application.ZApplicationRepositoryWeb;

import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.cache.WebappTemplateLoader;
import freemarker.template.Configuration;

public class ZFreeMarkerContextListener implements ServletContextListener
{

  private static final Logger log = Logger.getLogger(ZFreeMarkerContextListener.class);


  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    log.info("initializing FreeMarker...");
    ServletContext ctx = servletContextEvent.getServletContext();
    initFreeMarker(ctx);
    log.info("FreeMarker initialized");
  }


  public void contextDestroyed(ServletContextEvent arg0)
  {
    log.info("FreeMarker destroyed");
  }


  private void initFreeMarker(ServletContext servletContext)
  {
    try
    {
      WebappTemplateLoader wtlClasses = new WebappTemplateLoader(servletContext, "WEB-INF/classes");
      WebappTemplateLoader wtlLib = new WebappTemplateLoader(servletContext, "WEB-INF/lib");
      ClassTemplateLoader ctl = new ClassTemplateLoader(getClass(), "");
      TemplateLoader[] loaders = new TemplateLoader[]
      {
          wtlClasses, wtlLib, ctl
      };
      MultiTemplateLoader mtl = new MultiTemplateLoader(loaders);
      Configuration conf = new Configuration();
      conf.setTemplateLoader(mtl);

      ZApplication application = ZApplicationRepositoryWeb.getApplication(servletContext);
      ZFreeMarkerRenderer.setConfiguration(application.getRenderApplication().getApplicationContext(), conf);
    }
    catch (Exception e)
    {
      log.error("error while initializing FreeMarker:", e);
    }
  }
}
