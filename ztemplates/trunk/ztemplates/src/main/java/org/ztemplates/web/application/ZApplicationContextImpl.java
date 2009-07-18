package org.ztemplates.web.application;

import java.util.Enumeration;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZIActionApplicationContext;
import org.ztemplates.render.ZIRenderApplicationContext;

public class ZApplicationContextImpl implements ZIRenderApplicationContext,
    ZIActionApplicationContext
{
  private static final Logger log = Logger.getLogger(ZApplicationContextImpl.class);

  private final ServletContext servletContext;

  private final ZIClassRepository classRepository;


  public ZApplicationContextImpl(ZIClassRepository classRepository, ServletContext servletContext)
      throws Exception
  {
    super();
    this.classRepository = classRepository;
    this.servletContext = servletContext;
  }


  public String getInitParameter(String name)
  {
    return servletContext.getInitParameter(name);
  }


  public Object getAttribute(String name)
  {
    return servletContext.getAttribute(name);
  }


  public void removeAttribute(String name)
  {
    servletContext.removeAttribute(name);
  }


  public void setAttribute(String name, Object value)
  {
    servletContext.setAttribute(name, value);
  }


  // public String getContextPath()
  // {
  // return contextPath;
  // }

  public Enumeration<String> getInitParameterNames()
  {
    return servletContext.getAttributeNames();
  }


  public ZIClassRepository getClassRepository()
  {
    return classRepository;
  }
}
