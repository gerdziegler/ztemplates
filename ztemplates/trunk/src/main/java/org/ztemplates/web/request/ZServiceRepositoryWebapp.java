package org.ztemplates.web.request;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIActionApplicationContext;
import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.ZUrlFactory;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.actions.urlhandler.tree.ZTreeUrlHandler;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.web.ZIActionService;
import org.ztemplates.web.ZIApplicationService;
import org.ztemplates.web.ZIEncryptionService;
import org.ztemplates.web.ZIExceptionService;
import org.ztemplates.web.ZIFormService;
import org.ztemplates.web.ZIMessageService;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZISecurityService;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.application.ZIServiceFactory;
import org.ztemplates.web.request.impl.ZActionServiceImpl;
import org.ztemplates.web.request.impl.ZApplicationServiceImpl;
import org.ztemplates.web.request.impl.ZFormServiceImpl;
import org.ztemplates.web.request.impl.ZRenderServiceImpl;
import org.ztemplates.web.request.impl.ZServletServiceImpl;

/**
 * holds the per thread services of ztemplates in a webapp environment, uses
 * factory to lazy initialize services
 * 
 * @author www.gerdziegler.de
 */
public class ZServiceRepositoryWebapp implements ZIServiceRepository
{
  static final Logger log = Logger.getLogger(ZServiceRepositoryWebapp.class);

  private final ZApplication application;

  private final ZIServiceFactory serviceFactory;

  private final ZIApplicationService applicationService;

  private final ZIServletService servletService;

  private final ZIRenderService renderService;

  private final ZIActionService actionService;

  private final ZISecurityService securityService;

  private ZIEncryptionService encryptionService;

  private ZIExceptionService exceptionService;

  private ZIMessageService messageService;

  private ZIFormService formService;


  public ZServiceRepositoryWebapp(ZApplication application, ZIServiceFactory serviceFactory, final HttpServletRequest request,
      final HttpServletResponse response)
  {
    super();
    this.application = application;
    this.serviceFactory = serviceFactory;
    String contextPath = request.getContextPath();

    applicationService = new ZApplicationServiceImpl(application);
    securityService = serviceFactory.createSecurityService(application);

    ZIActionApplicationContext applicationContext = application.getActionApplication().getApplicationContext();

    String prefix = applicationContext.getInitParameter("prefix");
    String encoding = applicationContext.getEncoding();
    response.setCharacterEncoding(encoding);
    ZMatchTree matchTree = application.getActionApplication().getMatchTree();

    ZIUrlHandler urlHandler = new ZTreeUrlHandler(matchTree, securityService.getSecurityProvider(), securityService.getSecureUrlDecorator(),
        applicationContext, encoding);
    ZIUrlFactory urlFactory = new ZUrlFactory(securityService.getSecureUrlDecorator(), encoding);
    actionService = new ZActionServiceImpl(urlHandler, urlFactory, contextPath, prefix);

    this.renderService = new ZRenderServiceImpl(application.getRenderApplication(), contextPath);
    this.servletService = new ZServletServiceImpl(request, response, actionService, renderService, encoding);
    this.formService = new ZFormServiceImpl(servletService, applicationService.getActionApplicationContext());
  }


  public ZIActionService getActionService()
  {
    return actionService;
  }


  public ZIServletService getServletService()
  {
    return servletService;
  }


  public ZIRenderService getRenderService()
  {
    return renderService;
  }


  public ZIApplicationService getApplicationService()
  {
    return applicationService;
  }


  public ZIFormService getFormService()
  {
    return formService;
  }


  public ZISecurityService getSecurityService()
  {
    return securityService;
  }


  public ZIEncryptionService getEncryptionService()
  {
    if (encryptionService == null)
    {
      encryptionService = serviceFactory.createEncryptionService(application);
    }
    return encryptionService;
  }


  public ZIExceptionService getExceptionService()
  {
    if (exceptionService == null)
    {
      exceptionService = serviceFactory.createExceptionService(application);
    }
    return exceptionService;
  }


  public ZIMessageService getMessageService()
  {
    if (messageService == null)
    {
      messageService = serviceFactory.createMessageService(application);
    }
    return messageService;
  }
}
