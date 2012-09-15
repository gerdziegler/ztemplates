package org.ztemplates.web.request;

import org.ztemplates.web.ZIActionService;
import org.ztemplates.web.ZIApplicationService;
import org.ztemplates.web.ZIEncryptionService;
import org.ztemplates.web.ZIExceptionService;
import org.ztemplates.web.ZIFormService;
import org.ztemplates.web.ZIMessageService;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZISecurityService;
import org.ztemplates.web.ZIServletService;

public abstract class ZServiceRepositoryWrapper implements ZIServiceRepository
{
  private final ZIServiceRepository instance;


  public ZServiceRepositoryWrapper(ZIServiceRepository instance)
  {
    super();
    this.instance = instance;
  }


  @Override
  public ZIApplicationService getApplicationService()
  {
    return instance.getApplicationService();
  }


  @Override
  public ZIServletService getServletService()
  {
    return instance.getServletService();
  }


  @Override
  public ZIRenderService getRenderService()
  {
    return instance.getRenderService();
  }


  @Override
  public ZIActionService getActionService()
  {
    return instance.getActionService();
  }


  @Override
  public ZIFormService getFormService()
  {
    return instance.getFormService();
  }


  @Override
  public ZIEncryptionService getEncryptionService()
  {
    return instance.getEncryptionService();
  }


  @Override
  public ZISecurityService getSecurityService()
  {
    return instance.getSecurityService();
  }


  @Override
  public ZIExceptionService getExceptionService()
  {
    return instance.getExceptionService();
  }


  @Override
  public ZIMessageService getMessageService()
  {
    return instance.getMessageService();
  }
}
