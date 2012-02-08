package org.ztemplates.validation;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.message.ZMessages;

public class ZValidator implements ZIValidator
{
  private final List<ZIValidator> validators = new ArrayList<ZIValidator>();


  public ZValidator()
  {
  }


  public ZMessages validate()
  {
    ZMessages ret = new ZMessages();
    validate(ret);
    return ret;
  }


  // @Override
  public void validate(ZMessages res)
  {
    for (ZIValidator v : validators)
    {
      try
      {
        v.validate(res);
      }
      catch (Exception e)
      {
        res.addError(e.getLocalizedMessage());
      }
    }
  }


  public List<ZIValidator> getValidators()
  {
    return validators;
  }


  public void add(ZIValidator val)
  {
    validators.add(val);
  }


  public void clear()
  {
    validators.clear();
  }
}