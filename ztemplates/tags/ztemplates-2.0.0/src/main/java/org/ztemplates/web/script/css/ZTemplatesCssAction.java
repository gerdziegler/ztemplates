package org.ztemplates.web.script.css;

import javax.servlet.http.HttpServletResponse;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.application.ZHttpUtil;

@ZMatch(value = "/ztemplates.css")
public class ZTemplatesCssAction
{
  public void after() throws Exception
  {
    HttpServletResponse resp = ZTemplates.getServletService().getResponse();
    ZHttpUtil.addCachingHeaders(resp);
    String ret = ZTemplates.getRenderService().renderZtemplatesCss();
    ZTemplates.getServletService().render(ret, "text/css");
  }
}
