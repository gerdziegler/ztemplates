package org.ztemplates.web.standalone;

import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZActionApplication;
import org.ztemplates.commons.ZIObjectFactory;
import org.ztemplates.commons.ZObjectFactory;
import org.ztemplates.render.ZRenderApplication;
import org.ztemplates.web.application.ZApplication;

public class ZTemplatesStandaloneApplication
{
  public static ZApplication create(ZIClassRepository classRepository) throws Exception
  {
    return create(classRepository, null);
  }


  public static ZApplication create(ZIClassRepository classRepository, String contextPath) throws Exception
  {
    ZTemplatesStandaloneApplicationContext context = new ZTemplatesStandaloneApplicationContext(classRepository);
    ZIObjectFactory objectFactory = new ZObjectFactory();
    ZActionApplication actionApplication = new ZActionApplication(context, classRepository, objectFactory);
    ZRenderApplication renderApplication = new ZRenderApplication(context, classRepository, objectFactory);
    ZApplication ret = new ZApplication(classRepository, null, actionApplication, renderApplication);
    return ret;
  }
}
