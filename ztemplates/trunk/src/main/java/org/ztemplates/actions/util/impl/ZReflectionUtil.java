/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * @author www.gerdziegler.de
 */

package org.ztemplates.actions.util.impl;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.ztemplates.actions.ZAfter;
import org.ztemplates.actions.ZBefore;
import org.ztemplates.actions.ZInit;
import org.ztemplates.property.ZProperty;
import org.ztemplates.web.ZTemplates;

public class ZReflectionUtil
{
  private static Logger log = Logger.getLogger(ZReflectionUtil.class);


  // *********************************************************************************
  // *********************************************************************************
  // *********************************************************************************

  public static void callAfter(Object obj/* , ZOperation op */) throws Exception
  {
    /*
     * if (op != null) { // if operation first try op after callback String name
     * = computeCallbackName(op.getName());
     * 
     * Method m = getAfter(obj.getClass(), name); if (m == null) { if
     * (log.isDebugEnabled()) {
     * log.debug("no callback method found in action pojo " +
     * obj.getClass().getName() + " for operation " + name +
     * " --- trying default callback after()"); }
     * 
     * m = getAfter(obj.getClass(), ""); } if (m != null) { invoke(m, obj); }
     * else { if (log.isDebugEnabled()) {
     * log.debug("no after callback method found in action pojo " +
     * obj.getClass().getName()); } } } else {
     */
    Method m = getAfter(obj.getClass(), "");
    if (m != null)
    {
      invoke(m, obj);
    }
    else
    {
      if (log.isDebugEnabled())
      {
        log.debug("no after callback method found in action pojo " + obj.getClass().getName());
      }
    }
    // }
  }


  public static boolean callAfterOperation(Object obj, String operationName) throws Exception
  {
    String name = computeAfterOperationName(operationName);
    Method m = getAfter(obj.getClass(), name);
    if (m == null)
    {
      return false;
    }
    invoke(m, obj);
    return true;
  }


  public static void callAfterReference(Object obj, String name) throws Exception
  {
    Method m = getAfter(obj.getClass(), name);
    if (m != null)
    {
      invoke(m, obj);
    }
  }


  public static void callAfterVariable(Object obj, String name) throws Exception
  {
    Method m = getAfter(obj.getClass(), name);
    if (m != null)
    {
      invoke(m, obj);
    }
  }


  public static void callBefore(Object obj) throws Exception
  {
    Method m = getBefore(obj.getClass(), "");
    if (m != null)
    {
      invoke(m, obj);
    }
  }


  public static void callBeforeReference(Object obj, String name) throws Exception
  {
    Method m = getBefore(obj.getClass(), name);
    if (m != null)
    {
      invoke(m, obj);
    }
  }


  public static void callBeforeVariable(Object obj, String name) throws Exception
  {
    Method m = getBefore(obj.getClass(), name);
    if (m != null)
    {
      invoke(m, obj);
    }
  }


  public static void callInitReference(Object obj, String name, Object value) throws Exception
  {
    Method m = getInit(obj.getClass(), name);
    if (m != null)
    {
      invoke(m, obj, value);
    }
  }


  public static ZProperty callParameterSetter(Object obj, String name, String[] value) throws Exception
  {
    ZSetParameter setter;
    Class clazz = obj.getClass();
    Method m = getSetter(clazz, name);
    if (m != null)
    {
      if (ZProperty.class.isAssignableFrom(m.getReturnType()))
      {
        ZProperty prop = (ZProperty) invoke(m, obj);
        prop.setStringValues(value);
        return prop;
      }
      setter = new ZSetParameter_Method(m);
    }
    else
    {
      Field f = getField(clazz, name);
      if (f != null)
      {
        if (ZProperty.class.isAssignableFrom(f.getType()))
        {
          ZProperty prop = (ZProperty) f.get(obj);
          prop.setStringValues(value);
          return prop;
        }
        setter = new ZSetParameter_Field(f);
      }
      else
      {
        throw new Exception("parameter setter not found: '" + name + "' in " + obj.getClass().getName());
      }
    }

    Class paramType = setter.getType();
    if (paramType.isArray())
    {
      setter.setValue(obj, (Object) value);
    }
    else
    {
      if (value == null)
      {
        setter.setValue(obj, (Object) null);
      }
      else if (value.length == 1)
      {
        String val = value[0];
        if ("".equals(val))
        {
          val = null;
        }

        if (paramType.isEnum())
        {
          setter.setValue(obj, Enum.valueOf(paramType, val));
        }
        else
        {
          setter.setValue(obj, val);
        }
      }
      else
      {
        throw new Exception("cannot assign multiple values to non array property: " + name + " " + value);
      }
    }

    return null;
  }


