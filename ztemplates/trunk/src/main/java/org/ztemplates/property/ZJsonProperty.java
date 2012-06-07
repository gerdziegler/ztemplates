/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.json.JSONObject;
import org.ztemplates.marshaller.ZJsonObjectMarshaller;

/**
 * 
 * @author gerd
 *
 */
public class ZJsonProperty extends ZProperty<JSONObject>
{
  public ZJsonProperty()
  {
    super(new ZJsonObjectMarshaller());
  }


  public ZJsonProperty(String name)
  {
    super(new ZJsonObjectMarshaller());
    setName(name);
  }
}