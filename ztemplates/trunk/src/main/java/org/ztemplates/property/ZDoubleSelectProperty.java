package org.ztemplates.property;

import org.ztemplates.marshaller.ZDoubleMarshaller;

public class ZDoubleSelectProperty extends ZSelectProperty<Double>
{
  public ZDoubleSelectProperty()
  {
    super(new ZDoubleMarshaller());
  }
}