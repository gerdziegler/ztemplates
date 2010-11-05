package org.ztemplates.test.actions.urlhandler.tree.parameters;

import org.ztemplates.actions.ZMatch;

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


  public String getParam1()
  {
    return param1;
  }


  public void setParam1(String param1)
  {
    this.param1 = param1;
  }


  public String getValue()
  {
    return value;
  }


  public void setValue(String value)
  {
    this.value = value;
  }


  public String getParam3()
  {
    return param3;
  }


  public void setParam3(String param3)
  {
    this.param3 = param3;
  }
}
