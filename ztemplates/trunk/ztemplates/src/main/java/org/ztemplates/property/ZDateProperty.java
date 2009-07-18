/*
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.ztemplates.web.ZTemplates;

public class ZDateProperty extends ZProperty<Date>
{
  public static final String MESSAGE_ID_ParseException = "ParseException";

  private final DateFormat df;


  public ZDateProperty(String label, DateFormat df)
  {
    super(label);
    this.df = df;
  }


  public ZDateProperty(DateFormat df)
  {
    this(null, df);
  }


  public String format(Date date)
  {
    return df.format(date);
  }


  public Date parse(String formattedValue) throws Exception
  {
    if (formattedValue == null)
    {
      return null;
    }

    try
    {
      return df.parse(formattedValue);
    }
    catch (ParseException e)
    {
      throw new Exception(ZTemplates.getMessageService().getMessage(ZDateProperty.class.getName(),
          MESSAGE_ID_ParseException,
          e.getMessage()));
    }
  }

}
