/*
 * 17.07.2006 @author www.gerdziegler.de
 */
package org.ztemplates.property;

public class ZStringListProperty extends ZListProperty<String>
{
  public ZStringListProperty()
  {
  }


  public ZStringListProperty(String separator)
  {
    super(separator);
  }


  @Override
  protected String parseListElement(String formattedValue) throws Exception
  {
    return formattedValue;
  }


  @Override
  protected String formatListElement(String obj)
  {
    return obj;
  }
}
