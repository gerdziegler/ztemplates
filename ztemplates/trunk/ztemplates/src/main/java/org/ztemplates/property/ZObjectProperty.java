/*
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import java.io.Serializable;

import org.ztemplates.actions.util.ZSerializeUtil;

public class ZObjectProperty<T extends Serializable> extends ZProperty<T>
{
  public ZObjectProperty()
  {
  }


  public ZObjectProperty(String label)
  {
    super(label);
  }


  public String format(T obj)
  {
    try
    {
      return ZSerializeUtil.serialize(obj);
    }
    catch (Exception e)
    {
      log.error("format " + obj, e);
      return e.getMessage();
    }
  }


  public T parse(String formattedValue) throws Exception
  {
    if (formattedValue == null)
    {
      return null;
    }

    return (T) ZSerializeUtil.deserialize(formattedValue);
  }
}
