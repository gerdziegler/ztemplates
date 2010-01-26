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
package org.ztemplates.web;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassPathItemWebapp;
import org.zclasspath.ZClassPathScanner;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassPathFilter;
import org.zclasspath.ZIClassPathItem;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZActionApplication;
import org.ztemplates.render.ZRenderApplication;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.application.ZApplicationContextWebImpl;
import org.ztemplates.web.application.ZApplicationRepositoryWeb;
import org.ztemplates.web.script.css.ZCachingCssProcessorData;
import org.ztemplates.web.script.javascript.ZCachingJavaScriptProcessorData;
import org.ztemplates.web.standalone.ZApplicationRepositoryStandalone;

/**
 * 
 * Assembles the application
 * 
 */
public class ZTemplatesContextListener implements ServletContextListener
{
  private static final Logger log = Logger.getLogger(ZTemplatesContextListener.class);


  public void contextInitialized(ServletContextEvent ev)
  {
    try
    {
      log.info("initialiazing context...");
      ServletContext ctx = ev.getServletContext();

      log.info("creating class repository...");
      final ZIClassRepository classRepository = createClassRepository(ctx);
      final ZApplicationContextWebImpl applicationContext = new ZApplicationContextWebImpl(classRepository, ctx);

      log.info("creating application...");

      ZCachingJavaScriptProcessorData.setInstance(applicationContext, new ZCachingJavaScriptProcessorData());
      ZCachingCssProcessorData.setInstance(applicationContext, new ZCachingCssProcessorData());
      ZActionApplication actionApplication = new ZActionApplication(applicationContext, classRepository);
      ZRenderApplication renderApplication = new ZRenderApplication(applicationContext, classRepository);
      ZApplication application = new ZApplication(classRepository, actionApplication, renderApplication);
      ZApplicationRepositoryWeb.setApplication(ctx, application);
      
      String applicationName =  ctx.getInitParameter("applicationName");
      if(applicationName==null)
      {
        log.info("No init parameter called 'applicationName' has been found in web.xml --- Using default application name. This is safe if webapp has its own classloader or you use ztemplates only inside a http request. --- If you share classloader between weapps and need access to ztemplates functionality outside of a http request (for example: a scheduled job) set the init parameter 'applicationName' in web.xml to unique name in each webapp and use the applicationName in ZTemplatesStandalone.init()");
        applicationName = ZApplicationRepositoryStandalone.DEFAULT_APP_NAME;
      }
      ZApplicationRepositoryStandalone.setApplication(applicationName, application);

      log.info("context initialized");
    }
    catch (Exception e)
    {
      log.error("context not initialized", e);
    }
  }


  public void contextDestroyed(ServletContextEvent ev)
  {
    ServletContext ctx = ev.getServletContext();
    String applicationName =  ctx.getInitParameter("applicationName");
    if(applicationName==null)
    {
      applicationName = ZApplicationRepositoryStandalone.DEFAULT_APP_NAME;
    }
    ZApplicationRepositoryWeb.setApplication(ctx, null);
    ZApplicationRepositoryStandalone.setApplication(applicationName, null);    
    log.info("context destroyed");
  }


  private ZIClassRepository createClassRepository(final ServletContext servletContext) throws Exception
  {
    List<ZIClassPathItem> items = new ArrayList<ZIClassPathItem>();
    items.add(new ZClassPathItemWebapp(servletContext, "/WEB-INF/classes/"));
    items.add(new ZClassPathItemWebapp(servletContext, "/WEB-INF/lib/"));

    String filterClassPropertyName = ZIClassPathFilter.class.getSimpleName();
    final String filterClass = servletContext.getInitParameter(filterClassPropertyName);
    final ZIClassPathFilter filter;
    if (filterClass != null)
    {
      log.info("filtering scanned classes with : " + filterClass + " ( to change modify " + filterClassPropertyName + " in WEB-INF/xml )");
      filter = (ZIClassPathFilter) Class.forName(filterClass).newInstance();
    }
    else
    {
      log.info("no init property: " + filterClassPropertyName + ", using default value " + ZDefaultClassPathFilter.class.getName());
      log.info("to avoid scanning too many jars/classes you could have set the init property '" + filterClassPropertyName
          + "' in WEB-INF/web.xml to the classname of a implementation of " + ZIClassPathFilter.class.getName() + " (best extending "
          + ZDefaultClassPathFilter.class.getName() + ")");
      filter = new ZDefaultClassPathFilter();
    }
    ZClassPathScanner scanner = new ZClassPathScanner();
    scanner.setClassPathItems(items);
    scanner.setFilter(filter);

    ZIClassRepository ret = new ZClassRepository(scanner);
    return ret;
  }
}
