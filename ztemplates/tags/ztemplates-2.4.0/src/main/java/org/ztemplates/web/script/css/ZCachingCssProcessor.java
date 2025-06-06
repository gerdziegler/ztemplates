package org.ztemplates.web.script.css;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ztemplates.render.script.ZICssProcessor;
import org.ztemplates.web.ZTemplates;

public class ZCachingCssProcessor implements ZICssProcessor
{
  private static final Logger log = Logger.getLogger(ZCachingCssProcessor.class);

  private final ZCachingCssProcessorData data;


  public ZCachingCssProcessor(ZCachingCssProcessorData data)
  {
    this.data = data;
  }


  public String computeHtmlTags(List<String> cssExposed, ZICssPreprocessor preprocessor) throws Exception
  {
    List<String> preprocessed = preprocessor.preprocessCss(cssExposed);

    StringBuffer sb = new StringBuffer();
    String contextPath = ZTemplates.getServletService().getRequest().getContextPath();
    Map<String, String> path2DigestMap = data.getPath2DigestMap();
    for (int i = 0; i < preprocessed.size(); i++)
    {
      String path = preprocessed.get(i);
      if (path.startsWith("/"))
      {
        addLink(sb, contextPath, path, path2DigestMap);
      }
      else
      {
        sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + path + "\">");
      }
      sb.append('\n');
    }
    addLink(sb, contextPath, "/ztemplates.css", path2DigestMap);
    return sb.toString();
  }


  private void addLink(StringBuffer sb, String contextPath, String path, Map<String, String> path2DigestMap) throws Exception
  {
    // String digest = path2DigestMap.get(path);
    // if (digest == null)
    // {
    // log.info("loading " + path);
    // byte[] buff = ZMergeUtil.getFromDispatcher(path);
    // digest = DigestUtils.md5Hex(buff);
    // synchronized (path2DigestMap)
    // {
    // path2DigestMap.put(path, digest);
    // }
    // }
    sb.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"" + contextPath + path /*
                                                                                         * +
                                                                                         * "?ver="
                                                                                         * +
                                                                                         * digest
                                                                                         */+ "\">");
  }
}
