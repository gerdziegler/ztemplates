package org.ztemplates.render.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ztemplates.render.ZExpose;

public class ZExposedMethodRepository implements ZIExposedMethodRepository
{
  private final Map<Class, List<ZIExposedValue>> cache = new HashMap<Class, List<ZIExposedValue>>();


  public List<ZIExposedValue> getExposedValues(Class clazz) throws Exception
  {
    List<ZIExposedValue> ret = cache.get(clazz);
    if (ret != null)
    {
      return ret;
    }
    return addExposed(clazz);
  }


  public List<ZIExposedValue> addExposed(Class clazz) throws Exception
  {
    Map<String, ZIExposedValue> map = new HashMap<String, ZIExposedValue>();
    for (Field f : clazz.getFields())
    {
      ZExpose exp = f.getAnnotation(ZExpose.class);
      if (exp != null)
      {
        String name = f.getName();
        ZExposedField em = new ZExposedField(name, f, exp.render());
        map.put(em.getName(), em);
      }
    }
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
        boolean render = exp != null && exp.render();
        ZExposedMethod em = new ZExposedMethod(key, m, render);
        if (map.containsKey(em.getName()))
        {
          ZIExposedValue val1 = map.get(em.getName());
          throw new Exception("duplicate value exposed in " + clazz.getName() + " --- " + val1 + " --- " + em);
        }
        map.put(em.getName(), em);
      }
    }
    List<ZIExposedValue> ret = new ArrayList<ZIExposedValue>(map.values());
    cache.put(clazz, ret);
    return ret;
  }
}
