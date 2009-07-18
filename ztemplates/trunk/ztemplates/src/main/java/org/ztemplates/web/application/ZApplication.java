package org.ztemplates.web.application;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZActionApplication;
import org.ztemplates.render.ZRenderApplication;
import org.ztemplates.web.request.impl.ZServiceFactory;

/**
 * 
 * assembles the application
 * 
 */
public class ZApplication
{
  private static final Logger log = Logger.getLogger(ZApplication.class);

  private final ZIClassRepository classRepository;

  private final ZActionApplication actionApplication;

  private final ZRenderApplication renderApplication;

  private ZIServiceFactory serviceFactory;


  public ZApplication(ZIClassRepository classRepository,
      ZActionApplication actionApplication,
      ZRenderApplication renderApplication) throws Exception
  {
    this.classRepository = classRepository;
    this.actionApplication = actionApplication;
    this.renderApplication = renderApplication;
    serviceFactory = new ZServiceFactory(this);
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


  public void setServiceFactory(ZIServiceFactory serviceRepositoryConfigurator)
  {
    this.serviceFactory = serviceRepositoryConfigurator;
  }
}
