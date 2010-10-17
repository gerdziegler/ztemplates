package org.ztemplates.render.impl;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ztemplates.render.ZExpose;

public class ZExposedMethodRepository implements ZIExposedMethodRepository
{
  private final Map<Class, List<ZExposedMethod>> cache = new HashMap<Class, List<ZExposedMethod>>();


  public List<ZExposedMethod> getExposedMethods(Class clazz) throws Exception
  {
    List<ZExposedMethod> ret = cache.get(clazz);
    if (ret != null)
    {
      return ret;
    }
    return addExposed(clazz);
  }


  public List<ZExposedMethod> addExposed(Class clazz) throws Exception
  {
    List<ZExposedMethod> ret = new ArrayList<ZExposedMethod>();
    for (Method m : clazz.getMethods())
    {
      ZExpose exp = m.getAnnotation(ZExpose.class);
      if (exp != null)
      {
        String name = m.getName();
        String key;
        if (name.startsWith("get"))
        {
          key = Character.toLowerCase(name.charAt(3)) + name.substring(4);
        }
        else if (name.startsWith("is"))
        {
          key = Character.toLowerCase(name.charAt(2)) + name.substring(3);
        }
        else
        {
          throw new Exception("Only getters can be annotated with " + ZExpose.class.getName() + " "
              + m);
        }
        ZExposedMethod em = new ZExposedMethod(key, m);
        ret.add(em);
      }
    }
    cache.put(clazz, ret);
    return ret;
  }
}
