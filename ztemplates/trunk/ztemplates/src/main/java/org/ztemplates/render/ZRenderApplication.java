package org.ztemplates.render;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.render.impl.ZCssEngine;
import org.ztemplates.render.impl.ZCssIdRepository;
import org.ztemplates.render.impl.ZExposedMethodRepository;
import org.ztemplates.render.impl.ZICssIdRepository;
import org.ztemplates.render.impl.ZRenderEngineRepository;
import org.ztemplates.render.impl.ZTemplateNameRepository;
import org.ztemplates.render.script.ZIScriptRepository;
import org.ztemplates.render.script.ZScriptRepository;

public class ZRenderApplication
{
  private static final Logger log = Logger.getLogger(ZRenderApplication.class);

  private final ZIRenderApplicationContext applicationContext;

  private final ZICssIdRepository cssIdRepository;

  private final ZITemplateNameRepository templateNameRepository;

  private final ZIScriptRepository scriptRepository;

  private final ZExposedMethodRepository exposedMethodRepository;

  private final ZCssEngine cssEngine;

  private final ZRenderEngineRepository renderEngineRepository;


  public ZRenderApplication(ZIRenderApplicationContext applicationContext) throws Exception
  {
    this.applicationContext = applicationContext;

    ZCssIdRepository cssIdRepository = new ZCssIdRepository(applicationContext.getClassRepository());
    cssIdRepository.preload();
    this.cssIdRepository = cssIdRepository;

    ZTemplateNameRepository templateNameRepository = new ZTemplateNameRepository(applicationContext
        .getClassRepository());
    templateNameRepository.preload();
    this.templateNameRepository = templateNameRepository;

    // this.javaScriptProcessor = javaScriptProcessor;
    //
    // this.cssProcessor = cssProcessor;

    List<ZScript> scripts = new ArrayList<ZScript>();
    List<Class> scriptClasses = applicationContext.getClassRepository()
        .getClassesAnnotatedWith(ZScript.class);
    for (Class c : scriptClasses)
    {
      ZScript sc = (ZScript) c.getAnnotation(ZScript.class);
      scripts.add(sc);
    }
    scriptRepository = new ZScriptRepository(scripts);

    exposedMethodRepository = new ZExposedMethodRepository();
    List<Class> exposedClasses = applicationContext.getClassRepository()
        .getClassesAnnotatedWith(ZRenderer.class);
    for (Class c : exposedClasses)
    {
      exposedMethodRepository.addExposed(c);
    }

    cssEngine = new ZCssEngine(this);

    renderEngineRepository = new ZRenderEngineRepository();
  }


  public ZICssIdRepository getCssIdRepository()
  {
    return cssIdRepository;
  }


  public ZITemplateNameRepository getTemplateNameRepository()
  {
    return templateNameRepository;
  }


  // public ZIJavaScriptProcessor getJavaScriptProcessor()
  // {
  // return javaScriptProcessor;
  // }
  //
  //
  // public ZICssProcessor getCssProcessor()
  // {
  // return cssProcessor;
  // }

  public ZIScriptRepository getScriptRepository()
  {
    return scriptRepository;
  }


  public ZExposedMethodRepository getExposedMethodRepository()
  {
    return exposedMethodRepository;
  }


  public ZIRenderApplicationContext getApplicationContext()
  {
    return applicationContext;
  }


  public ZIClassRepository getClassRepository()
  {
    return applicationContext.getClassRepository();
  }


  public ZCssEngine getCssEngine()
  {
    return cssEngine;
  }


  public ZRenderEngineRepository getRenderEngineRepository()
  {
    return renderEngineRepository;
  }
}
