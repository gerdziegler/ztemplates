package org.ztemplates.validation;

import java.util.List;

import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZIForm;
import org.ztemplates.form.impl.ZFormWrapper;
import org.ztemplates.message.ZErrorMessage;
import org.ztemplates.message.ZMessages;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;

public class ZFormValidator implements ZIValidator
{
  private final ZIForm form;

  private final String requiredMessage;


  /**
   * Convenience method
   * 
   * @param form
   * @param requiredMessage
   * @return
   * @throws Exception
   */
  public static void validate(ZIForm form, String requiredMessage, ZMessages messages) throws Exception
  {
    new ZFormValidator(form, requiredMessage).validate(messages);
  }


  /**
   * Convenience method
   * 
   * @param form
   * @param requiredMessage
   * @return
   * @throws Exception
   */
  public static ZMessages validate(ZIForm form, String requiredMessage) throws Exception
  {
    ZMessages messages = new ZMessages();
    new ZFormValidator(form, requiredMessage).validate(messages);
    return messages;
  }


  public ZFormValidator(ZIForm form, String requiredMessage)
  {
    super();
    this.form = form;
    this.requiredMessage = requiredMessage;
  }


  // @Override
  public void validate(ZMessages messages) throws Exception
  {
    final ZFormWrapper wrapper = new ZFormWrapper(form);
    final ZFormMembers members = wrapper.getFormMembers();
    ZErrorMessage msg = new ZErrorMessage(requiredMessage);
    for (ZProperty prop : members.getProperties())
    {
      if (!prop.isEmpty())
      {
        createValidationMessage(prop, messages);
      }
      else
      {
        if (prop.isRequired())
        {
          msg.getPropertyNames().add(prop.getName());
        }
      }
    }
    if (!msg.getPropertyNames().isEmpty())
    {
      messages.addMessage(msg);
    }
    for (ZOperation op : members.getOperations())
    {
      createValidationMessage(op, messages);
    }
  }


  private void createValidationMessage(ZProperty prop, ZMessages messages)
  {
    for (ZIValidator val : (List<ZIValidator>) prop.getValidators())
    {
      try
      {
        val.validate(messages);
      }
      catch (Exception e)
      {
        messages.addMessage(new ZErrorMessage(e.getLocalizedMessage(), prop.getName()));
      }
    }
    for (String stringValue : prop.getStringValues())
    {
      try
      {
        prop.parse(stringValue);
      }
      catch (Exception e)
      {
        messages.addMessage(new ZErrorMessage(e.getLocalizedMessage(), prop.getName()));
      }
    }
  }

}
