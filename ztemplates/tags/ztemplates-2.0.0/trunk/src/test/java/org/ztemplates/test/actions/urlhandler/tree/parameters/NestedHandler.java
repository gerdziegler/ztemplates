package org.ztemplates.test.actions.urlhandler.tree.parameters;

import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSetter;

/**
 */
@ZMatch(value = "nested/${value}", parameters =
{
    "param1",
    "param3"
})
public class NestedHandler
{
  private String param1;

  private String param3;

  private String value;


  @ZGetter("param1")
  public String getParam1()
  {
    return param1;
  }


  @ZSetter("param1")
  public void setParam1(String param1)
  {
    this.param1 = param1;
  }


  @ZGetter("value")
  public String getValue()
  {
    return value;
  }


  @ZSetter("value")
  public void setValue(String value)
  {
    this.value = value;
  }


  @ZGetter("param3")
  public String getParam3()
  {
    return param3;
  }


  @ZSetter("param3")
  public void setParam3(String param3)
  {
    this.param3 = param3;
  }
}
