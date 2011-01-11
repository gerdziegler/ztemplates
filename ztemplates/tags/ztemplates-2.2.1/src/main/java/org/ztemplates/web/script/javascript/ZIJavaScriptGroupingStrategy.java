package org.ztemplates.web.script.javascript;

import java.util.List;

/**
 * 
 * @author gerd
 * 
 */
public interface ZIJavaScriptGroupingStrategy
{
  public List<List<String>> group(List<String> javaScriptExposed);
}
