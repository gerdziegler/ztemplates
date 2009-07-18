/*
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

public class ZLongProperty extends ZProperty<Long>
{

  public ZLongProperty()
  {
  }


  public ZLongProperty(String label)
  {
    super(label);
  }


  public Long parse(String formattedValue) throws Exception
  {
    Long i = Long.parseLong(formattedValue);
    return i;
  }


  public String format(Long obj)
  {
    return obj == null ? null : obj.toString();
  }
}
