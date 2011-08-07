package org.ztemplates.render;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.render.impl.ZCssEngine;
import org.ztemplates.render.impl.ZCssIdRepository;
import org.ztemplates.render.impl.ZExposedMethodRepository;
import org.ztemplates.render.impl.ZICssIdRepository;
import org.ztemplates.render.impl.ZTemplateNameRepository;

public class ZRenderApplication
{
  private static final Logger log = Logger
      .getLogger(ZRenderApplication.class);

  private final ZIRenderApplicationContext applicationContext;

  private final ZICssIdRepository cssIdRepository;

  private final ZITemplateNameRepository templateNameRepository;

  // private final ZIScriptRepository scriptRepository;

  private final ZExposedMethodRepository exposedMethodRepository;

  private final ZCssEngine cssEngine;

  private final ZIClassRepository classRepository;


  public ZRenderApplication(ZIRenderApplicationContext applicationContext,
      ZIClassRepository classRepository) throws Exception
  {
    this.applicationContext = applicationContext;
    this.classRepository = classRepository;
    ZCssIdRepository cssIdRepository = new ZCssIdRepository(classRepository);
    cssIdRepository.preload();
    this.cssIdRepository = cssIdRepository;

    ZTemplateNameRepository templateNameRepository = new ZTemplateNameRepository();

    List<Class> classesAnnotatedWithRenderer = classRepository
        .getClassesAnnotatedWith(ZRenderer.class);
    templateNameRepository.preload(classesAnnotatedWithRenderer);
    this.templateNameRepository = templateNameRepository;

    // this.javaScriptProcessor = javaScriptProcessor;
    //
    // this.cssProcessor = cssProcessor;

    List<ZScript> scripts = new ArrayList<ZScript>();
    List<Class> scriptClasses = classRepository
        .getClassesAnnotatedWith(ZScript.class);
    for (Class c : scriptClasses)
    {
      ZScript sc = (ZScript) c.getAnnotation(ZScript.class);
      scripts.add(sc);
    }
    // TODO real meta
    // ZIJavaScriptMeta jsMeta = new c();
    // scriptRepository = new ZScriptRepository(jsMeta, scripts);

    exposedMethodRepository = new ZExposedMethodRepository(applicationContext);
    List<Class> exposedClasses = classRepository
        .getClassesAnnotatedWith(ZRenderer.class);
    List<String> exceptions = new ArrayList<String>();
    for (Class c : exposedClasses)
    {
      try
      {
        exposedMethodRepository.addExposed(c);
      }
      catch (Exception e)
      {
        exceptions.add(e.getMessage());
      }
    }
    if (!exceptions.isEmpty())
    {
      StringBuffer msg = new StringBuffer("Errors in exposed methods:\n");
      for (String s : exceptions)
      {
        msg.append(s);
        msg.append('\n');
      }
      throw new Exception(msg.toString());
    }

    cssEngine = new ZCssEngine(this);
  }


  public ZICssIdRepository getCssIdRepository()
  {
    return cssIdRepository;
  }


  public ZITemplateNameRepository getTemplateNameRepository()
  {
    return templateNameRepository;
  }


  // public ZIScriptRepository getScriptRepository()
  // {
  // return scriptRepository;
  // }

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
    return classRepository;
  }


  public ZCssEngine getCssEngine()
  {
    return cssEngine;
  }
}
