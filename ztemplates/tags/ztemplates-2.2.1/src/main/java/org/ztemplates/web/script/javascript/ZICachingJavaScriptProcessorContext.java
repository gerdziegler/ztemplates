package org.ztemplates.web.script.javascript;

import java.util.List;

public interface ZICachingJavaScriptProcessorContext
{
  public byte[] mergeWebResources(List<String> urls) throws Exception;


  public ZCachingJavaScriptProcessorData getData();


  public String getContextPath();


  public String createUrl(String js) throws Exception;
}
