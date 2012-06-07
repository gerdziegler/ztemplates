package org.ztemplates.marshaller;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * 
 * @author gerd
 *
 */
public class ZDateMarshaller implements ZIMarshaller<Date>
{
  private final DateFormat df;


  public ZDateMarshaller(DateFormat df)
  {
    super();
    this.df = df;
  }


  public String marshal(Date obj)
  {
    if (obj == null)
    {
      return null;
    }
    return df.format(obj);
  }


  public Date unmarshal(String stringValue) throws ZMarshallerException
  {
    if (stringValue == null || stringValue.length() == 0)
    {
      return null;
    }
    try
    {
      return df.parse(stringValue);
    }
    catch (ParseException e)
    {
      throw new ZMarshallerException(stringValue, e);
    }
  }
}