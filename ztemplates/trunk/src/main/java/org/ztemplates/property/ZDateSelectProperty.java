package org.ztemplates.property;

import java.text.DateFormat;
import java.util.Date;

import org.ztemplates.marshaller.ZDateMarshaller;

public class ZDateSelectProperty extends ZSelectProperty<Date>
{
  public ZDateSelectProperty(DateFormat df)
  {
    super(new ZDateMarshaller(df));
  }
}