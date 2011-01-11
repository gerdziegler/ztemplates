package org.ztemplates.test.actions.urlhandler.form;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZStringProperty;

/**
 */
@ZMatch(value = "/act", parameters =
{
    "form.topSection.field1"
})
public class FormAction
{
  private Form form = new Form();

  private final ZOperation op1 = new ZOperation("submit");

  private final ZStringProperty prop1 = new ZStringProperty();


  public Form getForm()
  {
    return form;
  }


  public ZOperation getOp1()
  {
    return op1;
  }


  public ZStringProperty getProp1()
  {
    return prop1;
  }
}
