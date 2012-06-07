/*
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.marshaller.ZExceptionMarshaller;

public class ZExceptionProperty extends ZProperty<Throwable>
{
  public ZExceptionProperty()
  {
    super(new ZExceptionMarshaller());
  }
}