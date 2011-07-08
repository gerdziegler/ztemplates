package org.ztemplates.impl.util;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;

public class StreamUtil
{
  public static byte[] readStream(InputStream in) throws Exception
  {
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    byte[] arr = new byte[1024];
    for (int cnt = in.read(arr); cnt > 0; cnt = in.read(arr))
    {
      out.write(arr, 0, cnt);
    }
    byte[] ret = out.toByteArray();
    try
    {
      in.close();
    }
    catch (Exception e)
    {
      // ignore
    }
    return ret;
  }
}
