package org.ztemplates.render.script;

import java.util.List;

import org.ztemplates.web.script.javascript.ZIJavaScriptGroupingStrategy;
import org.ztemplates.web.script.javascript.ZIJavaScriptPreprocessor;

public class ZJavaScriptProcessor implements ZIJavaScriptProcessor
{
  private final String contextPath;


  public ZJavaScriptProcessor(String contextPath)
  {
    super();
    this.contextPath = contextPath;
  }


  public String computeHtmlTags(List<String> javaScriptExposed, ZIJavaScriptPreprocessor replacingStrategy, ZIJavaScriptGroupingStrategy groupingStrategy)
  {
    StringBuilder sb = new StringBuilder();

    for (String jsPath : javaScriptExposed)
    {
      if (contextPath != null && jsPath.startsWith("/"))
      {
        jsPath = contextPath + jsPath;
      }
      sb.append("<script type=\"text/javascript\" src=\"" + jsPath + "\"></script>");
      sb.append('\n');
    }

    return sb.toString();
  }
}
