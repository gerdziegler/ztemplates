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

import javax.servlet.ServletContextEvent;

import org.apache.log4j.Logger;
import org.ztemplates.velocity.ZTemplatesVelocityContextListener;

@Deprecated
public class ZVelocityContextListener extends ZTemplatesVelocityContextListener
{
  private static final Logger log = Logger.getLogger(ZVelocityContextListener.class);


  public void contextInitialized(ServletContextEvent servletContextEvent)
  {
    log.warn("deprecated: do not use " + getClass() + " anymore.");
    log.warn("please use " + getClass().getSuperclass().getName() + " instead.");
    super.contextInitialized(servletContextEvent);
  }


  public void contextDestroyed(ServletContextEvent ev)
  {
    super.contextDestroyed(ev);
    log.warn("deprecated: do not use " + getClass() + " anymore.");
    log.warn("please use " + getClass().getSuperclass().getName() + " instead.");
  }

  // private static Properties getVelocityProperties(ServletContext
  // servletContext) throws Exception
  // {
  // log.info("loading velocity properties...");
  //
  // String propFile = "WEB-INF/velocity.properties";
  //
  // Properties ret;
  //
  // ret = getVelocityPropertiesFromResourcePath(servletContext, propFile);
  // if (ret != null)
  // {
  // return ret;
  // }
  //
  // ret = getVelocityPropertiesFromResourcePath(servletContext, "/" +
  // propFile);
  // if (ret != null)
  // {
  // return ret;
  // }
  //
  // return ret =
  // ZVelocityRenderer.getProperties(ZVelocityContextListener.class);
  // }

  // private static Properties getVelocityPropertiesFromFile(String path) throws
  // Exception
  // {
  // log.info("try loading velocity properties from " + new
  // File(path).getAbsolutePath() + ") ...");
  // Properties ret = ZVelocityRendererFactory.getVelocityProperties(path);
  // return ret;
  // }

  // private static Properties
  // getVelocityPropertiesFromResourcePath(ServletContext servletContext, String
  // path) throws Exception
  // {
  // log.info("try loading velocity properties from ServletContext.getResourceAsStream(\""
  // + path + "\") ...");
  // InputStream in = servletContext.getResourceAsStream(path);
  // if (in != null)
  // {
  // try
  // {
  // log.info("OK");
  // Properties ret = new Properties();
  // ret.load(in);
  // return ret;
  // }
  // finally
  // {
  // in.close();
  // }
  // }
  // else
  // {
  // return null;
  // }
  // }
}
