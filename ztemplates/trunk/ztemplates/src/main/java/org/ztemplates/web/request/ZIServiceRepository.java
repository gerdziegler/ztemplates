package org.ztemplates.web.request;

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

/**
 * The request-time environment
 * 
 * @author gerdziegler.de
 * 
 */
public interface ZIServiceRepository
{
  public ZIServletService getServletService();


  public ZIRenderService getRenderService();


  public ZIActionService getActionService();


  public ZIEncryptionService getEncryptionService();


  public ZISecurityService getSecurityService();


  public ZIExceptionService getExceptionService();


  public ZIApplicationService getApplicationService();


  public ZIMessageService getMessageService();


  public ZIFormService getFormService();


  /**
   * user defined services, create in ZIServiceFactory, add ContextListener to
   * your webapp
   * 
   * <code>
   ZIApplicationRepository applicationRepository = new ZApplicationRepository(req.getSession().getServletContext());
   ZApplication application = applicationRepository.getApplication();
   </code>
   * 
   * 
   * @param <T>
   * @param type
   * @return
   * @throws Exception
   */
  public <T extends ZIService> T getService(Class<T> type) throws Exception;
}
