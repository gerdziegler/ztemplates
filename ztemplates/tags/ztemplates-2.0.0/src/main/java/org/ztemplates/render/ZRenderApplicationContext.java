package org.ztemplates.render;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class ZRenderApplicationContext implements ZIRenderApplicationContext
{
  private final Map<String, Object> attributes = new HashMap<String, Object>();

  private final Map<String, String> initParameters = new HashMap<String, String>();


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

}
