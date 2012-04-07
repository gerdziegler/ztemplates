package org.ztemplates.render.impl;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ztemplates.render.ZITemplateNameRepository;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZTemplate;

public class ZTemplateNameRepository implements ZITemplateNameRepository
{
  private final static Logger log = Logger.getLogger(ZTemplateNameRepository.class);

  private final Map<String, String> cache = new HashMap<String, String>();


  public ZTemplateNameRepository()
  {
    super();
  }


  public void preload(List<Class> classes)
  {
    for (Class c : classes)
    {
      registerTemplateName(c);
    }
  }


  public String registerTemplateName(final Class clazz)
  {
    final String key = clazz.getName();
    String ret;
    Class pojoClass = clazz;
    ZTemplate templateAnnot = (ZTemplate) pojoClass.getAnnotation(ZTemplate.class);
    if (templateAnnot != null)
    {
      String tpl = templateAnnot.value();
      if (tpl.charAt(0) == '/')
      {
        cache.put(key, tpl);
        ret = tpl;
      }
      else
      {
        String template = pojoClass.getName();
        template = template.substring(0, template.lastIndexOf('.'));
        template = template.replace('.', '/');
        template += "/" + tpl;
        ret = template;
      }
    }
    else
    {
      ret = computeTemplateNameFromClass(pojoClass);
    }
    cache.put(key, ret);
    return ret;
  }


  private String computeTemplateNameFromClass(Class clazz)
  {
    for (Class crtClass = clazz; crtClass != Object.class; crtClass = crtClass.getSuperclass())
    {
      Annotation[] anns = crtClass.getDeclaredAnnotations();
      for (Annotation ann : anns)
      {
        if (ann instanceof ZRenderer)
        {
          String className = crtClass.getName();
          //      if (className.lastIndexOf('$') >= 0)
          //      {
          //        className = pojoClass.getSuperclass().getName();
          //      }
          return className.replace('.', '/');
        }
      }
    }
    return null;
  }


  public String getTemplateName(final Class clazz)
  {
    String key = clazz.getName();
    String ret = cache.get(key);
    if (ret == null)
    {
      ret = registerTemplateName(clazz);
    }
    return ret;
  }

}
