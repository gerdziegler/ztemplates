package org.ztemplates.test.render.impl.opt;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(value = ZVelocityRenderer.class, zscript = true)
public final class Root
{
  private Nested nested = new Nested("nested-val0");

  private List<Nested> nestedList = new ArrayList<Nested>();


  public Root()
  {
    nestedList.add(new Nested("nested-val1"));
    nestedList.add(new Nested("nested-val2"));
  }


  @ZExpose(render = true)
  public Nested getNested()
  {
    return nested;
  }


  @ZExpose(render = true)
  public List<Nested> getNestedList()
  {
    return nestedList;
  }


  @ZExpose
  public String getSimpleValue()
  {
    return "simple-value";
  }
}
