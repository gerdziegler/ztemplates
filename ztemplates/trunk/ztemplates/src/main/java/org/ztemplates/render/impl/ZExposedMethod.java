package org.ztemplates.render.impl;

import java.lang.reflect.Method;

public class ZExposedMethod
{
  private final String name;

  private final Method method;


  public ZExposedMethod(String name, Method method)
  {
    super();
    this.name = name;
    this.method = method;
  }


  public String getName()
  {
    return name;
  }


  public Method getMethod()
  {
    return method;
  }
}
