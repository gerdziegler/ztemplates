package org.ztemplates.validation;

import org.ztemplates.property.ZProperty;

public class ZWarningMessage extends ZMessage
{

  public ZWarningMessage(String text, String... propertyNameArr)
  {
    super(ZMessage.WARNING, text, propertyNameArr);
  }


  public static ZWarningMessage create(String text, ZProperty... propertyArr)
  {
    ZWarningMessage ret = new ZWarningMessage(text);
    for (ZProperty prop : propertyArr)
    {
      ret.getPropertyNames().add(prop.getName());
    }
    return ret;
  }
}
