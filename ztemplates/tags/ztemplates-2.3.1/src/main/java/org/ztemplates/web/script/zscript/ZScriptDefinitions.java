package org.ztemplates.web.script.zscript;

import java.util.List;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(ZVelocityRenderer.class)
public class ZScriptDefinitions
{
  @ZExpose
  private final List<ZIJavaScriptDefinition> definitions;


  public ZScriptDefinitions(List<ZIJavaScriptDefinition> definitions)
  {
    this.definitions = definitions;
  }


  public List<ZIJavaScriptDefinition> getDefinitions()
  {
    return definitions;
  }

}
