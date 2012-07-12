package org.ztemplates.render.impl;

import java.lang.reflect.Method;

import org.ztemplates.render.ZIRenderDecorator;

public class ZExposedMethod implements ZIExposedValue
{
  private final String name;

  private final Method method;

  private final boolean render;

  private final Class<? extends ZIRenderDecorator> decorator;


  public ZExposedMethod(String name,
      Method method,
      boolean render,
      Class<? extends ZIRenderDecorator> decorator)
  {
    super();
    this.name = name;
    this.method = method;
    this.render = render;
    this.decorator = decorator;
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


  public Class<? extends ZIRenderDecorator> getDecorator()
  {
    return decorator;
  }
}
