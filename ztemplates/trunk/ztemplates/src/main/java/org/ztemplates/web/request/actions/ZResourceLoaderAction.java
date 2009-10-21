package org.ztemplates.web.request.actions;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.ztemplates.render.impl.ZCopyUtil;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.application.ZHttpUtil;

public abstract class ZResourceLoaderAction
{
  protected static Logger log = Logger.getLogger(ZResourceLoaderAction.class);

  private String resourcePath;

  private String prefix;

  private int cacheSize = 2048;


  protected ZResourceLoaderAction(String prefix)
  {
    this.prefix = prefix;
  }


  protected ZResourceLoaderAction()
  {
    this.prefix = "/" + getClass().getPackage().getName().replace('.', '/');
  }


  public void after() throws Exception
  {
    String resource = prefix + resourcePath;

    InputStream in = getClass().getResourceAsStream(resource);
    if (in == null)
    {
      throw new IOException(getClass().getName() + " resource not found: " + resource);
    }
    HttpServletResponse response = ZTemplates.getServletService().getResponse();
    if (resource.endsWith(".js"))
    {
      response.setContentType("text/javascript");
    }
    else if (resource.endsWith(".css"))
    {
      response.setContentType("text/css");
    }
    ZHttpUtil.addCachingHeaders(response);
    try
    {
      OutputStream out = response.getOutputStream();
      ZCopyUtil.copy(in, out);
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


  public int getCacheSize()
  {
    return cacheSize;
  }


  public void setCacheSize(int cacheSize)
  {
    this.cacheSize = cacheSize;
  }


  public String getPrefix()
  {
    return prefix;
  }


  public void setPrefix(String prefix)
  {
    this.prefix = prefix;
  }
}
