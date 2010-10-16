package org.ztemplates.web.form.validation;

import org.ztemplates.property.ZProperty;

public abstract class ZOnlyNotEmptyValidator implements ZIValidator
{
  private final ZProperty[] props;


  public ZOnlyNotEmptyValidator(ZProperty... props)
  {
    this.props = props;
  }


  public abstract void validateNotEmpty(ZMessages res) throws Exception;


  @Override
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
