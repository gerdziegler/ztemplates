package org.ztemplates.validation;

import org.ztemplates.property.ZProperty;
import org.ztemplates.validation.message.ZMessages;

public abstract class ZOnlyNotEmptyValidator implements ZIValidator
{
  private final ZProperty[] props;


  public ZOnlyNotEmptyValidator(ZProperty... props)
  {
    this.props = props;
  }


  public abstract void validateNotEmpty(ZMessages res) throws Exception;


  // @Override
  public void validate(ZMessages res) throws Exception
  {
    for (ZProperty prop : props)
    {
      if (prop.isEmpty())
      {
        return;
      }
    }

    validateNotEmpty(res);
  }
}