  public static void callVariableSetter(Object obj, String name, String value) throws Exception
  {
    ZSetParameter setter;
    Class clazz = obj.getClass();
    Method m = getSetter(clazz, name);
    if (m != null)
    {
      if (ZProperty.class.isAssignableFrom(m.getReturnType()))
      {
        ZProperty prop = (ZProperty) invoke(m, obj);
        prop.setStringValues(new String[]
        {
            value
        });
        return;
      }
      setter = new ZSetParameter_Method(m);
    }
    else
    {
      Field f = getField(clazz, name);
      if (f != null)
      {
        if (ZProperty.class.isAssignableFrom(f.getType()))
        {
          ZProperty prop = (ZProperty) f.get(obj);
          prop.setStringValues(new String[]
          {
              value
          });
          return;
        }
        setter = new ZSetParameter_Field(f);
      }
      else
      {
        throw new Exception("variable setter not found: '" + name + "' in " + obj.getClass().getName());
      }
    }

    if (setter.getType().isEnum())
    {
      Class paramType = m.getParameterTypes()[0];
      setter.setValue(obj, Enum.valueOf(paramType, value));
    }
    else
    {
      setter.setValue(obj, value);
    }
  }


  public static void callReferenceSetter(Object obj, String name, Object value) throws Exception
  {
    Method m = getSetter(obj.getClass(), name);
    if (m == null)
    {
      throw new Exception("reference setter not found: '" + name + "' in " + obj.getClass().getName());
    }
    invoke(m, obj, value);
  }


  public static String[] callParameterGetter(Object obj, String name) throws Exception
  {
    Object ret;

    Class clazz = obj.getClass();
    String getterName = computePrefixName("get", name);
    Method m = getMethod(clazz, getterName);
    if (m != null)
    {
      ret = invoke(m, obj);
    }
    else
    {
      Field f = getField(clazz, name);
      if (f != null)
      {
        ret = f.get(obj);
      }
      else
      {
        throw new Exception("parameter getter/field not found: '" + name + "' in " + obj.getClass());
      }
    }

    if (ret == null)
    {
      return null;
    }

    if (ret instanceof ZProperty)
    {
      ZProperty prop = (ZProperty) ret;
      if (prop.isEmpty())
      {
        return null;
      }

      return new String[]
      {
        prop.getStringValue()
      };
    }

    if (ret.getClass().isArray())
    {
      return (String[]) ret;
    }

    if (ret.getClass().isEnum())
    {
      return new String[]
      {
        ((Enum) ret).name()
      };
    }

    if (ret instanceof String)
    {
      return new String[]
      {
        (String) ret
      };
    }

    throw new Exception("parameter getter/field must be String, String[], Enum, Enum[]: '" + name + "' in " + obj.getClass());
  }


  public static Object callReferenceGetter(Object obj, String name) throws Exception
  {
    Method m = getGetter(obj.getClass(), name);
    if (m == null)
    {
      throw new Exception("reference getter not found: '" + name + "' in " + obj.getClass().getName());
    }

    Object ret = invoke(m, obj);
    return ret;
  }


