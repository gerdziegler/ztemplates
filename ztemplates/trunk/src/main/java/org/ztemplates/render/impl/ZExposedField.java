package org.ztemplates.render.impl;

import java.lang.reflect.Field;

public class ZExposedField implements ZIExposedValue
{
  private final String name;

  private final Field field;

  private final boolean render;


  public ZExposedField(String name,
      Field field,
      boolean render)
  {
    super();
    this.name = name;
    this.field = field;
    this.render = render;
  }


  @Override
  public String toString()
  {
    return name + "[" + field + "]";
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
    if (!field.isAccessible())
    {
      field.setAccessible(true);
    }
    return field.get(obj);
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
