package org.ztemplates.property;

import org.ztemplates.marshaller.ZLongMarshaller;

public class ZLongSelectProperty extends ZSelectProperty<Long>
{
  public ZLongSelectProperty()
  {
    super(new ZLongMarshaller());
  }
}