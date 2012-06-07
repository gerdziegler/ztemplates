/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.marshaller.ZBooleanMarshaller;

public class ZBooleanProperty extends ZProperty<Boolean>
{
  public static final String MESSAGE_ID_ParseException = "ParseException";


  public ZBooleanProperty(Boolean value)
  {
    super(new ZBooleanMarshaller());
    setValue(value);
  }


  public ZBooleanProperty(String name)
  {
    super(new ZBooleanMarshaller());
    setName(name);
  }


  public ZBooleanProperty()
  {
    this(Boolean.FALSE);
  }
}