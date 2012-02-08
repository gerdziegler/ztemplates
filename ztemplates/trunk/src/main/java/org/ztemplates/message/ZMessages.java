package org.ztemplates.message;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.collections.set.ListOrderedSet;
import org.apache.log4j.Logger;
import org.ztemplates.property.ZProperty;
import org.ztemplates.property.ZPropertyException;

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

  //  private final List<ZMessage> messages = SetUniqueList.decorate(new ArrayList<ZMessage>());

  private final Map<String, ListOrderedSet> propertyName2MessageMap = new HashMap<String, ListOrderedSet>();

  private final Map<ZMessage, Set<String>> message2propertyNameMap = new HashMap<ZMessage, Set<String>>();

  private final Set<ZMessage> propertyMessages = new HashSet<ZMessage>();

  private final ListOrderedSet globalMessages = new ListOrderedSet();


  public ZMessages()
  {
  }


  public void clearMessages()
  {
    globalMessages.clear();
    propertyMessages.clear();
    propertyName2MessageMap.clear();
    message2propertyNameMap.clear();
  }


  private void addMessage(ZMessage msg, String... propertyNameArr)
  {
    if (propertyNameArr.length == 0)
    {
      globalMessages.add(msg);
    }
    else
    {
      propertyMessages.add(msg);

      //propertyName2MessageMap
      for (String propName : propertyNameArr)
      {
        ListOrderedSet pm = propertyName2MessageMap.get(propName);
        if (pm == null)
        {
          pm = new ListOrderedSet();
          propertyName2MessageMap.put(propName, pm);
        }
        pm.add(msg);
      }

      //message2propertyNameMap
      Set<String> names = message2propertyNameMap.get(msg);
      if (names == null)
      {
        names = new HashSet<String>();
        message2propertyNameMap.put(msg, names);
      }
      for (String propName : propertyNameArr)
      {
        names.add(propName);
      }
    }
  }


  private void addMessage(ZMessage msg, ZProperty... properties)
  {
    String[] propNames = new String[properties.length];
    for (int i = 0; i < properties.length; i++)
    {
      propNames[i] = properties[i].getName();
    }
    addMessage(msg, propNames);
  }


  /**
   * utility
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addInfoPropertyNames(String text, String... propertyNameArr)
  {
    addMessage(new ZInfoMessage(text), propertyNameArr);
  }


  /**
   * utilitiy
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addInfo(String text, ZProperty... propertyArr)
  {
    addMessage(new ZMessage(ZMessage.INFO, text), propertyArr);
  }


  /**
   * utilitiy
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addErrorPropertyNames(String text, String... propertyNameArr)
  {
    addMessage(new ZErrorMessage(text), propertyNameArr);
  }


  /**
   * utilitiy
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addError(String text, ZProperty... propertyArr)
  {
    addMessage(new ZErrorMessage(text), propertyArr);
  }


  /**
   * utilitiy
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addWarningPropertyNames(String text, String... propertyNameArr)
  {
    addMessage(new ZWarningMessage(text), propertyNameArr);
  }


  /**
   * utilitiy
   * 
   * @param text
   * @param propertyNameArr
   */
  public void addWarning(String text, ZProperty... propertyArr)
  {
    addMessage(new ZWarningMessage(text), propertyArr);
  }


  //  public List<ZMessage> getMessages()
  //  {
  //    return Collections.unmodifiableList(messages);
  //  }

  /**
   * 
   * @param prop
   * @return true if there is an error
   */
  public boolean isError(ZProperty prop)
  {
    return isMessage(ZMessage.ERROR, prop);
  }


  /**
   * 
   * @param prop
   * @return true if there is an error
   */
  public boolean isWarnings(ZProperty prop)
  {
    return isMessage(ZMessage.ERROR, prop);
  }


  public boolean isMessage(String type, ZProperty prop)
  {
    Collection<ZMessage> messages = propertyName2MessageMap.get(prop.getName());
    if (messages != null)
    {
      for (ZMessage msg : messages)
      {
        if (type.equals(msg.getType()))
        {
          return true;
        }
      }
    }
    return false;
  }


  /**
   * messages bound to a property
   * 
   * @param propName
   * @return
   */
  public List<ZMessage> getPropertyMessages(String... propNames)
  {
    List<ZMessage> ret = new ArrayList<ZMessage>();
    for (String name : propNames)
    {
      Collection<ZMessage> propMessages = propertyName2MessageMap.get(name);
      if (propMessages != null)
      {
        ret.addAll(propMessages);
      }
    }
    return ret;
  }


  public Set<String> getPropertyNames(ZMessage msg)
  {
    Set<String> ret = message2propertyNameMap.get(msg);
    if (ret == null)
    {
      ret = new HashSet<String>();
    }
    return ret;
  }


  public List<ZMessage> getPropertyMessages(ZProperty... prop)
  {
    String[] names = new String[prop.length];
    for (int i = 0; i < prop.length; i++)
    {
      names[i] = prop[i].getName();
    }
    return getPropertyMessages(names);
  }


  public Set<String> getPropertyNames()
  {
    return propertyName2MessageMap.keySet();
  }


  /**
   * Messages that are not bound to a specific property, like database
   * constraint violations or general infos
   * 
   * @return
   */
  public Collection<ZMessage> getGlobalMessages()
  {
    return globalMessages;
  }


  //  public List<ZMessage> getErrors()
  //  {
  //    List<ZMessage> ret = new ArrayList<ZMessage>();
  //    for (ZMessage v : messages)
  //    {
  //      if (v.isError())
  //      {
  //        ret.add(v);
  //      }
  //    }
  //    return ret;
  //  }

  //  public List<ZMessage> getWarnings()
  //  {
  //    List<ZMessage> ret = new ArrayList<ZMessage>();
  //    for (ZMessage v : messages)
  //    {
  //      if (v.isWarning())
  //      {
  //        ret.add(v);
  //      }
  //    }
  //    return ret;
  //  }
  //
  //
  //  public List<ZMessage> getInfo()
  //  {
  //    List<ZMessage> ret = new ArrayList<ZMessage>();
  //    for (ZMessage v : messages)
  //    {
  //      if (v.isInfo())
  //      {
  //        ret.add(v);
  //      }
  //    }
  //    return ret;
  //  }

  //  public List<ZMessage> getType(String type)
  //  {
  //    List<ZMessage> ret = new ArrayList<ZMessage>();
  //    for (ZMessage v : messages)
  //    {
  //      if (v.isType(type))
  //      {
  //        ret.add(v);
  //      }
  //    }
  //    return ret;
  //  }

  public boolean isEmpty()
  {
    return globalMessages.isEmpty() && propertyName2MessageMap.isEmpty();
  }


  private boolean isMessage(String type, Collection<ZMessage> coll)
  {
    for (ZMessage v : coll)
    {
      if (type.equals(v.getType()))
      {
        return true;
      }
    }
    return false;
  }


  public boolean isPropertyMessage(String type)
  {
    return isMessage(type, propertyMessages);
  }


  public boolean isPropertyWarnings()
  {
    return isPropertyMessage(ZMessage.WARNING);
  }


  public boolean isPropertyErrors()
  {
    return isPropertyMessage(ZMessage.ERROR);
  }


  public boolean isPropertyInfo()
  {
    return isPropertyMessage(ZMessage.INFO);
  }


  public boolean isGlobalMessage(String type)
  {
    return isMessage(type, globalMessages);
  }


  public boolean isGlobalWarnings()
  {
    return isGlobalMessage(ZMessage.WARNING);
  }


  public boolean isGlobalErrors()
  {
    return isGlobalMessage(ZMessage.ERROR);
  }


  public boolean isGlobalInfo()
  {
    return isGlobalMessage(ZMessage.INFO);
  }


  public boolean isErrors()
  {
    return isMessage(ZMessage.ERROR);
  }


  public boolean isInfos()
  {
    return isMessage(ZMessage.INFO);
  }


  public boolean isWarnings()
  {
    return isMessage(ZMessage.WARNING);
  }


  public boolean isMessage(String type)
  {
    return isGlobalMessage(type) || isPropertyMessage(type);
  }


  public void addError(Exception e)
  {
    if (e instanceof ZPropertyException)
    {
      addError(e.getLocalizedMessage(), ((ZPropertyException) e).getProperties());
    }
    else if (e.getMessage() == null)
    {
      log.error("", e);
      addError(e.getClass().getName());
    }
    else
    {
      addError(e.getLocalizedMessage());
    }
  }

}