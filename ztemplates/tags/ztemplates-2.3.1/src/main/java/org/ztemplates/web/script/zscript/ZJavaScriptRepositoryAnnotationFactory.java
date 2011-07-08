package org.ztemplates.web.script.zscript;

import java.util.List;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;

public class ZJavaScriptRepositoryAnnotationFactory
{
  private static final Logger log = Logger.getLogger(ZJavaScriptRepositoryAnnotationFactory.class);

  private final ZIClassRepository classRepository;

  private final String encoding;


  public ZJavaScriptRepositoryAnnotationFactory(ZIClassRepository classRepository,
      String encoding)
  {
    super();
    this.classRepository = classRepository;
    this.encoding = encoding;
  }


  public ZIJavaScriptRepository createJavaScriptRepository() throws Exception
  {
    final ZIJavaScriptRepository ret = new ZJavaScriptRepository();

    List<Class> definitions = classRepository.getClassesAnnotatedWith(ZScriptDefinition.class);
    for (Class clazz : definitions)
    {
      ZIJavaScriptDefinition def = new ZJavaScriptAnnotationDefinition(clazz, encoding);
      ret.getDefinitions().put(def.getName(), def);
    }
    return ret;
  }

}
