package org.ztemplates.web.application;

import javax.servlet.ServletContext;

public class ZApplicationRepositoryWeb
{
  private static final String KEY_ZApplication = ZApplication.class.getName();


  public static ZApplication getApplication(ServletContext servletContext)
  {
    return (ZApplication) servletContext.getAttribute(KEY_ZApplication);
  }


  public static void setApplication(ServletContext servletContext, ZApplication application)
  {
    if (application == null)
    {
      servletContext.removeAttribute(KEY_ZApplication);
    }
    else
    {
      servletContext.setAttribute(KEY_ZApplication, application);
    }
  }
}
