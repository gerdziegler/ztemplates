/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

public class ZIntProperty extends ZProperty<Integer>
{
  public ZIntProperty()
  {
  }


  @Override
  public Integer parse(String formattedValue) throws Exception
  {
    if (formattedValue == null || formattedValue.length() == 0)
    {
      return null;
    }
    Integer i = Integer.valueOf(formattedValue);
    return i;
  }


  @Override
  public String format(Integer obj)
  {
    return obj == null ? "" : obj.toString();
  }
}
