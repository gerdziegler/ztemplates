/*
 * Copyright 2008 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 *
 * @author www.gerdziegler.de
 */
package org.ztemplates.json;

import java.lang.reflect.Method;
import java.util.List;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.ztemplates.form.ZIFormModel;
import org.ztemplates.property.ZProperty;

public class ZJsonUtil
{
  static final Logger log = Logger.getLogger(ZJsonUtil.class);


  public static JSONObject computeJSON(Object obj) throws Exception
  {
    return computeJSON(obj, null);
  }


  private static JSONObject computeJSON(Object obj, String prefix) throws Exception
  {
    if (obj == null)
    {
      return null;
    }

    boolean form = obj instanceof ZIFormModel;

    JSONObject ret = new JSONObject();
    for (Method m : obj.getClass().getMethods())
    {
      boolean ann = m.isAnnotationPresent(ZExposeJson.class);
      if (!form && !ann)
      {
        continue;
      }
      if (m.getParameterTypes().length != 0)
      {
        continue;
      }
      String methodNamePrefix;
      if (m.getName().startsWith("get"))
      {
        methodNamePrefix = "get";
      }
      else if (m.getName().startsWith("is"))
      {
        methodNamePrefix = "is";
      }
      else
      {
        continue;
      }

      Class type = m.getReturnType();
      if (!ann && !ZProperty.class.isAssignableFrom(type) && !ZIFormModel.class.isAssignableFrom(type))
      {
        continue;
      }

      Object value = m.invoke(obj);
      if (value == null)
      {
        continue;
      }
      String propName = removePrefixName(methodNamePrefix, m.getName());
      Object propValue = computeJSONValue(value, prefix != null ? prefix + "." + propName
          : propName);
      ret.put(propName, propValue);
    }
    return ret;
  }


  private static String removePrefixName(String prefix, String name)
  {
    int prefixLen = prefix.length();
    String ret = Character.toLowerCase(name.charAt(prefixLen)) + name.substring(prefixLen + 1);
    return ret;
  }


  private static Object computeJSONValue(Object value, String prefix) throws Exception
  {
    Class returnType = value.getClass();
    if (value instanceof String || value instanceof Boolean || value instanceof Integer
        || value instanceof Long || value instanceof Float || value instanceof Double
        || returnType.isPrimitive())
    {
      return value;
    }
    else if (List.class.isAssignableFrom(returnType))
    {
      JSONArray array = new JSONArray();
      List coll = (List) value;
      for (int i = 0; i < coll.size(); i++)
      {
        Object crt = coll.get(i);
        if (crt != null)
        {
          String jsonPath = prefix + "[" + i + "]";
          array.put(computeJSONValue(crt, jsonPath));
        }
      }
      return array;
    }
    else if (returnType.isArray())
    {
      JSONArray array = new JSONArray();
      Object[] arr = (Object[]) value;
      for (int i = 0; i < arr.length; i++)
      {
        Object crt = arr[i];
        String jsonPath = prefix + "[" + i + "]";
        array.put(computeJSONValue(crt, jsonPath));
      }
      return array;
    }
    else
    {
      return computeJSON(value, prefix);
    }
  }
}
