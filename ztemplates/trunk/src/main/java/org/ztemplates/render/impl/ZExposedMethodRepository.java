package org.ztemplates.render.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ztemplates.render.ZExpose;

public class ZExposedMethodRepository implements ZIExposedMethodRepository
{
  private static final Logger log = Logger.getLogger(ZExposedMethodRepository.class);

  private final Map<Class, List<ZIExposedValue>> cache = new HashMap<Class, List<ZIExposedValue>>();


  public List<ZIExposedValue> getExposedValues(Class clazz) throws Exception
  {
    List<ZIExposedValue> ret = cache.get(clazz);
    if (ret == null)
    {
      addExposed(clazz);
      ret = cache.get(clazz);
    }
    return ret;
  }


  public void addExposed(Class clazz) throws Exception
  {
    Map<String, ZIExposedValue> map = new HashMap<String, ZIExposedValue>();
    // fields first, take precedence over methods
    addExposedFields(clazz, map);
    addExposedMethods(clazz, map);
    List<ZIExposedValue> ret = new ArrayList<ZIExposedValue>(map.values());
    cache.put(clazz, ret);
  }


  private void addExposedMethods(Class clazz, Map<String, ZIExposedValue> map) throws Exception
  {
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
        if (map.containsKey(key))
        {
          ZIExposedValue val1 = map.get(key);
          log.warn("property exposed twice in " + clazz.getName() + " --- " + val1 + " --- " + m);
        }
        else
        {
          boolean render = exp != null && exp.render();
          ZExposedMethod em = new ZExposedMethod(key, m, render);
          map.put(em.getName(), em);
        }
      }
    }
  }


  private void addExposedFields(Class clazz, Map<String, ZIExposedValue> map) throws Exception
  {
    // all fields, from superclass too
    for (Class c = clazz; c != Object.class && c != null; c = c.getSuperclass())
    {
      for (Field f : clazz.getDeclaredFields())
      {
        ZExpose exp = f.getAnnotation(ZExpose.class);
        if (exp != null)
        {
          String name = f.getName();
          // fields from derived classes first, skip overridden fields
          if (!map.containsKey(name))
          {
            ZExposedField em = new ZExposedField(name, f, exp.render());
            map.put(em.getName(), em);
          }
        }
      }
    }
  }
}
