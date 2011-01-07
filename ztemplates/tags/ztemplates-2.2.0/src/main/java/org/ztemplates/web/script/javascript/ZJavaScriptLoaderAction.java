package org.ztemplates.web.script.javascript;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.application.ZHttpUtil;

@ZMatch("/ztemplates-js-*{scriptKey}")
public class ZJavaScriptLoaderAction
{
  protected static Logger log = Logger.getLogger(ZJavaScriptLoaderAction.class);

  private String scriptKey;


  public static String createUrl(String scriptKey) throws Exception
  {
    ZJavaScriptLoaderAction act = new ZJavaScriptLoaderAction();
    act.setScriptKey(scriptKey);
    return ZTemplates.getActionService().createUrl(act);
  }


  public void after() throws Exception
  {
    ZIServletService ss = ZTemplates.getServletService();
    HttpServletResponse resp = ss.getResponse();
    ZHttpUtil.addCachingHeaders(resp);
    ZCachingJavaScriptProcessorData proc = ZCachingJavaScriptProcessorData.getInstance(ZTemplates
        .getApplicationService().getRenderApplicationContext());
    ZJavaScriptCacheEntry js = proc.getMergedJavaScript(scriptKey);
    if (js != null)
    {
      ServletOutputStream out = resp.getOutputStream();
      resp.setContentType("text/javascript");
      out.write(js.getContentGZIP());
      //no more zipping done in ztemplates, do this in webserver
      //      String accept = ss.getRequest().getHeader("Accept-Encoding");
      //      if (accept != null && accept.toLowerCase().indexOf("gzip") >= 0)
      //      {
      //        resp.setHeader("Content-Encoding", "gzip");
      //        out.write(js.getContentGZIP());
      //      }
      //      else
      //      {
      //        log.warn("decompressing " + scriptKey + " ...");
      //        InputStream in = new GZIPInputStream(new ByteArrayInputStream(js.getContentGZIP()));
      //        try
      //        {
      //          ZCopyUtil.copy(in, out);
      //        }
      //        finally
      //        {
      //          in.close();
      //        }
      //      }
      out.flush();
    }
    else
    {
      log.warn("no script found " + scriptKey + " sending redirect");
      resp.sendRedirect(ss.getRequest().getContextPath() + scriptKey);
    }
  }


  public String getScriptKey()
  {
    return scriptKey;
  }


  public void setScriptKey(String scriptKey)
  {
    this.scriptKey = scriptKey;
  }

}
