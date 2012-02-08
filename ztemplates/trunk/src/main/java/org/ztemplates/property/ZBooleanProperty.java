/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;


public class ZBooleanProperty extends ZProperty<Boolean>
{
  public static final String MESSAGE_ID_ParseException = "ParseException";


  public ZBooleanProperty(Boolean value)
  {
    setValue(value);
  }


  public ZBooleanProperty(String name)
  {
    setName(name);
  }


  public ZBooleanProperty()
  {
    this(Boolean.FALSE);
  }


  public Boolean parse(String formattedValue) throws ZPropertyException
  {
    try
    {
      Boolean i = Boolean.valueOf(formattedValue);
      return i;
    }
    catch (NumberFormatException e)
    {
      if (log.isDebugEnabled())
      {
        log.debug(formattedValue, e);
      }
      throw new ZPropertyException(e, this);
    }
  }


  public String format(Boolean obj)
  {
    return obj.toString();
  }
}
