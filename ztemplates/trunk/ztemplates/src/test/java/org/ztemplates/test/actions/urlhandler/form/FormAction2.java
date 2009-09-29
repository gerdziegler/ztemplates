package org.ztemplates.test.actions.urlhandler.form;

import org.ztemplates.actions.ZMatch;

/**
 */
@ZMatch(value = "/act2")
public class FormAction2
{
  private FormController form = new FormController(new FormModel());


  public FormController getForm()
  {
    return form;
  }
}
