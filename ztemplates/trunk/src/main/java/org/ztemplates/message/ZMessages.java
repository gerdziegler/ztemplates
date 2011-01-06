package org.ztemplates.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ztemplates.property.ZProperty;

/**
 * container for messages, keeps messages created by a validator or any other
 * program part
 * 
 * @author gerd
 * 
 */
public class ZMessages
{
  private static final Logger log = Logger.getLogger(ZMessages.class);

  private final List<ZMessage> messages = new ArrayList<ZMessage>();

  private final Map<String, List<ZMessage>> propertyMessagesMap = new HashMap<String, List<ZMessage>>();

  private final List<ZMessage> globalMessages = new ArrayList<ZMessage>();

  private boolean dirty = false;


  public ZMessages()
  {
  }


  private void cleanup()
  {
    if (!dirty)
    {
      return;
    }
    dirty = false;
    propertyMessagesMap.clear();
    globalMessages.clear();

    for (ZMessage msg : messages)
    {
      if (msg.getPropertyNames().isEmpty())
      {
        globalMessages.add(msg);
      }
      else
      {
        for (String propName : msg.getPropertyNames())
        {
          List<ZMessage> pm = propertyMessagesMap.get(propName);
          if (pm == null)
          {
            pm = new ArrayList<ZMessage>();
            propertyMessagesMap.put(propName, pm);
          }
          pm.add(msg);
        }
      }
    }
  }


  public void clearMessages()
  {
    dirty = true;
    messages.clear();
  }


  public void addMessage(ZMessage msg)
  {
    dirty = true;
    messages.add(msg);
  }


  /**
   * utilitiy
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addInfoPropertyNames(String text, String... propertyNameArr)
  {
    addMessage(new ZInfoMessage(text, propertyNameArr));
  }


  /**
   * utilitiy
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addInfo(String text, ZProperty... propertyArr)
  {
    addMessage(ZMessage.create(ZMessage.INFO, text, propertyArr));
  }


  /**
   * utilitiy
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addErrorPropertyNames(String text, String... propertyNameArr)
  {
    addMessage(new ZErrorMessage(text, propertyNameArr));
  }


  /**
   * utilitiy
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addError(String text, ZProperty... propertyArr)
  {
    addMessage(ZMessage.create(ZMessage.ERROR, text, propertyArr));
  }


  /**
   * utilitiy
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addWarningPropertyNames(String text, String... propertyNameArr)
  {
    addMessage(new ZWarningMessage(text, propertyNameArr));
  }


  /**
   * utilitiy
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addWarning(String text, ZProperty... propertyArr)
  {
    addMessage(ZMessage.create(ZMessage.WARNING, text, propertyArr));
  }


  public List<ZMessage> getMessages()
  {
    return Collections.unmodifiableList(messages);
  }


  public String getMessagesJSON()
  {
    cleanup();
    try
    {
      JSONObject ret = new JSONObject();
      ret.put("globalMessages", createGlobalMessagesJSON());
      ret.put("propertyMessages", createPropertyMessagesJSON());
      return ret.toString(2);
    }
    catch (JSONException e)
    {
      log.error("", e);
      return e.getLocalizedMessage();
    }
  }


  public String getGlobalMessagesJSON()
  {
    cleanup();
    try
    {
      return createGlobalMessagesJSON().toString(2);
    }
    catch (JSONException e)
    {
      log.error("", e);
      return e.getLocalizedMessage();
    }
  }


  public String getPropertyMessagesJSON()
  {
    cleanup();
    try
    {
      return createPropertyMessagesJSON().toString(2);
    }
    catch (JSONException e)
    {
      log.error("", e);
      return e.getLocalizedMessage();
    }
  }


  public JSONArray createGlobalMessagesJSON() throws JSONException
  {
    cleanup();
    JSONArray globalMessagesJSON = new JSONArray();
    for (ZMessage msg : globalMessages)
    {
      JSONObject msgJSON = new JSONObject();
      msgJSON.put("type", msg.getType());
      msgJSON.put("text", msg.getText());
      globalMessagesJSON.put(msgJSON);
    }
    return globalMessagesJSON;
  }


  public JSONObject createPropertyMessagesJSON() throws JSONException
  {
    cleanup();
    JSONObject propertyMessagesJSON = new JSONObject();
    for (Map.Entry<String, List<ZMessage>> entry : propertyMessagesMap.entrySet())
    {
      String name = entry.getKey();
      List<ZMessage> val = entry.getValue();
      JSONArray arr = new JSONArray();
      for (ZMessage msg : val)
      {
        JSONObject msgJSON = new JSONObject();
        msgJSON.put("type", msg.getType());
        msgJSON.put("text", msg.getText());
        JSONArray propsJSON = new JSONArray();
        for (String propName : msg.getPropertyNames())
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


  /**
   * messages bound to a property
   * 
   * @param propName
   * @return
   */
  public List<ZMessage> getPropertyMessages(String propName)
  {
    cleanup();
    return propertyMessagesMap.get(propName);
  }


