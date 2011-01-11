package org.ztemplates.render.impl;

import org.ztemplates.render.ZIRenderer;

public interface ZIRendererRepository
{
  public ZIRenderer getRenderer(Class<? extends ZIRenderer> clazz) throws Exception;
}
