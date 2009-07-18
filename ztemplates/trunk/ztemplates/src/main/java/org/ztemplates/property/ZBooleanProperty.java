/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.web.ZTemplates;

public class ZBooleanProperty extends ZProperty<Boolean>
{
  public static final String MESSAGE_ID_ParseException = "ParseException";


  public ZBooleanProperty(String label, Boolean value)
  {
    super(label);
    setValue(value);
  }


  public ZBooleanProperty()
  {
    this(null, Boolean.FALSE);
  }


  public ZBooleanProperty(Boolean value)
  {
    this(null, value);
  }


  public ZBooleanProperty(String label)
  {
    this(label, Boolean.FALSE);
  }


  public Boolean parse(String formattedValue) throws Exception
  {
    try
    {
      Boolean i = Boolean.parseBoolean(formattedValue);
      return i;
    }
    catch (NumberFormatException e)
    {
      if (log.isDebugEnabled())
      {
        log.debug(formattedValue, e);
      }
      throw new Exception(ZTemplates.getMessageService().getMessage(ZBooleanProperty.class
          .getName(),
          "ParseException"));
    }
  }


  public String format(Boolean obj)
  {
    return obj.toString();
  }
}
