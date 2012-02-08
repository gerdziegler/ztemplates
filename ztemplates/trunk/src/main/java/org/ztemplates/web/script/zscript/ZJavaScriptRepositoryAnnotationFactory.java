package org.ztemplates.web.script.zscript;

import java.util.List;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.web.application.ZApplicationContextWebImpl;

public class ZJavaScriptRepositoryAnnotationFactory
{
  private static final Logger log = Logger.getLogger(ZJavaScriptRepositoryAnnotationFactory.class);

  private final ZIClassRepository classRepository;

  private final String encoding;

  private ServletContext ctx;

  private final ZApplicationContextWebImpl applicationContext;


  public ZJavaScriptRepositoryAnnotationFactory(ZApplicationContextWebImpl applicationContext,
      ServletContext ctx,
      ZIClassRepository classRepository,
      String encoding)
  {
    super();
    this.applicationContext = applicationContext;
    this.classRepository = classRepository;
    this.encoding = encoding;
    this.ctx = ctx;
  }


  public ZIJavaScriptRepository createJavaScriptRepository() throws Exception
  {
    final ZIJavaScriptRepository ret = new ZJavaScriptRepository(applicationContext, ctx, encoding);
    List<Class> definitions = classRepository.getClassesAnnotatedWith(ZScriptDefinition.class);
    for (Class clazz : definitions)
    {
      ZIJavaScriptDefinition def = new ZJavaScriptAnnotationDefinition(clazz, encoding);
      ret.getDefinitions().put(def.getName(), def);
    }
    return ret;
  }
}