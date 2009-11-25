package org.ztemplates.render.script;

import java.util.SortedSet;

public class ZJavaScriptProcessor implements ZIJavaScriptProcessor
{
  private final String contextPath;


  public ZJavaScriptProcessor(String contextPath)
  {
    super();
    this.contextPath = contextPath;
  }


  public String computeHtmlTags(SortedSet<ZJavaScriptExposed> javaScriptExposed)
  {
    StringBuffer sb = new StringBuffer();

    for (ZJavaScriptExposed s : javaScriptExposed)
    {
      String jsPath = s.getUrl();
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
