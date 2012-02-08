package org.ztemplates.web.script.javascript;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.ztemplates.render.script.ZIJavaScriptProcessor;

public class ZCachingJavaScriptProcessor implements ZIJavaScriptProcessor
{
  private static final Logger log = Logger.getLogger(ZCachingJavaScriptProcessor.class);

  private final ZCachingJavaScriptProcessorData data;

  private final ZICachingJavaScriptProcessorContext context;


  public ZCachingJavaScriptProcessor(ZICachingJavaScriptProcessorContext context)
  {
    this.context = context;
    this.data = context.getData();
  }


  public String computeHtmlTags(List<String> javaScript, ZIJavaScriptPreprocessor preprocessor, ZIJavaScriptGroupingStrategy groupingStrategy)
      throws Exception
  {
    List<String> preprocessed = preprocessor.preprocessJavaScript(javaScript);

    List<List<String>> grouped = groupingStrategy.group(preprocessed);

    List<ZJavaScriptTag> merged = merge(grouped);

    StringBuilder sb = new StringBuilder();
    for (ZJavaScriptTag entry : merged)
    {
      if (entry.getMergedFrom().size() > 0)
      {
        sb.append("<!-- merged from:");
        sb.append('\n');
        for (String s : entry.getMergedFrom())
        {
          sb.append(s);
          sb.append('\n');
        }
        sb.append("-->");
        sb.append('\n');
      }
      sb.append("<script type=\"text/javascript\" src=\"" + entry.getUrl() + "\"></script>");
      sb.append('\n');
    }

    return sb.toString();
  }


  public List<ZJavaScriptTag> merge(List<List<String>> grouped) throws Exception
  {
    List<ZJavaScriptTag> ret = new ArrayList<ZJavaScriptTag>();
    for (List<String> group : grouped)
    {
      if (group.size() == 1)
      {
        String url = group.get(0);
        if (url.charAt(0) == '/')
        {
          url = context.getContextPath() + url;
        }
        ret.add(new ZJavaScriptTag(url));
      }
      else
      {
        final String key = ZCachingJavaScriptProcessor.computeCacheKey(group);
        if (key.charAt(0) == '/')
        {
          ZJavaScriptCacheEntry cacheEntry;
          synchronized (data)
          {
            cacheEntry = data.getScript2UrlMap().get(key);
            if (cacheEntry == null)
            {
              List<String> mergeUrls = addContextPathToUrls(group);
              byte[] buff = context.mergeWebResources(mergeUrls);
              buff = ZCachingJavaScriptProcessor.minify(buff);
              // buff = gzip(buff);
              String scriptId = DigestUtils.md5Hex(buff) + ZCachingJavaScriptProcessorData.SUFFIX;
              // String scriptId = data.getVersion() + "-" + (data.getNextCnt())
              // + ZCachingJavaScriptProcessorData.SUFFIX;
              cacheEntry = new ZJavaScriptCacheEntry(scriptId, buff, group);
              data.getScript2UrlMap().put(key, cacheEntry);
              data.getUrl2ContentMap().put(scriptId, cacheEntry);
            }
          }
          String url = context.createUrl(cacheEntry.getScriptId());
          ZJavaScriptTag tag = new ZJavaScriptTag(url);
          for (String exp : cacheEntry.getScripts())
          {
            tag.getMergedFrom().add(exp);
          }
          ret.add(tag);
        }
        else
        {
          String url = group.get(0);
          ret.add(new ZJavaScriptTag(url));
        }
      }
    }
    return ret;
  }


  public List<String> addContextPathToUrls(List<String> group)
  {
    List<String> ret = new ArrayList<String>();

    String contextPath = context.getContextPath();
    for (String url : group)
    {
      if (contextPath != null)
      {
        url = contextPath + url;
      }
      ret.add(url);
    }

    return ret;
  }


  public ZJavaScriptCacheEntry getMergedJavaScript(String scriptKey)
  {
    synchronized (this)
    {
      return data.getUrl2ContentMap().get(scriptKey);
    }
  }


  private static String computeCacheKey(List<String> merge)
  {
    StringBuilder keyBuff = new StringBuilder();
    for (String url : merge)
    {
      keyBuff.append(url);
      keyBuff.append('|');
    }

    return keyBuff.toString();
  }


  private static byte[] minify(byte[] raw) throws Exception
  {
    log.warn("MINIFIY DISABLED");
    return raw;
  }

  //
  // /**
  // * @return the strategy
  // */
  // public ZIJavaScriptGroupingStrategy getGroupingStrategy()
  // {
  // return groupingStrategy;
  // }

  // private static byte[] minify(byte[] raw) throws Exception
  // {
  // ByteArrayOutputStream out = new ByteArrayOutputStream();
  // InputStream in = new ByteArrayInputStream(raw);
  // JSMin jsmin = new JSMin(in, out);
  // jsmin.jsmin();
  // in.close();
  // out.flush();
  // byte[] buff = out.toByteArray();
  // return buff;
  // }

  /**
   * do zipping in the web-server
   */
  // private static byte[] gzip(byte[] raw) throws Exception
  // {
  // return raw;
  // }
  // private static byte[] gzip(byte[] raw) throws Exception
  // {
  // ByteArrayOutputStream out = new ByteArrayOutputStream();
  // GZIPOutputStream zout = new GZIPOutputStream(out);
  // zout.write(raw);
  // zout.flush();
  // zout.close();
  // out.flush();
  // return out.toByteArray();
  // }
}
