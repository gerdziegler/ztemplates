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
  public JSONObject parse(String formattedValue) throws ZPropertyException
  {
    try
    {
      return new JSONObject(formattedValue);
    }
    catch (JSONException e)
    {
      throw new ZPropertyException(e, this);
    }
  }


  @Override
  public String format(JSONObject obj)
  {
    return obj.toString();
  }
}
