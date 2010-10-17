package org.ztemplates.web.request.actions.admin;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSecure;
import org.ztemplates.web.ZTemplates;

@ZMatch("/ztemplates/admin")
@ZSecure("ztemplates-admin")
public class ZAdminAction
{
  public void after() throws Exception
  {
    StringBuffer sb = new StringBuffer();
    ZAdminView view = new ZAdminView();
    view.setInfo(sb.toString());
    ZTemplates.getServletService().render(view);
  }
}
