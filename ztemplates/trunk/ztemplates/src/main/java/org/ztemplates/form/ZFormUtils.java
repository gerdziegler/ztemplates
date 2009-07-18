/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
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
package org.ztemplates.form;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ztemplates.actions.util.ZBase64Util;
import org.ztemplates.property.ZProperty;

public class ZFormUtils
{
//  public static Set<String> computeChangedValues(Map<String, String> oldValues,
//      Map<String, String> newValues)
//  {
//    Set<String> names = new HashSet<String>(oldValues.keySet());
//    names.addAll(newValues.keySet());
//    Set<String> ret = new HashSet<String>();
//    for (String name : names)
//    {
//      String oldValue = oldValues.get(name);
//      String newValue = newValues.get(name);
//      if (oldValue == null && newValue != null || oldValue != null && !oldValue.equals(newValue))
//      {
//        ret.add(name);
//      }
//    }
//    return ret;
//  }


  public static HashMap<String, String> computeValues(ZIFormElement form) throws Exception
  {
    ZFormElementMirror mirr = new ZFormElementMirror(form);
    ZFormMembers members = mirr.getFormMembers();
    HashMap<String, String> values = new HashMap<String, String>();
    for (ZProperty prop : members.getProperties())
    {
      if (!prop.isEmpty())
      {
        values.put(prop.getName(), prop.getStringValue());
      }
    }
    return values;
  }


//  public static HashMap<String, String> decodeValues(String base64)
//  {
//    HashMap<String, String> ret = (HashMap<String, String>) ZBase64Util.decodeToObject(base64);
//    return ret;
//  }
//
//
//  public static String encodeValues(HashMap<String, String> values)
//  {
//    String base64 = ZBase64Util.encodeObject(values, ZBase64Util.GZIP
//        | ZBase64Util.DONT_BREAK_LINES);
//    return base64;
//  }

}