  private static Method getMethod(Class clazz, String name)
  {
    try
    {
      return clazz.getMethod(name);
    }
    catch (NoSuchMethodException e)
    {
      return null;
    }
  }


  private static Field getField(Class clazz, String name)
  {
    try
    {
      return clazz.getField(name);
    }
    catch (NoSuchFieldException e)
    {
      return null;
    }
  }


  public static Object callFormGetter(Object obj, String name) throws Exception
  {
    String formGetter = computePrefixName("get", name);
    Method m = getMethod(obj.getClass(), formGetter);
    if (m != null)
    {
      return invoke(m, obj);
    }

    throw new Exception("form getter not found: '" + name + "' in " + obj.getClass().getName());
  }


  public static String callVariableGetter(Object obj, String name) throws Exception
  {
    Object ret;

    Class clazz = obj.getClass();
    String getterName = computePrefixName("get", name);
    Method m = getMethod(clazz, getterName);
    if (m != null)
    {
      ret = invoke(m, obj);
    }
    else
    {
      Field f = getField(clazz, name);
      if (f != null)
      {
        ret = f.get(obj);
      }
      else
      {
        throw new Exception("variable getter/field not found: '" + name + "' in " + obj.getClass());
      }
    }

    if (ret == null)
    {
      return null;
    }

    if (ret instanceof ZProperty)
    {
      ZProperty prop = (ZProperty) ret;
      return prop.getStringValue();
    }

    if (ret.getClass().isEnum())
    {
      Enum val = (Enum) invoke(m, obj);
      if (val == null)
      {
        return null;
      }
      return val.name();
    }

    if (ret instanceof String)
    {
      return (String) ret;
    }

    throw new Exception("variable getter/field must be String or Enum: '" + name + "' in " + obj.getClass());
  }


  // *********************************************************************************
  // *********************************************************************************
  // *********************************************************************************
  public static Class getReferenceType(Class clazz, String name) throws Exception
  {
    Method m = getGetter(clazz, name);
    if (m == null)
    {
      throw new Exception("reference getter not found: '" + name + "' in " + clazz.getName());
    }
    return m.getReturnType();
  }


  public static Object invoke(Method method, Object obj, Object... value) throws Exception
  {
    if (log.isDebugEnabled())
    {
      log.debug("invoke  " + method);
    }
    try
    {
      Object ret = method.invoke(obj, value);
      return ret;
    }
    catch (Exception e)
    {
      log.error("error while calling --- " + method + " --- on object " + obj + " --- with parameters " + value, e);
      throw e;
    }
  }


  /**
   * not used anymore
   * 
   * @param path
   * @return
   * @throws Exception
   */

  public static <T> T newInstance(Class<T> clazz) throws Exception
  {
    if (log.isDebugEnabled())
    {
      log.debug("new     " + clazz.getName());
    }
    Component comp = (Component) clazz.getAnnotation(Component.class);
    if (comp != null)
    {
      WebApplicationContext ctx = WebApplicationContextUtils
          .getRequiredWebApplicationContext(ZTemplates.getServletService().getRequest().getSession().getServletContext());
      String name = comp.value();
      if (name.length() > 0)
      {
        return ctx.getBean(name, clazz);
      }
      else
      {
        return ctx.getBean(clazz);
      }
    }
    else
    {
      Constructor<T> constr = clazz.getDeclaredConstructor();
      T ret;
      if (!constr.isAccessible())
      {
        // disable ReflectPermission("suppressAccessChecks") for this to work
        constr.setAccessible(true);
        ret = constr.newInstance();
      }
      else
      {
        ret = constr.newInstance();
      }
      return ret;
    }
  }


  // ***************************************************************************************
  // ***************************************************************************************
  // ***************************************************************************************

  public static String computePrefixName(String prefix, String name)
  {
    int nameLen = name.length();
    if (nameLen == 0)
    {
      return prefix;
    }
    else
    {
      int prefixLen = prefix.length();
      StringBuffer sb = new StringBuffer(prefixLen + nameLen);
      sb.append(prefix);
      sb.append(name);
      sb.setCharAt(prefixLen, Character.toUpperCase(name.charAt(0)));
      return sb.toString();
    }
  }


