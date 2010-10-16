package org.ztemplates.web.form.validation;

import java.util.ArrayList;
import java.util.List;

public class ZPropertyMessages
{
  private final String propertyName;

  private final List<ZMessage> messages = new ArrayList<ZMessage>();


  public ZPropertyMessages(String propertyName)
  {
    super();
    this.propertyName = propertyName;
  }


  public String getPropertyName()
  {
    return propertyName;
  }


  public List<ZMessage> getMessages()
  {
    return messages;
  }

  // public String getMessagesAsString()
  // {
  // StringBuffer sb = new StringBuffer();
  // for (ZValidationMessage m : messages)
  // {
  // sb.append(m.getText());
  // sb.append('\n');
  // }
  // return sb.toString();
  // }
}
