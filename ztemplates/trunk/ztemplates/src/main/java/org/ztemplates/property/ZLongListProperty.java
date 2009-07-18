/*
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

public class ZLongListProperty extends ZListProperty<Long>
{

  public ZLongListProperty()
  {
  }


  public ZLongListProperty(String separator)
  {
    super(separator);
  }


  @Override
  protected Long parseListElement(String formattedValue) throws Exception
  {
    Long i = Long.parseLong(formattedValue);
    return i;
  }


  @Override
  protected String formatListElement(Long obj)
  {
    return obj == null ? null : obj.toString();
  }
}
