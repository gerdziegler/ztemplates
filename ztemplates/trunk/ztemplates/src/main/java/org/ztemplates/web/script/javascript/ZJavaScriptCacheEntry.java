package org.ztemplates.web.script.javascript;

import java.util.Collections;
import java.util.List;

import org.ztemplates.render.script.ZJavaScriptExposed;

public class ZJavaScriptCacheEntry
{
  private final String scriptId;

  private final byte[] contentGZIP;

  private final List<ZJavaScriptExposed> scripts;


  public ZJavaScriptCacheEntry(String scriptId, byte[] contentGZIP, List<ZJavaScriptExposed> scripts)
      throws Exception
  {
    super();
    this.contentGZIP = contentGZIP;
    this.scriptId = scriptId;
    this.scripts = Collections.unmodifiableList(scripts);
  }


  public byte[] getContentGZIP()
  {
    return contentGZIP;
  }


  public String getScriptId()
  {
    return scriptId;
  }


  public List<ZJavaScriptExposed> getScripts()
  {
    return scripts;
  }
}
