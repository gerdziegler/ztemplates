package org.ztemplates.form.impl;

import org.ztemplates.property.ZProperty;

public class ZPropertyWrapper
{
  private final String name;

  private final ZProperty property;


  public ZPropertyWrapper(String name,
      ZProperty property)
  {
    super();
    this.name = name;
    this.property = property;
  }


  public String getName()
  {
    return name;
  }


  public ZProperty getProperty()
  {
    return property;
  }

}
