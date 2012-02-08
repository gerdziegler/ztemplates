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
    this.ztemplatesCssUrl = "/ztemplates.css";
  }


  public String computeHtmlTags(List<String> cssExposed, ZICssPreprocessor preprocessor, String ztemplatesCssDigest) throws Exception
  {
    List<String> preprocessed = preprocessor.preprocessCss(cssExposed);
    StringBuilder sb = new StringBuilder();

    for (String cssPath : cssExposed)
    {
      if (contextPath != null && cssPath.startsWith("/"))
      {
        cssPath = contextPath + cssPath;
      }
      sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + cssPath + "\">");
      sb.append('\n');
    }
    String path;
    if (contextPath != null)
    {
      path = contextPath + ztemplatesCssUrl + "?md5=" + ztemplatesCssDigest;
    }
    else
    {
      path = ztemplatesCssUrl + "?md5=" + ztemplatesCssDigest;
    }
    sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + path + "\">\n");

    return sb.toString();
  }

}
