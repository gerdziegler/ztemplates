package org.ztemplates.validation;

import java.util.ArrayList;
import java.util.List;

public class ZValidator implements ZIValidator
{
  private final List<ZIValidator> validators = new ArrayList<ZIValidator>();


  public ZValidator()
  {
  }


  public ZMessages validate() throws Exception
  {
    ZMessages ret = new ZMessages();
    for (ZIValidator v : validators)
    {
      v.validate(ret);
    }
    return ret;
  }


  // @Override
  public void validate(ZMessages res) throws Exception
  {
    for (ZIValidator v : validators)
    {
      v.validate(res);
    }
  }


  public List<ZIValidator> getValidators()
  {
    return validators;
  }
}