  private static String computeAfterOperationName(String name)
  {
    int nameLen = name.length();
    StringBuffer sb = new StringBuffer(nameLen);
    for (int i = 0; i < nameLen; i++)
    {
      if (name.charAt(i) == '.')
      {
        i++;
        sb.append(Character.toUpperCase(name.charAt(i)));
      }
      else
      {
        sb.append(name.charAt(i));
      }
    }
    return sb.toString();
  }


  public static Method getGetter(Class clazz, String name) throws Exception
  {
    String getterName = computePrefixName("get", name);
    Method ret = getMethod(clazz, getterName);
    // Method[] methods = clazz.getMethods();
    // for (Method m : methods)
    // {
    // if (m.isAnnotationPresent(ZGetter.class))
    // {
    // ZGetter ann = m.getAnnotation(ZGetter.class);
    // if (name.equals(ann.value()))
    // {
    // return m;
    // }
    // }
    // else if (m.getName().equals(getterName))
    // {
    // ret = m;
    // }
    // }
    return ret;
  }


  public static Method getSetter(Class clazz, String name) throws Exception
  {
    String setterName = computePrefixName("set", name);
    String getterName = computePrefixName("get", name);
    Method ret = null;
    Method[] methods = clazz.getMethods();
    for (Method m : methods)
    {
      if (m.getName().equals(setterName))
      {
        return m;
      }
      if (m.getName().equals(getterName) && ZProperty.class.isAssignableFrom(m.getReturnType()))
      {
        return m;
      }
    }
    return ret;
  }


  public static Method getBefore(Class clazz, String name) throws Exception
  {
    String methodName = computePrefixName("before", name);
    Method ret = null;
    Method[] methods = clazz.getMethods();
    for (Method m : methods)
    {
      if (m.isAnnotationPresent(ZBefore.class))
      {
        ZBefore ann = m.getAnnotation(ZBefore.class);
        if (name.equals(ann.value()))
        {
          return m;
        }
      }
      else if (m.getName().equals(methodName))
      {
        return m;
      }
    }
    return ret;
  }


  public static Method getAfter(Class clazz, String name) throws Exception
  {
    String methodName = computePrefixName("after", name);
    Method ret = null;
    Method[] methods = clazz.getMethods();
    for (Method m : methods)
    {
      if (m.isAnnotationPresent(ZAfter.class))
      {
        ZAfter ann = m.getAnnotation(ZAfter.class);
        if (name.equals(ann.value()))
        {
          return m;
        }
      }
      else if (m.getName().equals(methodName))
      {
        return m;
      }
    }
    return ret;
  }


  public static Method getInit(Class clazz, String name)
  {
    String methodName = computePrefixName("init", name);
    Method ret = null;
    Method[] methods = clazz.getMethods();
    for (Method m : methods)
    {
      if (m.isAnnotationPresent(ZInit.class))
      {
        ZInit ann = m.getAnnotation(ZInit.class);
        if (name.equals(ann.value()))
        {
          return m;
        }
      }
      else if (m.getName().equals(methodName))
      {
        return m;
      }
    }
    return ret;
  }


  public static void callBeforeForm(Object obj, String name) throws Exception
  {
    Method m = getBefore(obj.getClass(), name);
    if (m != null)
    {
      invoke(m, obj);
    }
  }


  public static void callAfterForm(Object obj, String name) throws Exception
  {
    Method m = getAfter(obj.getClass(), name);
    if (m != null)
    {
      invoke(m, obj);
    }
  }


  public static String removePrefixName(String prefix, String name)
  {
    int prefixLen = prefix.length();
    String ret = Character.toLowerCase(name.charAt(prefixLen)) + name.substring(prefixLen + 1);
    return ret;
  }
}
