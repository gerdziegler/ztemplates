package org.ztemplates.test.actions.urlhandler.tree.parameters;

import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSetter;
import org.ztemplates.property.ZStringProperty;

/**
 */
@ZMatch(value = "/mytext[/#{nested}]", parameters =
{
    "param1",
    "param2",
    "paramArr4",
    "paramProp"
})
public class Handler
{
  private String param1;

  private String param2;

  private String[] paramArr4;

  private final ZStringProperty paramProp = new ZStringProperty();

  private NestedHandler nested;


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


  @ZGetter("nested")
  public NestedHandler getNested()
  {
    return nested;
  }


  @ZSetter("nested")
  public void setNested(NestedHandler nested)
  {
    this.nested = nested;
  }


  @ZGetter("param2")
  public String getParam2()
  {
    return param2;
  }


  @ZSetter("param2")
  public void setParam2(String param2)
  {
    this.param2 = param2;
  }


  public String[] getParamArr4()
  {
    return paramArr4;
  }


  public void setParamArr4(String[] paramArr4)
  {
    this.paramArr4 = paramArr4;
  }


  @ZGetter("paramProp")
  @ZSetter("paramProp")
  public ZStringProperty getParamProp()
  {
    return paramProp;
  }
}
