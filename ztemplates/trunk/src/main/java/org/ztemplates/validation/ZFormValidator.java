package org.ztemplates.validation;

import java.util.List;

import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZIForm;
import org.ztemplates.form.impl.ZFormWrapper;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;

public class ZFormValidator implements ZIValidator
{
  private final ZIForm form;

  private final String requiredMessage;


  public ZFormValidator(ZIForm form, String requiredMessage)
  {
    super();
    this.form = form;
    this.requiredMessage = requiredMessage;
  }


  // @Override
  public void validate(ZMessages res) throws Exception
  {
    final ZFormWrapper wrapper = new ZFormWrapper(form);
    final ZFormMembers members = wrapper.getFormMembers();
    ZErrorMessage msg = new ZErrorMessage(requiredMessage);
    for (ZProperty prop : members.getProperties())
    {
      if (!prop.isEmpty())
      {
        createValidationMessage(prop, res);
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
      res.addMessage(msg);
    }
    for (ZOperation op : members.getOperations())
    {
      createValidationMessage(op, res);
    }
  }


  private void createValidationMessage(ZProperty prop, ZMessages res)
  {
    for (ZIValidator val : (List<ZIValidator>) prop.getValidators())
    {
      try
      {
        val.validate(res);
      }
      catch (Exception e)
      {
        res.addMessage(new ZErrorMessage(e.getLocalizedMessage(), prop.getName()));
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
        res.addMessage(new ZErrorMessage(e.getLocalizedMessage(), prop.getName()));
      }
    }
  }

}
