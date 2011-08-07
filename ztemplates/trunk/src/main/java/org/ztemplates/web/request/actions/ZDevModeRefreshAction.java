package org.ztemplates.web.request.actions;

import java.io.PrintWriter;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.render.ZIRenderApplicationContext;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.ZTemplatesContextListener;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.application.ZApplicationRepositoryWeb;

@ZMatch("/ztemplates/devMode/refresh")
public class ZDevModeRefreshAction
{
  public void after() throws Exception
  {
    ZIRenderApplicationContext renderContext = ZTemplates.getApplicationService().getRenderApplicationContext();
    if (!renderContext.isDevMode())
    {
      ZTemplates.getServletService().render("devMode not enabled, to enable set context parameter 'ztemplates.devMode=true'");
      return;
    }

    long time = System.currentTimeMillis();
    HttpServletRequest req = ZTemplates.getServletService().getRequest();
    HttpServletResponse resp = ZTemplates.getServletService().getResponse();
    ServletContext ctx = req.getSession().getServletContext();

    ZApplication oldApp = ZApplicationRepositoryWeb.getApplication(ctx);
    req.getSession().invalidate();

    ZTemplatesContextListener.initContext(ctx);
    ZApplication newApp = ZApplicationRepositoryWeb.getApplication(ctx);

    long time2 = System.currentTimeMillis();

    ZMatchTree matchTree = newApp.getActionApplication().getMatchTree();

    PrintWriter pw = resp.getWriter();
    pw.println("<pre>");
    pw.println("OK --- refreshed in " + (time2 - time) + " ms");
    pw.println("OLD ztemplates app: " + oldApp);
    pw.println("NEW ztemplates app: " + newApp);
    pw.println("NEW match tree");
    pw.println(matchTree.toConsoleString());
    pw.println("</pre>");
    resp.flushBuffer();
  }
}
