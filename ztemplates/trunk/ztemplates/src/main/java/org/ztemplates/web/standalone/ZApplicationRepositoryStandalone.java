package org.ztemplates.web.standalone;

import java.util.HashMap;
import java.util.Map;

import org.ztemplates.web.application.ZApplication;

/**
 * Keeps the standalone applications, initialize once at application startup,
 * use multiple times.
 * 
 * @author gerdziegler.de
 * 
 */
public class ZApplicationRepositoryStandalone
{
  private final static Map<String, ZApplication> applications = new HashMap<String, ZApplication>();

  public static final String DEFAULT_APP_NAME = "";

  public static ZApplication getApplication(String applicationName)
  {
    return applications.get(applicationName);
  }


  public static void setApplication(String applicationName, ZApplication application)
  {
    applications.put(applicationName, application);
  }
}
