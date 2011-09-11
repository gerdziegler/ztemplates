package org.ztemplates.actions;

import java.util.Enumeration;

public interface ZIActionApplicationContext
{
  public Enumeration<String> getInitParameterNames();


  public String getInitParameter(String name);


  public void setAttribute(String name, Object value);


  public Object getAttribute(String name);


  public void removeAttribute(String name);


  public String getEncoding();


  public String getContextPath();
}
