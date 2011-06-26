package org.ztemplates.test;

import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZActionApplication;
import org.ztemplates.actions.ZIActionApplicationContext;
import org.ztemplates.render.ZIRenderApplicationContext;
import org.ztemplates.render.ZRenderApplication;
import org.ztemplates.web.application.ZApplication;

public class ZTestApplication
{
  public static ZApplication create(ZIClassRepository classRepository) throws Exception
  {
    return create(classRepository, null);
  }


  public static ZApplication create(ZIClassRepository classRepository, String contextPath) throws Exception
  {
    ZIActionApplicationContext actionContext = new ZTestApplicationContext(classRepository);
    ZIRenderApplicationContext renderContext = new ZTestApplicationContext(classRepository);

    ZActionApplication actionApplication = new ZActionApplication(actionContext, classRepository);
    ZRenderApplication renderApplication = new ZRenderApplication(renderContext, classRepository);

    // ZVelocityRenderer.init(renderApplication.getApplicationContext());

    ZApplication ret = new ZApplication(classRepository, null, actionApplication, renderApplication);
    return ret;
  }
}
