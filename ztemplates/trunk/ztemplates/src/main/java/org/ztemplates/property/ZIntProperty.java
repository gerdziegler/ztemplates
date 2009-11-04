/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.web.ZTemplates;

public class ZIntProperty extends ZProperty<Integer>
{
  public static final String MESSAGE_ID_ParseException = "ParseException";


  public ZIntProperty()
  {
  }


  public ZIntProperty(String label)
  {
    super(label);
  }


  @Override
  public Integer parse(String formattedValue) throws Exception
  {
    try
    {
      if (formattedValue == null || formattedValue.length() == 0)
      {
        return null;
      }
      Integer i = Integer.valueOf(formattedValue);
      return i;
    }
    catch (NumberFormatException e)
    {
      throw new Exception(ZTemplates.getMessageService().getMessage(ZIntProperty.class.getName(),
          "ParseException"));
    }
  }


  @Override
  public String format(Integer obj)
  {
    return obj == null ? "" : obj.toString();
  }
}
