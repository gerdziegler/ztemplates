package org.ztemplates.message.view;

import java.util.List;

import org.ztemplates.message.ZMessage;
import org.ztemplates.message.ZMessages;
import org.ztemplates.property.ZProperty;

public class ZFormPropertyMessages
{
  private final List<ZMessage> messageList;

  private String id;

  private String propName;

  private String clazz = "form-input-propertyMessage";


  public ZFormPropertyMessages(String propName,
      List<ZMessage> messageList)
  {
    this.propName = propName;
    this.messageList = messageList;
    this.id = propName + "Message";
  }


  public ZFormPropertyMessages(ZMessages messages,
      ZProperty prop)
  {
    this(prop.getName(), messages.getPropertyMessages(prop));
  }


  public ZFormPropertyMessages(ZMessages messages,
      String propName)
  {
    this(propName, messages.getPropertyMessages(propName));
  }


  public String toString()
  {
    StringBuilder sb = new StringBuilder("<ul id='" + id + "' class='" + clazz + "' propName='" + propName + "'>");
    for (ZMessage msg : messageList)
    {
      sb.append("<li class='" + msg.getType() + "'>");
      sb.append(msg.getText());
      sb.append("</li>");
    }
    sb.append("</ul>");
    return sb.toString();
  }


  public String getClazz()
  {
    return clazz;
  }


  public void setClazz(String clazz)
  {
    this.clazz = clazz;
  }


  public String getPropName()
  {
    return propName;
  }


  public void setPropName(String propName)
  {
    this.propName = propName;
  }


  public List<ZMessage> getMessageList()
  {
    return messageList;
  }

}