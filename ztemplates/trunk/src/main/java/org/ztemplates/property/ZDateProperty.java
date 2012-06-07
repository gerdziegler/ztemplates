/*
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import java.text.DateFormat;
import java.util.Date;

import org.ztemplates.marshaller.ZDateMarshaller;

public class ZDateProperty extends ZProperty<Date>
{
  public ZDateProperty(String name,
      DateFormat df)
  {
    super(new ZDateMarshaller(df));
    setName(name);
  }


  public ZDateProperty(DateFormat df)
  {
    super(new ZDateMarshaller(df));
  }
}