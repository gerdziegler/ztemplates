package org.ztemplates.test.property;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.property.ZStringProperty;

/**
 */
@ZMatch("/HandlerNotAnnotated/${var1}")
public class HandlerNotAnnotated
{
  private final ZStringProperty var1 = new ZStringProperty();


  public ZStringProperty getVar1()
  {
    return var1;
  }
}
