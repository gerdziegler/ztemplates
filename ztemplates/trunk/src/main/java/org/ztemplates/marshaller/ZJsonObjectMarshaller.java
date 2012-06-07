package org.ztemplates.marshaller;

import org.json.JSONException;
import org.json.JSONObject;

public class ZJsonObjectMarshaller implements ZIMarshaller<JSONObject>
{

  public String marshal(JSONObject obj)
  {
    if (obj == null)
    {
      return null;
    }
    return obj.toString();
  }


  public JSONObject unmarshal(String stringValue) throws ZMarshallerException
  {
    if (stringValue == null || stringValue.length() == 0)
    {
      return null;
    }
    try
    {
      return new JSONObject(stringValue);
    }
    catch (JSONException e)
    {
      throw new ZMarshallerException(stringValue, e);
    }
  }
}
