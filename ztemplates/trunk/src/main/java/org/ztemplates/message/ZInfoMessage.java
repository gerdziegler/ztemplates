package org.ztemplates.message;

public class ZInfoMessage extends ZMessage
{

  public ZInfoMessage(String text)
  {
    super(ZMessage.INFO, text);
  }

  //  public static ZInfoMessage create(String text, ZProperty... propertyArr)
  //  {
  //    ZInfoMessage ret = new ZInfoMessage(text);
  //    for (ZProperty prop : propertyArr)
  //    {
  //      ret.getPropertyNames().add(prop.getName());
  //    }
  //    return ret;
  //  }
}
