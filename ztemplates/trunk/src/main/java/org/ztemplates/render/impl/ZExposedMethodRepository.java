package org.ztemplates.render.impl;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZIRenderApplicationContext;

public class ZExposedMethodRepository implements ZIExposedMethodRepository
{
  private static final Logger log = Logger.getLogger(ZExposedMethodRepository.class);

  private final Map<Class, List<ZIExposedValue>> cache = new HashMap<Class, List<ZIExposedValue>>();

  private final ZIRenderApplicationContext application;


  public ZExposedMethodRepository(ZIRenderApplicationContext application)
  {
    super();
    this.application = application;
  }


  public List<ZIExposedValue> getExposedValues(Class clazz) throws Exception
  {
    List<ZIExposedValue> exposedValues = cache.get(clazz);
    if (exposedValues == null)
    {
      exposedValues = computeExposedValues(clazz);
      if (!application.isDevMode())
      {
        cache.put(clazz, exposedValues);
      }
    }
    return exposedValues;
  }


  public void addExposed(Class clazz) throws Exception
  {
    if (application.isDevMode())
    {
      return;
    }
    cache.put(clazz, computeExposedValues(clazz));
  }


  private List<ZIExposedValue> computeExposedValues(Class clazz) throws Exception
  {
    Map<String, ZIExposedValue> map = new HashMap<String, ZIExposedValue>();
    // fields first, take precedence over methods
    addExposedFields(clazz, map);
    addExposedMethods(clazz, map);
    return new ArrayList<ZIExposedValue>(map.values());
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
          ZExposedMethod em = new ZExposedMethod(key, m, exp.render(), exp.decorator());
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
      for (Field f : c.getDeclaredFields())
      {
        ZExpose exp = f.getAnnotation(ZExpose.class);
        if (exp != null)
        {
          String name = f.getName();
          // fields from derived classes first, skip overridden fields
          if (!map.containsKey(name))
          {
            ZExposedField em = new ZExposedField(name, f, exp.render(), exp.decorator());
            map.put(em.getName(), em);
          }
        }
      }
    }
  }
}
