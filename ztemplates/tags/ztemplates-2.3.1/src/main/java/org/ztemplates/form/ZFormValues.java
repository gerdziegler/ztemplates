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

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ztemplates.actions.util.ZBase64Util;

/**
 * Holds the values (as string) from a form. Should be used to extract the
 * values from a form and keep them in a servlet session or serialize them into
 * a hidden form parameter. Never keep the form object itself in the session.
 * 
 * @author gerdziegler.de
 * 
 */
public class ZFormValues implements Serializable
{
  private HashMap<String, String[]> values = new HashMap<String, String[]>();


  /**
   * Computes the names of the properties that have different values in the two
   * ZFormValues. Use this to compute the parameters changed by a user.
   * 
   * @param oldFormValues
   * @param newFormValues
   * @return
   */
  public static Set<String> computeChangedPropertyNames(ZFormValues oldFormValues, ZFormValues newFormValues)
  {
    Map<String, String[]> oldValues = oldFormValues.getValues();
    Map<String, String[]> newValues = newFormValues.getValues();

    Set<String> ret = new HashSet<String>();
    Set<String> names = new HashSet<String>(oldValues.keySet());
    names.addAll(newValues.keySet());
    for (String name : names)
    {
      String[] oldValueArr = oldValues.get(name);
      String[] newValueArr = newValues.get(name);
      String oldValue = oldValueArr != null ? oldValueArr[0] : null;
      String newValue = newValueArr != null ? newValueArr[0] : null;
      if (oldValue == null && newValue != null || oldValue != null && !oldValue.equals(newValue))
      {
        ret.add(name);
      }
    }
    return ret;
  }


  /**
   * Deserializes the form Values from a string.
   * 
   * @param encoded
   * @return
   * @throws Exception
   */
  public static ZFormValues createFromString(String encoded) throws Exception
  {
    ZFormValues ret = new ZFormValues();
    ret.readFromString(encoded);
    return ret;
  }


  public ZFormValues()
  {
  }


  /**
   * 
   * @return the values as a Map propertyName->propertyValues
   */
  public HashMap<String, String[]> getValues()
  {
    return values;
  }


  /**
   * encodes values to a string that it can be used as value in a hidden form
   * parameter
   * 
   * @param values
   * @return
   */
  public String writeToString()
  {
    String base64 = ZBase64Util.encodeObject(values, ZBase64Util.GZIP | ZBase64Util.DONT_BREAK_LINES);
    return base64;
  }


  /**
   * decodes the values previously encoded by encodeValues
   * 
   * @param base64
   * @return
   */
  public void readFromString(String base64)
  {
    this.values = (HashMap<String, String[]>) ZBase64Util.decodeToObject(base64);
  }
}
