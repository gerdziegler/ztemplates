package org.ztemplates.render.impl;

import java.util.Map;

public interface ZIRenderEngineListener
{
  public void beforeRender(Object obj, Map<String, Object> exposed) throws Exception;
}
