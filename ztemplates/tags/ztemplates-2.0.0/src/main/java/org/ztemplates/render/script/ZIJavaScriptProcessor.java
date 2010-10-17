package org.ztemplates.render.script;

import java.util.List;

import org.ztemplates.web.script.javascript.ZIJavaScriptGroupingStrategy;
import org.ztemplates.web.script.javascript.ZIJavaScriptPreprocessor;

public interface ZIJavaScriptProcessor
{
  public String computeHtmlTags(List<String> urls, ZIJavaScriptPreprocessor replacingStrategy, ZIJavaScriptGroupingStrategy groupingStrategy)
      throws Exception;
}
