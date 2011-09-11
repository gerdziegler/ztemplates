package org.ztemplates.web.request.actions.res;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.ZTemplates;

@ZMatch("/res*{resourcePath}")
public class ZResAction
{
  protected static Logger log = Logger.getLogger(ZResAction.class);

  private String resourcePath;


  public void after() throws Exception
  {
    log.info(resourcePath);

    InputStream in = getClass().getResourceAsStream(resourcePath);
    if (in == null)
    {
      throw new IOException("resource not found: " + resourcePath);
    }
    in = new BufferedInputStream(in);
    try
    {
      OutputStream out = new BufferedOutputStream(ZTemplates.getServletService().getResponse()
          .getOutputStream());
      try
      {
        byte[] buff = new byte[2048];
        int c = in.read(buff);
        while (c > 0)
        {
          out.write(buff, 0, c);
          c = in.read(buff);
        }
      }
      finally
      {
        out.flush();
      }
    }
    finally
    {
      in.close();
    }
  }


  public String getResourcePath()
  {
    return resourcePath;
  }


  public void setResourcePath(String resource)
  {
    this.resourcePath = resource;
  }
}
