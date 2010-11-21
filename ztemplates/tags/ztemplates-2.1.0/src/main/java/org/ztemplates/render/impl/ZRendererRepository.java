package org.ztemplates.render.impl;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.render.ZIRenderApplicationContext;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.ZITemplateNameRepository;

public class ZRendererRepository implements ZIRendererRepository
{
  private final List<ZIRenderer> rendererCache = new ArrayList<ZIRenderer>();

  private final ZIRenderApplicationContext applicationContext;

  private final ZITemplateNameRepository templateNameRepository;


  public ZRendererRepository(ZIRenderApplicationContext applicationContext, ZITemplateNameRepository templateNameRepository)
  {
    super();
    this.applicationContext = applicationContext;
    this.templateNameRepository = templateNameRepository;
  }


  public ZIRenderer getRenderer(Class<? extends ZIRenderer> clazz) throws Exception
  {
    for (ZIRenderer r : rendererCache)
    {
      if (clazz.equals(r.getClass()))
      {
        return r;
      }
    }
    ZIRenderer renderer = clazz.newInstance();
    renderer.init(applicationContext, templateNameRepository);
    rendererCache.add(renderer);
    return renderer;
  }
}
