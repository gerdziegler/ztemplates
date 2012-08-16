package org.ztemplates.web.script.zscript;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.commons.ZIObjectFactory;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZTemplates;

@ZRenderer(value = ZVelocityRenderer.class, mimeType = "text/javascript")
public class ZScriptDefinitions
{
  @ZExpose
  final List<ZIJavaScriptDefinition> definitions;

  @ZExpose
  final List<String> definitionsContent;

  @ZExpose
  String prefix;


  public ZScriptDefinitions(String prefix,
      List<ZIJavaScriptDefinition> definitions) throws Exception
  {
    this.prefix = prefix;
    this.definitions = definitions;
    this.definitionsContent = new ArrayList<String>();
    ZIObjectFactory objectFactory = ZTemplates.getApplicationService().getObjectFactory();
    ZIRenderService renderService = ZTemplates.getRenderService();
    for (ZIJavaScriptDefinition def : definitions)
    {
      Object instance = objectFactory.newInstance(def.getClazz());
      String rendered = renderService.render(instance);
      this.definitionsContent.add(rendered);
    }
  }
}