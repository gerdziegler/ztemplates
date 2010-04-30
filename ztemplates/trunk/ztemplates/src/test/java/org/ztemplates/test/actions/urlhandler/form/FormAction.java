package org.ztemplates.test.actions.urlhandler.form;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.property.ZError;
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

  private final ZOperation op1 = new ZOperation("submit")
  {
    @Override
    public ZError validate()
    {
      return new ZError("err");
    }
  };

  private final ZStringProperty prop1 = new ZStringProperty()
  {
    @Override
    public ZError validate()
    {
      return new ZError("err");
    }
  };


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
