package org.ztemplates.test.actions.urlhandler.prop.list;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.property.deprecated.ZIntListProperty;
import org.ztemplates.property.deprecated.ZStringListProperty;

/**
 */
@ZMatch(value = "/listproptest/${varProp}", parameters =
{
    "paramProp"
})
public class ListHandler
{
  private final ZIntListProperty varProp = new ZIntListProperty();

  private final ZStringListProperty paramProp = new ZStringListProperty("_");


  public ListHandler()
  {
    // varProp.setRequired(true);
  }


  public ZIntListProperty getVarProp()
  {
    return varProp;
  }


  public ZStringListProperty getParamProp()
  {
    return paramProp;
  }
}
