/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

public class ZStringProperty extends ZProperty<String>
{
  public ZStringProperty()
  {
  }


  @Override
  public String parse(String formattedValue)
  {
    return formattedValue;
  }


  @Override
  public String format(String obj)
  {
    return obj;
  }
}
