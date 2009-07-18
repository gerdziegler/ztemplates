package org.ztemplates.render.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zclasspath.ZIClassRepository;
import org.ztemplates.render.ZITemplateNameRepository;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZTemplate;

public class ZTemplateNameRepository implements ZITemplateNameRepository
{
  private final ZIClassRepository classRepository;

  private final Map<String, String> cache = new HashMap<String, String>();


  public ZTemplateNameRepository(ZIClassRepository classRepository)
  {
    super();
    this.classRepository = classRepository;
  }


  public void preload()
  {
    List<Class> classes = classRepository.getClassesAnnotatedWith(ZRenderer.class);
    for (Class c : classes)
    {
      registerTemplateName(c);
    }
  }


  public void registerTemplateName(final Class clazz)
  {
    final String key = clazz.getName();
    Class pojoClass = clazz;
    ZTemplate templateAnnot = (ZTemplate) pojoClass.getAnnotation(ZTemplate.class);
    if (templateAnnot != null)
    {
      String tpl = templateAnnot.value();
      if ("".equals(templateAnnot.value()))
      {
        pojoClass = classRepository.getDeclaringClass(pojoClass, ZTemplate.class);
      }
      else if (templateAnnot.relative())
      {
        pojoClass = classRepository.getDeclaringClass(pojoClass, ZTemplate.class);
        String template = pojoClass.getName();
        template = template.substring(0, template.lastIndexOf('.'));
        template = template.replace('.', '/');
        template += "/" + tpl;
        cache.put(key, template);
        return /*template*/;
      }
      else
      {
        cache.put(key, tpl);
        return /*tpl*/;
      }
    }
    String className = pojoClass.getName();
    if (className.lastIndexOf('$') >= 0)
    {
      className = pojoClass.getSuperclass().getName();
    }
    String ret = className.replace('.', '/');
    cache.put(key, ret);
    return /*ret*/;
  }


  public String getTemplateName(final Class clazz)
  {
    String key = clazz.getName();
    String ret = cache.get(key);
    if (ret == null)
    {
      throw new RuntimeException("no template found for " + clazz.getName()
          + " --- check your classpath");
    }
    return ret;
  }

}
