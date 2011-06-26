package org.ztemplates.web.script.zscript;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.ZTemplates;

@ZMatch(value = "/ztemplates/zscript/${name}.js")
public class ZScriptAction
{
  private String name;


  public static String createUrl(String name) throws Exception
  {
    ZScriptAction act = new ZScriptAction();
    act.setName(name);
    return ZTemplates.getActionService().createUrl(act);
  }


  public void after() throws Exception
  {
    ZIJavaScriptRepository repo = ZTemplates.getApplicationService().getJavaScriptRepository();
    ZIJavaScriptDefinition def = repo.getDefinitions().get(name);
    if (def == null)
    {
      throw new Exception("javascript-definition not found: " + name);
    }
    ZTemplates.getServletService().render(def.getContent(), "text/javascript", def.getEncoding());
  }


  public String getName()
  {
    return name;
  }


  public void setName(String name)
  {
    this.name = name;
  }
}
