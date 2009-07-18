package org.ztemplates.test.render.impl.opt;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZScript(javaScript =
{
    @ZJavaScript("first.js"),
    @ZJavaScript("nested.js?key=${key}")
})
@ZRenderer(ZVelocityRenderer.class)
public class Nested
{
  private final String val;


  public Nested(String val)
  {
    this.val = val;
  }


  @ZExpose
  public String getKey()
  {
    return "value2";
  }


  @ZExpose
  public String getVal()
  {
    return val;
  }
}
