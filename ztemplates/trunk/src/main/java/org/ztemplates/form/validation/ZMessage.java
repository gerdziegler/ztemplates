package org.ztemplates.form.validation;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.property.ZProperty;

public class ZMessage
{
  public static final String INFO = "INFO";

  public static final String WARNING = "WARNING";

  public static final String ERROR = "ERROR";

  private final String type;

  private final String text;

  private final List<String> propertyNames = new ArrayList<String>();


  public ZMessage(String type, String text, String... propertyNameArr)
  {
    this.type = type;
    this.text = text;
    for (String propName : propertyNameArr)
    {
      propertyNames.add(propName);
    }
  }


  public static ZMessage create(String type, String text, ZProperty... propertyArr)
  {
    ZMessage ret = new ZMessage(type, text);
    for (ZProperty prop : propertyArr)
    {
      ret.propertyNames.add(prop.getName());
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


  public List<String> getPropertyNames()
  {
    return propertyNames;
  }

}
