package org.ztemplates.form.validation;

import org.ztemplates.property.ZProperty;

public class ZErrorMessage extends ZMessage
{

  public ZErrorMessage(String text, String... propertyNameArr)
  {
    super(ZMessage.ERROR, text, propertyNameArr);
  }


  public static ZErrorMessage create(String text, ZProperty... propertyArr)
  {
    ZErrorMessage ret = new ZErrorMessage(text);
    for (ZProperty prop : propertyArr)
    {
      ret.getPropertyNames().add(prop.getName());
    }
    return ret;
  }

}
