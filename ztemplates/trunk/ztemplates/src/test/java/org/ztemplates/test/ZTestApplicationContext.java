package org.ztemplates.test;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZIActionApplicationContext;
import org.ztemplates.render.ZIRenderApplicationContext;

public class ZTestApplicationContext implements ZIRenderApplicationContext,
    ZIActionApplicationContext
{
  private final Map<String, Object> attributes = new HashMap<String, Object>();

  private final Map<String, String> initParameters = new HashMap<String, String>();

  private String contextPath = null;

  private final ZIClassRepository classRepository;


  public ZTestApplicationContext(ZIClassRepository classRepository)
  {
    this.classRepository = classRepository;
  }


  public String getInitParameter(String name)
  {
    return initParameters.get(name);
  }


  public Enumeration<String> getInitParameterNames()
  {
    return new Vector<String>().elements();
  }


  public Object getAttribute(String name)
  {
    return attributes.get(name);
  }


  public void removeAttribute(String name)
  {
    attributes.remove(name);
  }


  public void setAttribute(String name, Object value)
  {
    attributes.put(name, value);
  }


  public Map<String, String> getInitParameters()
  {
    return initParameters;
  }


  public void setContextPath(String contextPath)
  {
    this.contextPath = contextPath;
  }


  public String getContextPath()
  {
    return contextPath;
  }


  public ZIClassRepository getClassRepository()
  {
    return classRepository;
  }
}
