package org.ztemplates.render;

import org.ztemplates.render.impl.ZExposedMethodRepository;
import org.ztemplates.render.impl.ZIExposedMethodRepository;
import org.ztemplates.render.impl.ZIRendererRepository;
import org.ztemplates.render.impl.ZRenderEngine;
import org.ztemplates.render.impl.ZRendererRepository;
import org.ztemplates.render.impl.ZTemplateNameRepository;

public class ZRenderEngineFactory
{
  public static ZIRenderEngine createStandalone(ZIRenderApplicationContext applicationContext) throws Exception
  {
    ZIExposedMethodRepository exposedMethodRepository = new ZExposedMethodRepository(applicationContext);
    ZITemplateNameRepository templateNameRepository = new ZTemplateNameRepository();
    ZIRendererRepository rendererRepository = new ZRendererRepository(applicationContext, templateNameRepository);
    return new ZRenderEngine(exposedMethodRepository, rendererRepository, false);
  }
}
