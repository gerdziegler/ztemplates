package org.ztemplates.render.script;

import java.util.SortedSet;

public interface ZIJavaScriptProcessor
{
  public String computeHtmlTags(SortedSet<ZJavaScriptExposed> javaScriptExposed) throws Exception;
}
