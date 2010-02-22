package org.ztemplates.web.standalone;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZISecureUrlDecorator;
import org.ztemplates.actions.ZISecurityProvider;
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
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.request.ZIServiceRepository;
import org.ztemplates.web.request.impl.ZActionServiceImpl;
import org.ztemplates.web.request.impl.ZApplicationServiceImpl;
import org.ztemplates.web.request.impl.ZEncryptionServiceImpl;
import org.ztemplates.web.request.impl.ZExceptionServiceImpl;
import org.ztemplates.web.request.impl.ZFormServiceImpl;
import org.ztemplates.web.request.impl.ZRenderServiceImpl;
import org.ztemplates.web.request.impl.ZSecurityServiceImpl;

/**
 * holds the per thread services of ztemplates in a standalone environment.
 * 
 * @author www.gerdziegler.de
 */
public class ZTemplatesStandalone implements ZIServiceRepository
{
  static final Logger log = Logger.getLogger(ZTemplatesStandalone.class);

  private final ZIServletService servletService;

  private final ZIApplicationService applicationService;

  private final ZIActionService actionService;

  private final ZIRenderService renderService;

  private final ZIEncryptionService encryptionService;

  private final ZISecurityService securityService;

  private final ZIExceptionService exceptionService;

  private final ZIMessageService messageService;

  private final ZIFormService formService;


  /**
   * calls init() with default applicationName use if your application server
   * does not share classloaders between webapps.
   * 
   * @param locale
   * @param securityProvider
   * @return
   * @throws Exception
   */
  public static void init(Locale locale, ZISecurityProvider securityProvider) throws Exception
  {
    init(ZApplicationRepositoryStandalone.DEFAULT_APP_NAME, locale, securityProvider);
  }


  /**
   * use this if you provide a applicationName servletContext initParameter to
   * explicitly set a application name. This is needed if you explicitly
   * configure your application server to share the classloader between webapps.
   * 
   * @param applicationName
   * @param locale
   * @param securityProvider
   * @throws Exception
   */
  public static void init(String applicationName, Locale locale, ZISecurityProvider securityProvider) throws Exception
  {
    ZIServiceRepository repo = createServiceRepository(applicationName, locale, securityProvider);
    ZTemplates.setServiceRepository(repo);
  }


  public static void cleanup() throws Exception
  {
    ZTemplates.setServiceRepository(null);
  }


  public static ZIServiceRepository createServiceRepository(String applicationName, Locale locale, ZISecurityProvider securityProvider) throws Exception
  {
    ZApplication application = ZApplicationRepositoryStandalone.getApplication(applicationName);
    if (application == null)
    {
      throw new Exception("no ztemplates application found for applicationName " + applicationName);
    }
    String prefix = application.getActionApplication().getApplicationContext().getInitParameter("prefix");
    String encryptPassword = application.getActionApplication().getApplicationContext().getInitParameter("encryptPassword");
    String encryptSalt = application.getActionApplication().getApplicationContext().getInitParameter("encryptSalt");

    ZISecureUrlDecorator secureUrlDecorator = application.getActionApplication().getSecureUrlDecorator();
    ZMatchTree matchTree = application.getActionApplication().getMatchTree();
    ZIUrlHandler urlHandler = new ZTreeUrlHandler(matchTree, securityProvider, secureUrlDecorator, application.getActionApplication().getApplicationContext());
    ZIUrlFactory urlFactory = new ZUrlFactory(secureUrlDecorator);
    ZIServletService servletService = null;

    ZIApplicationService applicationService = new ZApplicationServiceImpl(application);
    ZIActionService actionService = new ZActionServiceImpl(urlHandler, urlFactory, applicationName, prefix);
    ZIRenderService renderService = new ZRenderServiceImpl(application.getRenderApplication(), applicationName);

    ZIEncryptionService encryptionService = new ZEncryptionServiceImpl(encryptPassword, encryptSalt);
    ZISecurityService securityService = new ZSecurityServiceImpl(securityProvider, secureUrlDecorator);
    ZIExceptionService exceptionService = new ZExceptionServiceImpl();
    ZIMessageService messageService = new ZMessageServiceStandaloneImpl(locale);

    ZIFormService formService = new ZFormServiceImpl(applicationService.getActionApplicationContext());
    return new ZTemplatesStandalone(servletService, applicationService, actionService, renderService, encryptionService, securityService, exceptionService,
        messageService, formService);
  }


  public ZTemplatesStandalone(ZIServletService servletService, ZIApplicationService applicationService, ZIActionService actionService,
      ZIRenderService renderService, ZIEncryptionService encryptionService, ZISecurityService securityService, ZIExceptionService exceptionService,
      ZIMessageService messageService, ZIFormService formService)
  {
    super();
    this.servletService = servletService;
    this.applicationService = applicationService;
    this.actionService = actionService;
    this.renderService = renderService;
    this.encryptionService = encryptionService;
    this.securityService = securityService;
    this.exceptionService = exceptionService;
    this.messageService = messageService;
    this.formService = formService;
  }


  public ZIServletService getServletService()
  {
    return servletService;
  }


  public ZIApplicationService getApplicationService()
  {
    return applicationService;
  }


  public ZIRenderService getRenderService()
  {
    return renderService;
  }


  public ZIActionService getActionService()
  {
    return actionService;
  }


  public ZIEncryptionService getEncryptionService()
  {
    return encryptionService;
  }


  public ZISecurityService getSecurityService()
  {
    return securityService;
  }


  public ZIExceptionService getExceptionService()
  {
    return exceptionService;
  }


  public ZIMessageService getMessageService()
  {
    return messageService;
  }


  public ZIFormService getFormService()
  {
    return formService;
  }
}
