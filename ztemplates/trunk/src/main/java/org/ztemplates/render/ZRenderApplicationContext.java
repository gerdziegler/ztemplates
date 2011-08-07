package org.ztemplates.render;

import java.io.InputStream;
import java.util.Enumeration;
import java.util.Map;
import java.util.Vector;

public class ZRenderApplicationContext implements ZIRenderApplicationContext
{
  private final Map<String, Object> attributes;

  private final Map<String, String> initParameters;

  private final String encoding;

  private boolean devMode;


  public ZRenderApplicationContext(String encoding,
      Map<String, String> initParameters,
      Map<String, Object> attributes)
  {
    super();
    this.encoding = encoding;
    this.initParameters = initParameters;
    this.attributes = attributes;
    this.devMode = "true".equals(getInitParameter("ztemplates.devMode"));
  }


  public InputStream getResourceAsStream(String path)
  {
    return getClass().getClassLoader().getResourceAsStream(path);
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


  public String getEncoding()
  {
    return encoding;
  }


  public boolean isDevMode()
  {
    return devMode;
  }


  public void setDevMode(boolean devMode)
  {
    this.devMode = devMode;

  }

}
