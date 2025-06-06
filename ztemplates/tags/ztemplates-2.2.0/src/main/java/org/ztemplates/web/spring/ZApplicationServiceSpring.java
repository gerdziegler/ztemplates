package org.ztemplates.web.spring;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZIActionApplicationContext;
import org.ztemplates.render.ZIRenderApplicationContext;
import org.ztemplates.web.ZIApplicationService;
import org.ztemplates.web.ZTemplates;

/**
 * Spring proxy for ZIApplicationService
 * 
 * @author gerd
 * 
 */
@Component("ZIApplicationService")
@Scope("request")
public class ZApplicationServiceSpring implements ZIApplicationService
{
  private final ZIApplicationService service;


  public ZApplicationServiceSpring()
  {
    this.service = ZTemplates.getApplicationService();
  }


  public ZIClassRepository getClassRepository()
  {
    return service.getClassRepository();
  }


  public ZIRenderApplicationContext getRenderApplicationContext()
  {
    return service.getRenderApplicationContext();
  }


  public ZIActionApplicationContext getActionApplicationContext()
  {
    return service.getActionApplicationContext();
  }
}
