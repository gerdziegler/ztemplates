/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.json.JSONException;
import org.json.JSONObject;

public class ZJsonProperty extends ZProperty<JSONObject>
{
  public ZJsonProperty()
  {
  }


  public ZJsonProperty(String name)
  {
    super();
    setName(name);
  }


  @Override
  public JSONObject parse(String formattedValue) throws JSONException
  {
    return new JSONObject(formattedValue);
  }


  @Override
  public String format(JSONObject obj)
  {
    return obj.toString();
  }
}
