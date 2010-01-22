package org.ztemplates.web.request;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ztemplates.web.ZIActionService;
import org.ztemplates.web.ZIApplicationService;
import org.ztemplates.web.ZIEncryptionService;
import org.ztemplates.web.ZIExceptionService;
import org.ztemplates.web.ZIFormService;
import org.ztemplates.web.ZIMessageService;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZISecurityService;
import org.ztemplates.web.ZIService;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.application.ZIServiceFactory;
import org.ztemplates.web.request.impl.ZFormServiceImpl;

/**
 * holds the per thread services of ztemplates in a webapp environment
 * 
 * @author www.gerdziegler.de
 */
public abstract class ZServiceRepositoryStandalone implements ZIServiceRepository
{
  static final Logger log = Logger.getLogger(ZServiceRepositoryStandalone.class);

  private final ZIServiceFactory serviceFactory;

  private ZIApplicationService applicationService;

  private ZIRenderService renderService;

  private ZIActionService actionService;

  private ZIEncryptionService encryptionService;

  private ZISecurityService securityService;

  private ZIExceptionService exceptionService;

  private ZIMessageService messageService;

  private ZIFormService formService;

  private Map<Class, Object> serviceMap = new HashMap<Class, Object>();

  private final Locale locale;

  private final String contextPath;


  public ZServiceRepositoryStandalone(ZIServiceFactory serviceFactory, Locale locale, String contextPath)
  {
    super();
    this.serviceFactory = serviceFactory;
    this.locale = locale;
    this.contextPath = contextPath;
    formService = new ZFormServiceImpl();
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.impl.ZIServiceRepository#getServletService()
   */
  public ZIServletService getServletService()
  {
    return null;
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.impl.ZIServiceRepository#getRenderService()
   */
  public ZIRenderService getRenderService()
  {
    if (renderService == null)
    {
      renderService = serviceFactory.createRenderService(this, contextPath);
    }
    return renderService;
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.impl.ZIServiceRepository#getEncryptionService()
   */
  public ZIEncryptionService getEncryptionService()
  {
    if (encryptionService == null)
    {
      encryptionService = serviceFactory.createEncryptionService(this);
    }
    return encryptionService;
  }


//  /*
//   * (non-Javadoc)
//   * 
//   * @see org.ztemplates.web.impl.ZIServiceRepository#getSecurityService()
//   */
//  public ZISecurityService getSecurityService()
//  {
//    if (securityService == null)
//    {
//      securityService = serviceFactory.createSecurityService(this, request);
//    }
//    return securityService;
//  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.impl.ZIServiceRepository#getExceptionService()
   */
  public ZIExceptionService getExceptionService()
  {
    if (exceptionService == null)
    {
      exceptionService = serviceFactory.createExceptionService(this);
    }
    return exceptionService;
  }


  public ZIActionService getActionService()
  {
    if (actionService == null)
    {
      actionService = serviceFactory.createActionService(this, contextPath);
    }
    return actionService;
  }


  public ZIApplicationService getApplicationService()
  {
    if (applicationService == null)
    {
      applicationService = serviceFactory.createApplicationService(this);
    }
    return applicationService;
  }


  public ZIMessageService getMessageService()
  {
    if (messageService == null)
    {
      messageService = serviceFactory.createMessageService(this, locale);
    }
    return messageService;
  }


  public <T extends ZIService> T getService(Class<T> type) throws Exception
  {
    T ret = (T) serviceMap.get(type);
    if (ret == null)
    {
      ret = serviceFactory.createService(type);
      serviceMap.put(type, ret);
    }
    return ret;
  }


  public ZIFormService getFormService()
  {
    return formService;
  }
}
