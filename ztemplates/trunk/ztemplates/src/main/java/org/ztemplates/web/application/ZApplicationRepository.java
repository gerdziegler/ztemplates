package org.ztemplates.web.application;

import javax.servlet.ServletContext;

public class ZApplicationRepository implements ZIApplicationRepository
{
  private static final String KEY_ZApplication = ZApplication.class.getName();

  private final ServletContext servletContext;


  public ZApplicationRepository(ServletContext servletContext)
  {
    this.servletContext = servletContext;
  }


  public ZApplication getApplication()
  {
    return (ZApplication) servletContext.getAttribute(KEY_ZApplication);
  }


  public void setApplication(ZApplication application)
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
