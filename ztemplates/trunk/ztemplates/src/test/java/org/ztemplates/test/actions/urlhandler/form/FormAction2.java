package org.ztemplates.test.actions.urlhandler.form;

import org.ztemplates.actions.ZMatch;

/**
 */
@ZMatch(value = "/act2")
public class FormAction2
{
  private Form form = new Form();

  public Form getForm()
  {
    return form;
  }
}
