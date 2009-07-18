/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.web.ZTemplates;

public class ZDoubleProperty extends ZProperty<Double>
{
  public static final String MESSAGE_ID_ParseException = "ParseException";


  public ZDoubleProperty()
  {
  }


  public ZDoubleProperty(String label)
  {
    super(label);
  }


  @Override
  public Double parse(String formattedValue) throws Exception
  {
    try
    {
      if (formattedValue == null || formattedValue.length() == 0)
      {
        return null;
      }
      Double i = Double.parseDouble(formattedValue);
      return i;
    }
    catch (NumberFormatException e)
    {
      throw new Exception(ZTemplates.getMessageService()
          .getMessage(ZDoubleProperty.class.getName(), "ParseException"));
    }
  }


  @Override
  public String format(Double obj)
  {
    return obj == null ? "" : obj.toString();
  }
}
