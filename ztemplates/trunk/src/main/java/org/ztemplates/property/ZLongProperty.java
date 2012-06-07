/*
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.marshaller.ZLongMarshaller;

public class ZLongProperty extends ZProperty<Long>
{

  public ZLongProperty()
  {
    super(new ZLongMarshaller());
  }


  public ZLongProperty(String name)
  {
    super(new ZLongMarshaller());
    setName(name);
  }
}