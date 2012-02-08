package org.ztemplates.message;

public class ZWarningMessage extends ZMessage
{

  public ZWarningMessage(String text)
  {
    super(ZMessage.WARNING, text);
  }

  //
  //  public static ZWarningMessage create(String text, ZProperty... propertyArr)
  //  {
  //    ZWarningMessage ret = new ZWarningMessage(text);
  //    for (ZProperty prop : propertyArr)
  //    {
  //      ret.getPropertyNames().add(prop.getName());
  //    }
  //    return ret;
  //  }
}
