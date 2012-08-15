package org.ztemplates.web.script.zscript;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.commons.ZIObjectFactory;
import org.ztemplates.web.ZIApplicationService;
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
    ZIApplicationService applicationService = ZTemplates.getApplicationService();
    ZIJavaScriptRepository repo = applicationService.getJavaScriptRepository();
    ZIObjectFactory objectFactory = applicationService.getObjectFactory();
    ZIJavaScriptDefinition def = repo.getDefinitions().get(name);
    if (def == null)
    {
      throw new Exception("javascript-definition not found: " + name);
    }

    Object instance = objectFactory.newInstance(def.getClazz());
    ZTemplates.getServletService().render(instance, "text/javascript", def.getEncoding());
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
