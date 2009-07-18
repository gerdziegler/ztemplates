package org.ztemplates.test.actions.urlhandler.prop.plain;

import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.property.ZError;
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
  private final ZOperation opProp1 = new ZOperation("submit")
  {
    @Override
    public ZError validate()
    {
      return new ZError("katze");
    }
  };

  private final ZStringProperty varProp = new ZStringProperty();

  private final ZStringProperty paramProp = new ZStringProperty();

  private final ZOperation opProp2 = new ZOperation("submit");

  private final ZOperation op3 = new ZOperation("submit");


  public Handler()
  {
    varProp.setRequired(true);
  }


  @ZGetter("opProp1")
  public ZOperation getOpProp1()
  {
    return opProp1;
  }


  @ZGetter("paramProp")
  public ZStringProperty getParamProp()
  {
    return paramProp;
  }


  @ZGetter("varProp")
  public ZStringProperty getVarProp()
  {
    return varProp;
  }


  @ZGetter("opProp2")
  public ZOperation getOpProp2()
  {
    return opProp2;
  }


  @ZGetter("op3")
  public ZOperation getOp3()
  {
    return op3;
  }
}
