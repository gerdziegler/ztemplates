package org.ztemplates.web.request.actions;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.render.ZIRenderApplicationContext;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.application.ZApplicationRepositoryWeb;

@ZMatch("/ztemplates/devMode/actions")
public class ZDevModeActionsAction
{
  public void after() throws Exception
  {
    ZIRenderApplicationContext renderContext = ZTemplates.getApplicationService().getRenderApplicationContext();
    if (!renderContext.isDevMode())
    {
      ZTemplates.getServletService().render("devMode not enabled, to enable set context parameter 'ztemplates.devMode=true'");
      return;
    }

    HttpServletRequest req = ZTemplates.getServletService().getRequest();
    HttpServletResponse resp = ZTemplates.getServletService().getResponse();
    ServletContext ctx = req.getSession().getServletContext();
    ZApplication newApp = ZApplicationRepositoryWeb.getApplication(ctx);
    ZMatchTree matchTree = newApp.getActionApplication().getMatchTree();

    PrintWriter pw = resp.getWriter();
    pw.println("<pre>");
    pw.println(matchTree.toConsoleString());
    pw.println("</pre>");
    resp.flushBuffer();
  }
}
