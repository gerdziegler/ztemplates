package org.ztemplates.web.script.css;

import java.io.ByteArrayOutputStream;
import java.util.zip.GZIPOutputStream;

import org.apache.commons.codec.digest.DigestUtils;

public class ZScriptCacheEntry
{
  private final byte[] contentGZIP;

  private final String digest;


  public static ZScriptCacheEntry createFromRaw(byte[] content) throws Exception
  {
    ZScriptCacheEntry ret = new ZScriptCacheEntry(gzip(content));
    return ret;
  }


  private ZScriptCacheEntry(byte[] contentGZIP) throws Exception
  {
    super();
    this.contentGZIP = contentGZIP;
    this.digest = DigestUtils.md5Hex(contentGZIP);
  }


  public byte[] getContentGZIP()
  {
    return contentGZIP;
  }


  private static byte[] gzip(byte[] raw) throws Exception
  {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    GZIPOutputStream zout = new GZIPOutputStream(out);
    zout.write(raw);
    zout.flush();
    zout.close();
    out.flush();
    return out.toByteArray();
  }


  public String getDigest()
  {
    return digest;
  }
}
