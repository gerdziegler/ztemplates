package org.ztemplates.web.script.zscript;

import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.impl.util.StreamUtil;
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
    byte[] script = StreamUtil.readStream(getClass().getResourceAsStream("zscript.js"));
    HttpServletResponse resp = ZTemplates.getServletService().getResponse();
    resp.setContentType("text/javascript");
    OutputStream out = resp.getOutputStream();
    out.write(script);
    out.flush();
  }
}