package org.ztemplates.web.standalone;

import java.util.Locale;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZISecurityProvider;

/**
 * runs the runnable in a standalone ztemplates context,
 * so you can use ztemplates outside a http request, in tests, jobs etc.
 * @author gerd
 *
 */
public abstract class ZTemplatesRunnable
{
  static final Logger log = Logger.getLogger(ZTemplatesRunnable.class);

  private Locale locale = Locale.getDefault();

  private ZISecurityProvider securityProvider = new ZISecurityProvider()
  {
    public boolean isUserInRole(String role) throws Exception
    {
      return true;
    }


    public String getUserName() throws Exception
    {
      return ZTemplatesRunnable.class.getName();
    }
  };

  private String applicationName = ZApplicationRepositoryStandalone.DEFAULT_APP_NAME;


  public ZTemplatesRunnable(String applicationName)
  {
    this.applicationName = applicationName;
  }


  public ZTemplatesRunnable(String applicationName,
      Locale locale)
  {
    this.applicationName = applicationName;
    this.locale = locale;
  }


  public ZTemplatesRunnable(String applicationName,
      Locale locale,
      ZISecurityProvider securityProvider)
  {
    this.applicationName = applicationName;
    this.locale = locale;
    this.securityProvider = securityProvider;
  }


  public ZISecurityProvider getSecurityProvider()
  {
    return securityProvider;
  }


  public void setSecurityProvider(ZISecurityProvider securityProvider)
  {
    this.securityProvider = securityProvider;
  }


  public Locale getLocale()
  {
    return locale;
  }


  public void setLocale(Locale locale)
  {
    this.locale = locale;
  }


  protected abstract void runImpl() throws Exception;


  public String getApplicationName()
  {
    return applicationName;
  }


  public void setApplicationName(String applicationName)
  {
    this.applicationName = applicationName;
  }


  public final void run() throws Exception
  {
    log.debug("running standalone runnable for application: " + applicationName);
    ZTemplatesStandalone.init(applicationName, locale, securityProvider);
    try
    {
      runImpl();
    }
    finally
    {
      ZTemplatesStandalone.cleanup();
    }
  }

}
