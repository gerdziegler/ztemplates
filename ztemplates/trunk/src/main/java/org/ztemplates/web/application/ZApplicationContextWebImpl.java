package org.ztemplates.web.application;

import java.util.Enumeration;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZIActionApplicationContext;
import org.ztemplates.render.ZIRenderApplicationContext;

public class ZApplicationContextWebImpl implements ZIRenderApplicationContext, ZIActionApplicationContext
{
  private static final Logger log = Logger.getLogger(ZApplicationContextWebImpl.class);

  private final ServletContext servletContext;

  private final ZIClassRepository classRepository;

  private final String encoding;


  public ZApplicationContextWebImpl(ZIClassRepository classRepository, ServletContext servletContext, String encoding) throws Exception
  {
    super();
    this.classRepository = classRepository;
    this.servletContext = servletContext;
    this.encoding = encoding;
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


  public Enumeration<String> getInitParameterNames()
  {
    return servletContext.getInitParameterNames();
  }


  public ZIClassRepository getClassRepository()
  {
    return classRepository;
  }


  public String getEncoding()
  {
    return encoding;
  }


  public String getContextPath()
  {
    return servletContext.getContextPath();
  }

}
