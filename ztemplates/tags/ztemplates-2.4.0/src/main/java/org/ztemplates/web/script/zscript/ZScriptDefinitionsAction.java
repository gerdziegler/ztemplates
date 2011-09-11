package org.ztemplates.web.script.zscript;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.ZIApplicationService;
import org.ztemplates.web.ZTemplates;

@ZMatch(value = "/ztemplates/zscript-definitions.js")
public class ZScriptDefinitionsAction
{
  public static String createUrl() throws Exception
  {
    ZScriptDefinitionsAction act = new ZScriptDefinitionsAction();
    return ZTemplates.getActionService().createUrl(act);
  }


  public void after() throws Exception
  {
    ZIApplicationService appService = ZTemplates.getApplicationService();
    ZIJavaScriptRepository repo = appService.getJavaScriptRepository();
    // boolean nomergeScripts =
    // "true".equals(appService.getRenderApplicationContext().getInitParameter("ztemplates.zscript.nomerge"));

    List<ZIJavaScriptDefinition> definitions = new ArrayList<ZIJavaScriptDefinition>(repo.getDefinitions().values());

    ZScriptDefinitions defs = new ZScriptDefinitions(definitions);
    ZTemplates.getServletService().render(defs, "text/javascript");
    return;
  }
}