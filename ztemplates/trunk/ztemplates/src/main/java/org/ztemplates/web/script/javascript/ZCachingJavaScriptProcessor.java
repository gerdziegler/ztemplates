package org.ztemplates.web.script.javascript;

import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.ztemplates.render.script.ZIJavaScriptProcessor;
import org.ztemplates.render.script.ZJavaScriptExposed;

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


  public String computeHtmlTags(SortedSet<ZJavaScriptExposed> javaScriptExposed) throws Exception
  {
    StringBuffer sb = new StringBuffer();

    List<List<ZJavaScriptExposed>> grouped = group(javaScriptExposed);
    List<ZJavaScriptTag> merged = merge(grouped);
    for (ZJavaScriptTag entry : merged)
    {
      if (entry.getMergedFrom().size() > 0)
      {
        sb.append("\n<!-- merged from:\n");
        for (String s : entry.getMergedFrom())
        {
          sb.append(s);
          sb.append('\n');
        }
        sb.append("-->");
      }

      sb.append("\n<script type=\"text/javascript\" src=\"" + entry.getUrl() + "\"></script>");
    }

    return sb.toString();
  }


  public List<ZJavaScriptTag> merge(List<List<ZJavaScriptExposed>> grouped) throws Exception
  {
    List<ZJavaScriptTag> ret = new ArrayList<ZJavaScriptTag>();
    for (List<ZJavaScriptExposed> group : grouped)
    {
      if (group.size() == 1)
      {
        ZJavaScriptExposed exp = group.get(0);
        String url = exp.getPrefix() + exp.getUrl();
        if (url.charAt(0) == '/')
        {
          url = context.getContextPath() + url;
        }
        ret.add(new ZJavaScriptTag(url));
      }
      else
      {
        final String key = computeCacheKey(group);
        if (key.charAt(0) == '/')
        {
          ZJavaScriptCacheEntry cacheEntry;
          synchronized (data)
          {
            cacheEntry = data.getScript2UrlMap().get(key);
            if (cacheEntry == null)
            {
              List<String> mergeUrls = computeUrls(group);
              byte[] buff = context.mergeWebResources(mergeUrls);
              buff = minify(buff);
              //              buff = gzip(buff);
              String scriptId = DigestUtils.md5Hex(buff) + ZCachingJavaScriptProcessorData.SUFFIX;
              //            String scriptId = data.getVersion() + "-" + (data.getNextCnt()) + ZCachingJavaScriptProcessorData.SUFFIX;
              cacheEntry = new ZJavaScriptCacheEntry(scriptId, buff, group);
              data.getScript2UrlMap().put(key, cacheEntry);
              data.getUrl2ContentMap().put(scriptId, cacheEntry);
            }
          }
          String url = context.createUrl(cacheEntry.getScriptId());
          ZJavaScriptTag tag = new ZJavaScriptTag(url);
          for (ZJavaScriptExposed exp : cacheEntry.getScripts())
          {
            tag.getMergedFrom().add(exp.getPrefix() + exp.getUrl());
          }
          ret.add(tag);
        }
        else
        {
          ZJavaScriptExposed exp = group.get(0);
          String url = exp.getPrefix() + exp.getUrl();
          ret.add(new ZJavaScriptTag(url));
        }
      }
    }
    return ret;
  }


  public List<String> computeUrls(List<ZJavaScriptExposed> group)
  {
    List<String> ret = new ArrayList<String>();

    String contextPath = context.getContextPath();
    for (ZJavaScriptExposed crt : group)
    {
      String url = crt.getPrefix() + crt.getUrl();
      if (contextPath != null)
      {
        url = contextPath + url;
      }
      ret.add(url);
    }

    return ret;
  }


  public List<List<ZJavaScriptExposed>> group(SortedSet<ZJavaScriptExposed> javaScriptExposed)
  {
    List<List<ZJavaScriptExposed>> ret = new ArrayList<List<ZJavaScriptExposed>>();

    List<ZJavaScriptExposed> merge = null;
    for (ZJavaScriptExposed s : javaScriptExposed)
    {
      String url = s.getPrefix() + s.getUrl();
      if (url.charAt(0) == '/' && s.isMerge())
      {
        if (merge == null)
        {
          merge = new ArrayList<ZJavaScriptExposed>();
          ret.add(merge);
        }
        merge.add(s);
      }
      else
      {
        merge = new ArrayList<ZJavaScriptExposed>();
        ret.add(merge);
        merge.add(s);
        merge = null;
      }
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


  private static String computeCacheKey(List<ZJavaScriptExposed> merge)
  {
    StringBuffer keyBuff = new StringBuffer();
    for (ZJavaScriptExposed s : merge)
    {
      keyBuff.append(s.getPrefix() + s.getUrl());
      keyBuff.append('|');
    }

    return keyBuff.toString();
  }


  private static byte[] minify(byte[] raw) throws Exception
  {
    log.warn("MINIFIY DISABLED");
    return raw;
  }

  //    private static byte[] minify(byte[] raw) throws Exception
  //    {
  //      ByteArrayOutputStream out = new ByteArrayOutputStream();
  //      InputStream in = new ByteArrayInputStream(raw);
  //      JSMin jsmin = new JSMin(in, out);
  //      jsmin.jsmin();
  //      in.close();
  //      out.flush();
  //      byte[] buff = out.toByteArray();
  //      return buff;
  //    }

  /**
   * do zipping in the web-server
   */
  //  private static byte[] gzip(byte[] raw) throws Exception
  //  {
  //    return raw;
  //  }
  //  private static byte[] gzip(byte[] raw) throws Exception
  //  {
  //    ByteArrayOutputStream out = new ByteArrayOutputStream();
  //    GZIPOutputStream zout = new GZIPOutputStream(out);
  //    zout.write(raw);
  //    zout.flush();
  //    zout.close();
  //    out.flush();
  //    return out.toByteArray();
  //  }
}
