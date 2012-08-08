package org.ztemplates.web.script.zscript;

import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.ZTemplates;

@ZMatch(value = "/ztemplates/zscript.js")
public class ZScriptScriptAction
{

  public static String createUrl() throws Exception
  {
    ZScriptScriptAction act = new ZScriptScriptAction();
    return ZTemplates.getActionService().createUrl(act);
  }


  public void after() throws Exception
  {
    HttpServletResponse resp = ZTemplates.getServletService().getResponse();
    resp.setContentType("text/javascript");
    InputStream in = getClass().getResourceAsStream("zscript.js");
    OutputStream out = resp.getOutputStream();
    IOUtils.copy(in, out);
    out.flush();
  }
}