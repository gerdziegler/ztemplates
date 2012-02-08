/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;


public class ZDoubleProperty extends ZProperty<Double>
{
  public static final String MESSAGE_ID_ParseException = "ParseException";


  public ZDoubleProperty()
  {
  }


  public ZDoubleProperty(String name)
  {
    setName(name);
  }


  @Override
  public Double parse(String formattedValue) throws ZPropertyException
  {
    try
    {
      if (formattedValue == null || formattedValue.length() == 0)
      {
        return null;
      }
      Double i = Double.valueOf(formattedValue);
      return i;
    }
    catch (NumberFormatException e)
    {
      throw new ZPropertyException(e, this);
    }
  }


  @Override
  public String format(Double obj)
  {
    return obj == null ? "" : obj.toString();
  }
}
