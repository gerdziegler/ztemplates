package org.ztemplates.test.actions.urlhandler.prop.plain;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZStringProperty;

/**
 */
@ZMatch(value = "/proptest[/${varProp}]", parameters =
{
    "paramProp"
})
public class Handler
{
  private final ZOperation opProp1 = new ZOperation("submit");

  private final ZStringProperty varProp = new ZStringProperty();

  private final ZStringProperty paramProp = new ZStringProperty();

  private final ZOperation opProp2 = new ZOperation("submit");

  private final ZOperation op3 = new ZOperation("submit");


  public Handler()
  {
    // varProp.setRequired(true);
  }


  public ZOperation getOpProp1()
  {
    return opProp1;
  }


  public ZStringProperty getParamProp()
  {
    return paramProp;
  }


  public ZStringProperty getVarProp()
  {
    return varProp;
  }


  public ZOperation getOpProp2()
  {
    return opProp2;
  }


  public ZOperation getOp3()
  {
    return op3;
  }
}
