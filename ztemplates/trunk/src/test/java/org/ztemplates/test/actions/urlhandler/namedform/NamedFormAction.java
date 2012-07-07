package org.ztemplates.test.actions.urlhandler.namedform;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.form.impl.ZFormWrapper;

/**
 */
@ZMatch(value = "/namedFormAction", parameters = "id", form = "form")
public class NamedFormAction
{
  Long id;

  //  @ZFormName("myForm${id}")
  NamedForm form = new NamedForm();


  public void beforeForm()
  {
    new ZFormWrapper(form, "myForm" + id);
  }
}