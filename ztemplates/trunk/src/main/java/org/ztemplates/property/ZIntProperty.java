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


  public ZIntProperty(String name)
  {
    setName(name);
  }


  @Override
  public Integer parse(String formattedValue) throws ZPropertyException
  {
    if (formattedValue == null || formattedValue.length() == 0)
    {
      return null;
    }
    try
    {
      Integer i = Integer.valueOf(formattedValue);
      return i;
    }
    catch (NumberFormatException e)
    {
      throw new ZPropertyException(e, this);
    }
  }


  @Override
  public String format(Integer obj)
  {
    return obj == null ? "" : obj.toString();
  }
}
