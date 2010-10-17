package org.ztemplates.render.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.zclasspath.ZIClassRepository;
import org.ztemplates.render.ZRenderer;

public class ZCssIdRepository implements ZICssIdRepository
{
  private final ZIClassRepository classRepository;

  private final Map<String, String> cache = new HashMap<String, String>();


  public ZCssIdRepository(ZIClassRepository classRepository)
  {
    super();
    this.classRepository = classRepository;
  }


  public void preload()
  {
    List<Class> classes = classRepository.getClassesAnnotatedWith(ZRenderer.class);
    for (Class c : classes)
    {
      register(c);
    }
  }


  public String register(final Class clazz)
  {
    Class pojoClass = classRepository.getDeclaringClass(clazz, ZRenderer.class);
    if (pojoClass == null)
    {
      pojoClass = clazz;
    }
    String ret = pojoClass.getSimpleName();
    String key = clazz.getName();
    cache.put(key, ret);
    return ret;
  }


  public String getCssId(final Class clazz)
  {
    String key = clazz.getName();
    String ret = cache.get(key);
    if (ret == null)
    {
      ret = register(clazz);
    }
    return ret;
  }
}
