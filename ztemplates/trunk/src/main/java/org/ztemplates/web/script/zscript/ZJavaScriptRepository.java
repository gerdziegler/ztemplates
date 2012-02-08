package org.ztemplates.web.script.zscript;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.io.IOUtils;
import org.ztemplates.render.impl.ZReplaceUtil;
import org.ztemplates.web.application.ZApplicationContextWebImpl;

public class ZJavaScriptRepository implements Serializable, ZIJavaScriptRepository
{
  private static final long serialVersionUID = 1L;

  private final Map<String, ZIJavaScriptDefinition> definitions = new HashMap<String, ZIJavaScriptDefinition>();

  private ServletContext ctx;

  private String encoding;

  private String content;

  private boolean contentInitialized = false;

  private ZApplicationContextWebImpl applicationContext;


  public ZJavaScriptRepository(ZApplicationContextWebImpl applicationContext,
      ServletContext ctx,
      String encoding)
  {
    super();
    this.ctx = ctx;
    this.encoding = encoding;
    this.applicationContext = applicationContext;
  }


  public Map<String, ZIJavaScriptDefinition> getDefinitions()
  {
    return definitions;
  }


  public String getPrefix() throws IOException
  {
    if (!contentInitialized)
    {
      initContent();
      contentInitialized = !applicationContext.isDevMode();
    }
    return content;
  }


  private void initContent() throws IOException
  {
    InputStream in = ctx.getResourceAsStream("/ztemplates/zscript-definitions.js");
    if (in != null)
    {
      try
      {
        content = IOUtils.toString(in, encoding);
        Map<String, Object> values = new HashMap<String, Object>();
        values.put("contextPath", ctx.getContextPath());
        StringBuilder sb = new StringBuilder(content);
        ZReplaceUtil.merge(sb, values);
        content = sb.toString();
      }
      finally
      {
        IOUtils.closeQuietly(in);
      }
    }
  }
}