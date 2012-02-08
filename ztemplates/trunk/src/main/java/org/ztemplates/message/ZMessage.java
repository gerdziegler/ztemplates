package org.ztemplates.message;

import org.apache.commons.lang.StringUtils;

public class ZMessage
{
  public static final String INFO = "INFO";

  public static final String WARNING = "WARNING";

  public static final String ERROR = "ERROR";

  private final String type;

  private final String text;


  public ZMessage(String type,
      String text)
  {
    this.type = type;
    this.text = text;
  }


  public boolean equals(Object other)
  {
    if (other == null)
    {
      return false;
    }
    ZMessage msg = (ZMessage) other;
    if (!type.equals(msg.type))
    {
      return false;
    }
    //can be null, so must test like this
    if (!StringUtils.equals(text, msg.text))
    {
      return false;
    }
    return true;
  }


  public int hashCode()
  {
    int ret = type.hashCode();
    if (text != null)
    {
      ret = ret + text.hashCode();
    }
    return ret;
  }


  public String getText()
  {
    return text;
  }


  public String getType()
  {
    return type;
  }


  public boolean isType(String type)
  {
    return this.type.equals(type);
  }


  public boolean isError()
  {
    return isType(ERROR);
  }


  public boolean isWarning()
  {
    return isType(WARNING);
  }


  public boolean isInfo()
  {
    return isType(INFO);
  }


  @Override
  public String toString()
  {
    return "[ZMessage " + type + " " + text + "]";
  }
}
