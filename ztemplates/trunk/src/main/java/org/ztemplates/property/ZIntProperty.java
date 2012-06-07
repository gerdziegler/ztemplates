/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.marshaller.ZIntMarshaller;

public class ZIntProperty extends ZProperty<Integer>
{
  public ZIntProperty()
  {
    super(new ZIntMarshaller());
  }


  public ZIntProperty(String name)
  {
    super(new ZIntMarshaller());
    setName(name);
  }
}