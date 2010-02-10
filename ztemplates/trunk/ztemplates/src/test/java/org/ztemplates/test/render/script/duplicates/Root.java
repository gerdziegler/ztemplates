package org.ztemplates.test.render.script.duplicates;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(value = ZVelocityRenderer.class, zscript = true)
@ZScript(javaScript = @ZJavaScript("root.js"))
public final class Root
{
  private Nested nested = new Nested();

  private Nested1 nested1 = new Nested1();


  @ZExpose(render = true)
  public Nested getNested()
  {
    return nested;
  }


  @ZExpose(render = true)
  public Nested1 getNested1()
  {
    return nested1;
  }
}
