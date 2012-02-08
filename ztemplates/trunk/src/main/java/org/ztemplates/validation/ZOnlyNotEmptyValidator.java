package org.ztemplates.validation;

import org.ztemplates.message.ZMessages;
import org.ztemplates.property.ZProperty;

public abstract class ZOnlyNotEmptyValidator implements ZIValidator
{
  private final ZProperty[] props;


  public ZOnlyNotEmptyValidator(ZProperty... props)
  {
    this.props = props;
  }


  public abstract void validateNotEmpty(ZMessages res);


  // @Override
  public void validate(ZMessages res)
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
