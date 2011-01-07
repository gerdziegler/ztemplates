/*
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import java.text.DateFormat;
import java.util.Date;

public class ZDateProperty extends ZProperty<Date>
{
  private final DateFormat df;


  public ZDateProperty(DateFormat df)
  {
    this.df = df;
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

    return df.parse(formattedValue);
  }
}