  public Set<String> getPropertyNames()
  {
    cleanup();
    return propertyMessagesMap.keySet();
  }


  /**
   * Messages that are not bound to a specific property, like database
   * constraint violations or general infos
   * 
   * @return
   */
  public List<ZMessage> getGlobalMessages()
  {
    cleanup();
    return globalMessages;
  }


  public List<ZMessage> getErrors()
  {
    List<ZMessage> ret = new ArrayList<ZMessage>();
    for (ZMessage v : messages)
    {
      if (v.isError())
      {
        ret.add(v);
      }
    }
    return ret;
  }


  public List<ZMessage> getWarnings()
  {
    List<ZMessage> ret = new ArrayList<ZMessage>();
    for (ZMessage v : messages)
    {
      if (v.isWarning())
      {
        ret.add(v);
      }
    }
    return ret;
  }


  public List<ZMessage> getInfo()
  {
    List<ZMessage> ret = new ArrayList<ZMessage>();
    for (ZMessage v : messages)
    {
      if (v.isInfo())
      {
        ret.add(v);
      }
    }
    return ret;
  }


  public List<ZMessage> getType(String type)
  {
    List<ZMessage> ret = new ArrayList<ZMessage>();
    for (ZMessage v : messages)
    {
      if (v.isType(type))
      {
        ret.add(v);
      }
    }
    return ret;
  }


  public boolean isEmpty()
  {
    return messages.isEmpty();
  }


  public boolean isErrors()
  {
    for (ZMessage v : messages)
    {
      if (v.isError())
      {
        return true;
      }
    }
    return false;
  }


  public boolean isWarnings()
  {
    for (ZMessage v : messages)
    {
      if (v.isWarning())
      {
        return true;
      }
    }
    return false;
  }


  public boolean isInfos()
  {
    for (ZMessage v : messages)
    {
      if (v.isInfo())
      {
        return true;
      }
    }
    return false;
  }


  public boolean isType(String type)
  {
    for (ZMessage v : messages)
    {
      if (v.isType(type))
      {
        return true;
      }
    }
    return false;
  }


  // ////////////////////////////////////

  @Deprecated
  public void addInfoMessage(String text, String... propertyNameArr)
  {
    addMessage(new ZInfoMessage(text, propertyNameArr));
  }


  @Deprecated
  public void addInfoPropertyMessage(String text, ZProperty... propertyArr)
  {
    addMessage(ZMessage.create(ZMessage.INFO, text, propertyArr));
  }


  @Deprecated
  public void addErrorMessage(String text, String... propertyNameArr)
  {
    addMessage(new ZErrorMessage(text, propertyNameArr));
  }


  @Deprecated
  public void addErrorPropertyMessage(String text, ZProperty... propertyArr)
  {
    addMessage(ZMessage.create(ZMessage.ERROR, text, propertyArr));
  }


  @Deprecated
  public void addWarningMessage(String text, String... propertyNameArr)
  {
    addMessage(new ZWarningMessage(text, propertyNameArr));
  }


  @Deprecated
  public void addWarningPropertyMessage(String text, ZProperty... propertyArr)
  {
    addMessage(ZMessage.create(ZMessage.WARNING, text, propertyArr));
  }

}
