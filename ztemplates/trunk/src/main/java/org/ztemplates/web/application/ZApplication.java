package org.ztemplates.web.application;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZActionApplication;
import org.ztemplates.render.ZRenderApplication;
import org.ztemplates.web.request.impl.ZServiceFactory;
import org.ztemplates.web.script.zscript.ZIJavaScriptRepository;

/**
 * 
 * assembles the application
 * 
 */
public class ZApplication
{
  private static final Logger log = Logger.getLogger(ZApplication.class);

  private final ZIClassRepository classRepository;

  private final ZIJavaScriptRepository javaScriptRepository;

  private final ZActionApplication actionApplication;

  private final ZRenderApplication renderApplication;

  private ZIServiceFactory serviceFactory;


  public ZApplication(ZIClassRepository classRepository,
      ZIJavaScriptRepository javaScriptRepository,
      ZActionApplication actionApplication,
      ZRenderApplication renderApplication) throws Exception
  {
    this.classRepository = classRepository;
    this.javaScriptRepository = javaScriptRepository;
    this.actionApplication = actionApplication;
    this.renderApplication = renderApplication;
    this.serviceFactory = new ZServiceFactory();
  }


  public ZIClassRepository getClassRepository()
  {
    return classRepository;
  }


  public ZActionApplication getActionApplication()
  {
    return actionApplication;
  }


  public ZRenderApplication getRenderApplication()
  {
    return renderApplication;
  }


  public ZIServiceFactory getServiceFactory()
  {
    return serviceFactory;
  }


  public ZIJavaScriptRepository getJavaScriptRepository()
  {
    return javaScriptRepository;
  }


  public void setServiceFactory(ZIServiceFactory serviceFactory)
  {
    this.serviceFactory = serviceFactory;
  }
}