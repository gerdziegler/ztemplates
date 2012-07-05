/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.marshaller.ZStringMarshaller;

public class ZStringProperty extends ZProperty<String>
{
  public ZStringProperty()
  {
    super(new ZStringMarshaller());
  }


  public ZStringProperty(String name)
  {
    super(new ZStringMarshaller());
    setName(name);
  }
}