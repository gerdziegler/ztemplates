package org.ztemplates.form.impl;

import org.ztemplates.property.ZOperation;

public class ZOperationWrapper
{
  private final String name;

  private final ZOperation operation;


  public ZOperationWrapper(String name,
      ZOperation operation)
  {
    super();
    this.name = name;
    this.operation = operation;
  }


  public String getName()
  {
    return name;
  }


  public ZOperation getOperation()
  {
    return operation;
  }

}
