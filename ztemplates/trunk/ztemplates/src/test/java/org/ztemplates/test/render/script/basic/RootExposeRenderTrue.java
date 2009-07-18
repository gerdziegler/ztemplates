package org.ztemplates.test.render.script.basic;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(value = ZVelocityRenderer.class, zscript = true)
@ZScript(javaScript = @ZJavaScript("root.js"))
public class RootExposeRenderTrue
{
  private Nested nested = new Nested();


  @ZExpose(render = true)
  public Nested getNested()
  {
    return nested;
  }


  @ZExpose(render = true)
  public NoRender getNoRender()
  {
    return new NoRender();
  }
}
