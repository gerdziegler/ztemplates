package org.ztemplates.validation;

import org.ztemplates.message.ZMessages;
import org.ztemplates.property.ZProperty;

/**
 * Use this validator to check required items.
 * @author gerdziegler.de
 */
public class ZRequiredValidator implements ZIValidator
{
  private final String errorMessage;

  private final ZProperty prop;


  public ZRequiredValidator(String errorMessage,
      ZProperty prop)
  {
    this.errorMessage = errorMessage;
    this.prop = prop;
  }


  public void validate(ZMessages messages)
  {
    if (prop.isRequired() && prop.isEmpty())
    {
      messages.addError(errorMessage, prop);
    }
  }
}