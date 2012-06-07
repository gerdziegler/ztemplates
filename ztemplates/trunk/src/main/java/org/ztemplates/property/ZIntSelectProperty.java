package org.ztemplates.property;

import org.ztemplates.marshaller.ZIntMarshaller;

public class ZIntSelectProperty extends ZSelectProperty<Integer>
{
  public ZIntSelectProperty()
  {
    super(new ZIntMarshaller());
  }
}