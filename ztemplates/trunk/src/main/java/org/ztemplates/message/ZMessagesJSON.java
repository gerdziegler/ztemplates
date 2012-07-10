package org.ztemplates.message;

import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * JSON mapping for ZMessages Object
 * @author gerd
 *
 */
public class ZMessagesJSON extends JSONObject
{
  private static final Logger log = Logger.getLogger(ZMessagesJSON.class);

  private final ZMessages messages;


  public boolean isEmpty()
  {
    return messages == null || messages.isEmpty();
  }


  public ZMessagesJSON(ZMessages messages)
  {
    super();
    this.messages = messages;
    try
    {
      put("globalMessages", createGlobalMessagesJSON(messages));
      put("propertyMessages", createPropertyMessagesJSON(messages));
    }
    catch (JSONException e)
    {
      log.error(messages, e);
    }
  }


  public ZMessagesJSON()
  {
    this(null);
  }


  //  public String getMessagesJSON()
  //  {
  //    try
  //    {
  //      JSONObject ret = createMessagesJSON(messages);
  //      return ret.toString(2);
  //    }
  //    catch (JSONException e)
  //    {
  //      log.error("", e);
  //      return e.getLocalizedMessage();
  //    }
  //  }
  //
  //
  //  public static JSONObject createMessagesJSON(ZMessages messages) throws JSONException
  //  {
  //    JSONObject ret = new JSONObject();
  //    return ret;
  //  }
  //  private String getGlobalMessagesJSON()
  //  {
  //    try
  //    {
  //      return createGlobalMessagesJSON().toString(2);
  //    }
  //    catch (JSONException e)
  //    {
  //      log.error("", e);
  //      return e.getLocalizedMessage();
  //    }
  //  }
  //
  //
  //  private String getPropertyMessagesJSON()
  //  {
  //    try
  //    {
  //      return createPropertyMessagesJSON().toString(2);
  //    }
  //    catch (JSONException e)
  //    {
  //      log.error("", e);
  //      return e.getLocalizedMessage();
  //    }
  //  }

  public ZMessages getMessages()
  {
    return messages;
  }


  private JSONArray createGlobalMessagesJSON(ZMessages messages) throws JSONException
  {
    JSONArray globalMessagesJSON = new JSONArray();
    if (messages == null)
    {
      return globalMessagesJSON;
    }
    for (ZMessage msg : messages.getGlobalMessages())
    {
      JSONObject msgJSON = new JSONObject();
      msgJSON.put("type", msg.getType());
      msgJSON.put("text", msg.getText());
      globalMessagesJSON.put(msgJSON);
    }
    return globalMessagesJSON;
  }


  private JSONObject createPropertyMessagesJSON(ZMessages messages) throws JSONException
  {
    JSONObject propertyMessagesJSON = new JSONObject();
    if (messages == null)
    {
      return propertyMessagesJSON;
    }

    for (String name : messages.getPropertyNames())
    {
      List<ZMessage> val = messages.getPropertyMessages(name);
      JSONArray arr = new JSONArray();
      for (ZMessage msg : val)
      {
        JSONObject msgJSON = new JSONObject();
        msgJSON.put("type", msg.getType());
        msgJSON.put("text", msg.getText());
        JSONArray propsJSON = new JSONArray();
        for (String propName : messages.getPropertyNames(msg))
        {
          propsJSON.put(propName);
        }
        msgJSON.put("propertyNames", propsJSON);
        arr.put(msgJSON);
      }
      propertyMessagesJSON.put(name, arr);
    }
    return propertyMessagesJSON;
  }
}