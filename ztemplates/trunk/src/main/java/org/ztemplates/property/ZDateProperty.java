/*
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

public class ZDateProperty extends ZProperty<Date>
{
  private final DateFormat df;


  public ZDateProperty(String name,
      DateFormat df)
  {
    this(df);
    setName(name);
  }


  public ZDateProperty(DateFormat df)
  {
    this.df = df;
  }


  public String format(Date date)
  {
    return df.format(date);
  }


  public Date parse(String formattedValue) throws ZPropertyException
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
      throw new ZPropertyException(e, this);
    }
  }
}