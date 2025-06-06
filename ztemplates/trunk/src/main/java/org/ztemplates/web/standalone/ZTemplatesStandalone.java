package org.ztemplates.web.standalone;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassPathScanner;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassPathFilter;
import org.zclasspath.ZIClassRepository;
import org.zclasspath.ZJavaClassPath;
import org.ztemplates.actions.ZActionApplication;
import org.ztemplates.actions.ZIActionApplicationContext;
import org.ztemplates.actions.ZISecurityProvider;
import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.ZMatch;
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
    ZTemplatesStandalone.init(ZApplicationRepositoryStandalone.DEFAULT_APP_NAME, locale, securityProvider);
  }


  /**
   * Use this per thread if you provide a applicationName servletContext initParameter to
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
    ZIServiceRepository repo = ZTemplatesStandalone.createServiceRepository(applicationName, locale, securityProvider);
    ZTemplates.setServiceRepository(repo);
  }


  /**
   * Use this once (at startup) to setup your ztemplates application
   * @param applicationName
   * @param filter
   * @throws Exception
   */
  public static void initApplication(String applicationName, ZIClassPathFilter filter) throws Exception
  {
    ZApplication application = ZApplicationRepositoryStandalone.getApplication(applicationName);
    log.debug("initializing application: " + applicationName);

    log.debug("initializing context...");
    ZClassPathScanner scanner = new ZClassPathScanner();
    scanner.setFilter(filter);
    scanner.setClassPathItems(ZJavaClassPath.getItems());
    log.debug("creating class repository...");
    final ZIClassRepository classRepository = new ZClassRepository(scanner);
    log.debug("creating application...");
    application = ZTemplatesStandaloneApplication.create(classRepository);
    ZApplicationRepositoryStandalone.setApplication(applicationName, application);
    log.debug("context initialized");
    log.debug("application initialized: " + applicationName);
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
      throw new Exception("no ztemplates application found for applicationName: '" + applicationName + "'");
    }
    ZActionApplication actionApplication = application.getActionApplication();
    ZIActionApplicationContext applicationContext = actionApplication.getApplicationContext();

    String scheme = "http";
    String httpsPrefix = applicationContext.getInitParameter(ZMatch.Protocol.HTTPS.getContextParameterName());
    String httpPrefix = applicationContext.getInitParameter(ZMatch.Protocol.HTTP.getContextParameterName());

    String encryptPassword = applicationContext.getInitParameter("encryptPassword");
    String encryptSalt = applicationContext.getInitParameter("encryptSalt");
    String encoding = applicationContext.getEncoding();

    ZMatchTree matchTree = actionApplication.getMatchTree();
    ZIUrlHandler urlHandler = new ZTreeUrlHandler(matchTree, securityProvider, encoding, actionApplication);
    ZIUrlFactory urlFactory = new ZUrlFactory(encoding);
    ZIServletService servletService = null;

    ZIApplicationService applicationService = new ZApplicationServiceImpl(application);
    String contextPath = application.getActionApplication().getApplicationContext().getContextPath();
    ZIActionService actionService = new ZActionServiceImpl(urlHandler, urlFactory, contextPath, scheme, httpPrefix, httpsPrefix);
    ZIRenderService renderService = new ZRenderServiceImpl(application.getRenderApplication(), contextPath);

    ZIEncryptionService encryptionService = new ZEncryptionServiceImpl(encryptPassword, encryptSalt);
    ZISecurityService securityService = new ZSecurityServiceImpl(securityProvider);
    ZIExceptionService exceptionService = new ZExceptionServiceImpl();
    ZIMessageService messageService = new ZMessageServiceStandaloneImpl(locale);

    ZIFormService formService = new ZFormServiceImpl(servletService, applicationService.getActionApplicationContext());
    return new ZTemplatesStandalone(servletService, applicationService, actionService, renderService, encryptionService, securityService, exceptionService,
        messageService, formService);
  }


  public ZTemplatesStandalone(ZIServletService servletService,
      ZIApplicationService applicationService,
      ZIActionService actionService,
      ZIRenderService renderService,
      ZIEncryptionService encryptionService,
      ZISecurityService securityService,
      ZIExceptionService exceptionService,
      ZIMessageService messageService,
      ZIFormService formService)
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
