package org.ztemplates.test.render.methodrepo;

import org.ztemplates.render.ZExpose;

public class Pojo1
{
  @ZExpose
  public final String prop1 = "prop1";

  public final String prop2 = "prop2";

  private String prop3 = "prop3";

  @ZExpose
  private final String prop4 = "prop4";

  private final String prop5 = "prop5";

  @ZExpose
  private final String prop6 = "prop6FromField";


  @ZExpose
  public String getProp3()
  {
    return prop3;
  }


  @ZExpose
  public String getProp5()
  {
    return prop5;
  }


  @ZExpose
  public String getProp6()
  {
    return "prop6FromGetter";
  }

}
