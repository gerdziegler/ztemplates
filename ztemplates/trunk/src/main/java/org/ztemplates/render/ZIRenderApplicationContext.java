package org.ztemplates.render;

import java.util.Enumeration;

public interface ZIRenderApplicationContext
{
  public Enumeration<String> getInitParameterNames();


  public String getInitParameter(String name);


  public void setAttribute(String name, Object value);


  public Object getAttribute(String name);


  public void removeAttribute(String name);


  public String getEncoding();
}
