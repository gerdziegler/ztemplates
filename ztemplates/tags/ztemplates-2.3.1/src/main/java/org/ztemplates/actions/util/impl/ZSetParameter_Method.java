package org.ztemplates.actions.util.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ZSetParameter_Method implements ZSetParameter
{
  private final Method method;


  public ZSetParameter_Method(Method method)
  {
    super();
    this.method = method;
  }


  public void setValue(Object obj, Object value) throws IllegalAccessException, InvocationTargetException
  {
    method.invoke(obj, value);
  }


  public Class getType() throws Exception
  {
    Class[] types = method.getParameterTypes();

    if (types.length != 1)
    {
      throw new Exception("method must have exactly one parameter type: " + method);
    }
    return types[0];
  }
}
