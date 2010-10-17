package org.ztemplates.web.script.javascript;

import java.util.Collections;
import java.util.List;

public class ZJavaScriptCacheEntry
{
  private final String scriptId;

  private final byte[] contentGZIP;

  private final List<String> scripts;


  public ZJavaScriptCacheEntry(String scriptId, byte[] contentGZIP, List<String> scripts) throws Exception
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


  public List<String> getScripts()
  {
    return scripts;
  }
}
