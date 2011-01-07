package org.ztemplates.render.impl;

import java.lang.reflect.Method;

public class ZExposedMethod implements ZIExposedValue
{
  private final String name;

  private final Method method;

  private final boolean render;


  public ZExposedMethod(String name, Method method, boolean render)
  {
    super();
    this.name = name;
    this.method = method;
    this.render = render;
  }


  @Override
  public String toString()
  {
    return name + "[" + method + "]";
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.render.impl.ZIExposedValue#getName()
   */
  public String getName()
  {
    return name;
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.render.impl.ZIExposedValue#getValue(java.lang.Object)
   */
  public Object getValue(Object obj) throws Exception
  {
    return method.invoke(obj);
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.render.impl.ZIExposedValue#isRender()
   */
  public boolean isRender()
  {
    return render;
  }
}
