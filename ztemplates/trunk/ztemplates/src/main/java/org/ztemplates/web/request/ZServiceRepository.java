package org.ztemplates.web.request;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * holds the request-time services of ztemplates
 * 
 * @author www.gerdziegler.de
 */
public class ZServiceRepository implements ZIServiceRepository
{
  static final Logger log = Logger.getLogger(ZServiceRepository.class);

  private final ZIServiceFactory serviceFactory;

  private final HttpServletRequest request;

  private final HttpServletResponse response;

  private ZIApplicationService applicationService;

  private ZIServletService servletService;

  private ZIRenderService renderService;

  private ZIActionService actionService;

  private ZIEncryptionService encryptionService;

  private ZISecurityService securityService;

  private ZIExceptionService exceptionService;

  private ZIMessageService messageService;

  private ZIFormService formService;

  private Map<Class, Object> serviceMap = new HashMap<Class, Object>();


  public ZServiceRepository(ZIServiceFactory serviceFactory,
      final HttpServletRequest request,
      final HttpServletResponse response)
  {
    super();
    this.serviceFactory = serviceFactory;
    this.request = request;
    this.response = response;
    servletService = serviceFactory.createServletService(this, request, response);
    formService = new ZFormServiceImpl(servletService);
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.impl.ZIServiceRepository#getServletService()
   */
  public ZIServletService getServletService()
  {
    return servletService;
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
      renderService = serviceFactory.createRenderService(this, request, response);
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
      encryptionService = serviceFactory.createEncryptionService(this, request, response);
    }
    return encryptionService;
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.impl.ZIServiceRepository#getSecurityService()
   */
  public ZISecurityService getSecurityService()
  {
    if (securityService == null)
    {
      securityService = serviceFactory.createSecurityService(this, request, response);
    }
    return securityService;
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.impl.ZIServiceRepository#getExceptionService()
   */
  public ZIExceptionService getExceptionService()
  {
    if (exceptionService == null)
    {
      exceptionService = serviceFactory.createExceptionService(this, request, response);
    }
    return exceptionService;
  }


  public ZIActionService getActionService()
  {
    if (actionService == null)
    {
      actionService = serviceFactory.createActionService(this, request, response);
    }
    return actionService;
  }


  public ZIApplicationService getApplicationService()
  {
    if (applicationService == null)
    {
      applicationService = serviceFactory.createApplicationService(this, request, response);
    }
    return applicationService;
  }


  public ZIMessageService getMessageService()
  {
    if (messageService == null)
    {
      messageService = serviceFactory.createMessageService(this, request, response);
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
