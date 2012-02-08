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


  public T parse(String formattedValue) throws ZPropertyException
  {
    if (formattedValue == null)
    {
      return null;
    }

    try
    {
      return (T) ZSerializeUtil.deserialize(formattedValue);
    }
    catch (Exception e)
    {
      throw new ZPropertyException(e, this);
    }
  }
}
