package org.ztemplates.web.script.zscript;

import java.util.List;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(value = ZVelocityRenderer.class, mimeType = "text/javascript")
public class ZScriptDefinitions
{
  @ZExpose
  final List<ZIJavaScriptDefinition> definitions;

  @ZExpose
  String prefix;


  public ZScriptDefinitions(String prefix,
      List<ZIJavaScriptDefinition> definitions)
  {
    this.prefix = prefix;
    this.definitions = definitions;
  }
}