package org.ztemplates.test.render.script.basic2;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(value = ZVelocityRenderer.class, zscript = true)
public final class Root
{
  private Nested nested = new Nested();


  @ZExpose(render = true)
  public Nested getNested()
  {
    return nested;
  }
}
