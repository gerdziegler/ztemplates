package org.ztemplates.test.render.script.var;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(value = ZVelocityRenderer.class, zscript = true)
@ZScript(javaScript =
{
    @ZJavaScript("first.js"),
    @ZJavaScript("root.js?key=${key}")
})
public final class Root
{
  private Nested nested = new Nested();


  @ZExpose(render = true)
  public Nested getNested()
  {
    return nested;
  }


  @ZExpose
  public String getKey()
  {
    return "value1";
  }
}
