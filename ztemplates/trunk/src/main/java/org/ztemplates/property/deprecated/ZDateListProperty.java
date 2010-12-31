/*
 * 11.12.2005 @author www.gerdziegler.de
 */
package org.ztemplates.property.deprecated;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

import org.ztemplates.web.ZTemplates;

@Deprecated
public class ZDateListProperty extends ZListProperty<Date>
{
  private final DateFormat df;


  public ZDateListProperty(DateFormat df)
  {
    this.df = df;
  }


  public ZDateListProperty(String separator, DateFormat df)
  {
    super(separator);
    this.df = df;
  }


  @Override
  protected String formatListElement(Date date)
  {
    return df.format(date);
  }


  @Override
  protected Date parseListElement(String formattedValue) throws Exception
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
      throw new Exception(ZTemplates.getMessageService().getMessage(ZDateListProperty.class
          .getName(),
          "ParseException"));
    }
  }

}
