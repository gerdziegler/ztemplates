package org.ztemplates.web.request.impl;

import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZIActionApplicationContext;
import org.ztemplates.commons.ZIObjectFactory;
import org.ztemplates.render.ZIRenderApplicationContext;
import org.ztemplates.web.ZIApplicationService;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.script.zscript.ZIJavaScriptRepository;

public class ZApplicationServiceImpl implements ZIApplicationService
{
  private final ZApplication application;


  public ZApplicationServiceImpl(ZApplication application)
  {
    this.application = application;
  }


  public ZIRenderApplicationContext getRenderApplicationContext()
  {
    return application.getRenderApplication().getApplicationContext();
  }


  public ZIActionApplicationContext getActionApplicationContext()
  {
    return application.getActionApplication().getApplicationContext();
  }


  public ZIClassRepository getClassRepository()
  {
    return application.getClassRepository();
  }


  public ZIJavaScriptRepository getJavaScriptRepository()
  {
    return application.getJavaScriptRepository();
  }


  @Override
  public ZIObjectFactory getObjectFactory()
  {
    return application.getActionApplication().getObjectFactory();
  }
}
