package org.ztemplates.web.request;

import org.ztemplates.web.ZIActionService;
import org.ztemplates.web.ZIApplicationService;
import org.ztemplates.web.ZIFormService;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZIServletService;

/**
 * The core request-time environment, these services are fixed and cannot be
 * changed by application
 * 
 * @author gerdziegler.de
 * 
 */
public interface ZICoreServiceRepository
{
  public ZIApplicationService getApplicationService();


  public ZIServletService getServletService();


  public ZIRenderService getRenderService();


  public ZIActionService getActionService();


  public ZIFormService getFormService();
}
