/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.marshaller.ZDoubleMarshaller;

public class ZDoubleProperty extends ZProperty<Double>
{
  public ZDoubleProperty()
  {
    super(new ZDoubleMarshaller());
  }


  public ZDoubleProperty(String name)
  {
    super(new ZDoubleMarshaller());
    setName(name);
  }
}