package org.ztemplates.web.script.javascript;

import java.util.HashMap;
import java.util.Map;

import org.ztemplates.render.ZIRenderApplicationContext;

public class ZCachingJavaScriptProcessorData
{
  // private static final Logger log =
  // Logger.getLogger(ZCachingJavaScriptProcessor.class);

  private final Map<String, ZJavaScriptCacheEntry> script2UrlMap = new HashMap<String, ZJavaScriptCacheEntry>();

  private final Map<String, ZJavaScriptCacheEntry> url2ContentMap = new HashMap<String, ZJavaScriptCacheEntry>();

  private static final String KEY = ZCachingJavaScriptProcessorData.class.getSimpleName();

  public static final String SUFFIX = ".js";

  private final long version = System.currentTimeMillis();

  private int cnt = 0;


  public static ZCachingJavaScriptProcessorData getInstance(
      ZIRenderApplicationContext applicationContext)
  {
    ZCachingJavaScriptProcessorData ret = (ZCachingJavaScriptProcessorData) applicationContext
        .getAttribute(KEY);
    return ret;
  }


  public static void setInstance(ZIRenderApplicationContext applicationContext,
      ZCachingJavaScriptProcessorData cachingScriptProcessor)
  {
    applicationContext.setAttribute(KEY, cachingScriptProcessor);
  }


  public ZCachingJavaScriptProcessorData()
  {
  }


  public int getNextCnt()
  {
    return cnt++;
  }


  public ZJavaScriptCacheEntry getMergedJavaScript(String scriptKey)
  {
    synchronized (this)
    {
      return url2ContentMap.get(scriptKey);
    }
  }


  public Map<String, ZJavaScriptCacheEntry> getScript2UrlMap()
  {
    return script2UrlMap;
  }


  public Map<String, ZJavaScriptCacheEntry> getUrl2ContentMap()
  {
    return url2ContentMap;
  }


  public long getVersion()
  {
    return version;
  }
}
