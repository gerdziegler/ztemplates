package org.ztemplates.actions.util.impl;

import java.lang.reflect.Field;

public class ZSetParameter_Field implements ZSetParameter
{
  private final Field field;


  public ZSetParameter_Field(Field field)
  {
    super();
    this.field = field;
  }


  public void setValue(Object obj, Object value) throws IllegalArgumentException, IllegalAccessException
  {
    if (!field.isAccessible())
    {
      field.setAccessible(true);
    }
    field.set(obj, value);
  }


  public Class getType()
  {
    return field.getType();
  }
}
