package org.ztemplates.web.script.css;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ztemplates.render.ZIRenderApplicationContext;

public class ZCachingCssProcessorData implements Serializable
{
  private static final Logger log = Logger.getLogger(ZCachingCssProcessorData.class);

  private final Map<String, String> path2DigestMap = new HashMap<String, String>();

  private static final String KEY = ZCachingCssProcessorData.class.getSimpleName();


  public static ZCachingCssProcessorData getInstance(ZIRenderApplicationContext ctx)
  {
    ZCachingCssProcessorData ret = (ZCachingCssProcessorData) ctx.getAttribute(KEY);
    return ret;
  }


  public static void setInstance(ZIRenderApplicationContext ctx,
      ZCachingCssProcessorData cachingScriptProcessor)
  {
    ctx.setAttribute(KEY, cachingScriptProcessor);
  }


  public Map<String, String> getPath2DigestMap()
  {
    return path2DigestMap;
  }
}
