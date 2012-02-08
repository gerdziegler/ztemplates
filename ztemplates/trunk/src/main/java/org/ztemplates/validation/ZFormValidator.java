package org.ztemplates.validation;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZIForm;
import org.ztemplates.form.impl.ZFormWrapper;
import org.ztemplates.message.ZMessages;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;
import org.ztemplates.property.ZPropertyException;

public class ZFormValidator implements ZIValidator
{
  static final Logger log = Logger.getLogger(ZFormValidator.class);

  private final ZIForm form;

  private final String requiredMessage;

  private final List<ZIValidator> validators = new ArrayList<ZIValidator>();


  public ZFormValidator(ZIForm form,
      String requiredMessage)
  {
    super();
    this.form = form;
    this.requiredMessage = requiredMessage;
  }


  // @Override
  public void validate(ZMessages messages)
  {
    final ZFormMembers members;
    try
    {
      final ZFormWrapper wrapper = new ZFormWrapper(form);
      members = wrapper.getFormMembers();
    }
    catch (Exception e)
    {
      log.error("error while processing form " + form, e);
      return;
    }
    for (ZProperty prop : members.getProperties())
    {
      if (prop.getRequiredValidator() == null)
      {
        prop.setRequiredValidator(new ZRequiredValidator(requiredMessage, prop));
      }
      prop.getRequiredValidator().validate(messages);
      validateProperty(prop, messages);
    }
    for (ZOperation op : members.getOperations())
    {
      validateProperty(op, messages);
    }
    for (ZIValidator val : validators)
    {
      try
      {
        val.validate(messages);
      }
      catch (ZPropertyException e)
      {
        messages.addError(e.getMessage(), e.getProperties());
      }
    }
  }


  private void validateProperty(ZProperty prop, ZMessages messages)
  {
    //don't validate empty values
    if (prop.isEmpty())
    {
      return;
    }
    //parse all string values to check correct format
    for (String stringValue : prop.getStringValues())
    {
      if (stringValue != null)
      {
        try
        {
          prop.parse(stringValue);
        }
        catch (ZPropertyException e)
        {
          messages.addError(e.getMessage(), e.getProperties());
        }
      }
    }
    //only validate further if parsing succeeded
    if (messages.isError(prop))
    {
      return;
    }
    for (ZIValidator val : (List<ZIValidator>) prop.getValidators())
    {
      try
      {
        val.validate(messages);
      }
      catch (ZPropertyException e)
      {
        messages.addError(e.getMessage(), e.getProperties());
      }
    }
  }


  public void add(ZIValidator val)
  {
    validators.add(val);
  }
}