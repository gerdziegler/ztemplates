package org.ztemplates.render.script;

import java.util.List;

import org.ztemplates.web.script.css.ZICssPreprocessor;

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


  public String computeHtmlTags(List<String> cssExposed, ZICssPreprocessor preprocessor) throws Exception
  {
    List<String> preprocessed = preprocessor.preprocessCss(cssExposed);
    StringBuffer sb = new StringBuffer();

    for (String cssPath : cssExposed)
    {
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
