package org.ztemplates.render.script;

import java.util.SortedSet;

public class ZCssProcessor implements ZICssProcessor
{
  private final String contextPath;

  private final String ztemplatesCssUrl;


  public ZCssProcessor(String contextPath)
  {
    super();
    this.contextPath = contextPath;
    this.ztemplatesCssUrl = "/ztemplates.css?ver=" + System.currentTimeMillis();
  }


  public String computeHtmlTags(SortedSet<ZCssExposed> cssExposed)
  {
    StringBuffer sb = new StringBuffer();

    for (ZCssExposed s : cssExposed)
    {
      String cssPath = s.getUrl();
      if (contextPath != null && cssPath.startsWith("/"))
      {
        cssPath = contextPath + cssPath;
      }
      sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssPath + "\">");
      sb.append('\n');
    }

    if (contextPath != null)
    {
      String path = contextPath + ztemplatesCssUrl;
      sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + path + "\">\n");
    }
    else
    {
      String path = ztemplatesCssUrl;
      sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + path + "\">\n");
    }

    return sb.toString();
  }
}
