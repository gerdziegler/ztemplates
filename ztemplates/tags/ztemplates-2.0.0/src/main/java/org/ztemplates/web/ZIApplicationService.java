package org.ztemplates.web;

import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZIActionApplicationContext;
import org.ztemplates.render.ZIRenderApplicationContext;

public interface ZIApplicationService extends ZIService
{
  public ZIClassRepository getClassRepository();


  public ZIRenderApplicationContext getRenderApplicationContext();


  public ZIActionApplicationContext getActionApplicationContext();
}
