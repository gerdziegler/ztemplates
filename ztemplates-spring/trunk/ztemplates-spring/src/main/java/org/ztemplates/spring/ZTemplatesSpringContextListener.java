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
package org.ztemplates.spring;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.ztemplates.commons.ZIObjectFactory;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.application.ZApplicationRepositoryWeb;

/**
 * 
 * prepares ztemplates spring specific objects 
 * 
 */
public class ZTemplatesSpringContextListener implements ServletContextListener
{
  private static final Logger log = Logger.getLogger(ZTemplatesSpringContextListener.class);


  public void contextInitialized(ServletContextEvent ev)
  {
    ServletContext ctx = ev.getServletContext();
    ZTemplatesSpringContextListener.initContext(ctx);
  }


  public static void initContext(ServletContext ctx)
  {
    log.info("ztemplates spring initializing...");
    ZApplication application = ZApplicationRepositoryWeb.getApplication(ctx);
    ZIObjectFactory actionObjectFactory = application.getActionApplication().getObjectFactory();
    application.getActionApplication().setObjectFactory(new ZSpringObjectFactory(actionObjectFactory));
    ZIObjectFactory renderObjectFactory = application.getRenderApplication().getObjectFactory();
    application.getRenderApplication().setObjectFactory(new ZSpringObjectFactory(renderObjectFactory));
    log.info("ztemplates spring initialized");
  }


  public void contextDestroyed(ServletContextEvent ev)
  {
  }
}