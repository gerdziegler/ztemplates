package org.ztemplates.property;

public class ZStringSelectProperty extends ZSelectProperty<String>
{
  public String computeDisplayValue(String value)
  {
    return value;
  }


  public String format(String s)
  {
    return s;
  }


  public String parse(String stringValue) throws Exception
  {
    return stringValue;
  }

}
