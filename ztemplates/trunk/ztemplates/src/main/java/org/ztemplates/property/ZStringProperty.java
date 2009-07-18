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

  public ZStringProperty(String label)
  {
    super(label);
  }
  
  public String parse(String formattedValue) throws Exception
  {
    return formattedValue;
  }


  public String format(String obj)
  {
    return obj;
  }
}
