package org.ztemplates.validation;

import org.ztemplates.property.ZProperty;

public class ZInfoMessage extends ZMessage
{

  public ZInfoMessage(String text, String... propertyNameArr)
  {
    super(ZMessage.INFO, text, propertyNameArr);
  }


  public static ZInfoMessage create(String text, ZProperty... propertyArr)
  {
    ZInfoMessage ret = new ZInfoMessage(text);
    for (ZProperty prop : propertyArr)
    {
      ret.getPropertyNames().add(prop.getName());
    }
    return ret;
  }
}
