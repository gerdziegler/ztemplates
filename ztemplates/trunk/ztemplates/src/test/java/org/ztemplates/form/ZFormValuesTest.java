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
import java.util.Set;

import junit.framework.TestCase;

public class ZFormValuesTest extends TestCase
{
  public void test1() throws Exception
  {
    HashMap<String, String[]> map = new HashMap<String, String[]>();
    map.put("key1", new String[]
    {
      "val1"
    });
    map.put("key2", new String[]
    {
      "val2"
    });
    map.put("key3", new String[]
    {
      "val3"
    });
    map.put("key4", new String[]
    {
      "val4"
    });
    map.put("key5", new String[]
    {
      "val5"
    });
    map.put("key6", new String[]
    {
      "val6"
    });

    ZFormValues values = new ZFormValues();
    values.getValues().putAll(map);

    String enc = values.writeToString();

    ZFormValues values2 = new ZFormValues();
    values2.readFromString(enc);

    assertEquals(values.getValues(), values2.getValues());
  }


  public void test2() throws Exception
  {
    HashMap<String, String[]> oldValues = new HashMap<String, String[]>();
    oldValues.put("unchanged-not-null", new String[]
    {
      "unchanged"
    });
    oldValues.put("unchanged-null", null);
    oldValues.put("changed-not-null", new String[]
    {
      "changed-not-null-before"
    });
    oldValues.put("changed-null", new String[]
    {
      "changed-null-before"
    });
    oldValues.put("extra-old-value-not-null", new String[]
    {
      "extra-old-value-not-null"
    });
    oldValues.put("extra-old-value-null", null);

    HashMap<String, String[]> newValues = new HashMap<String, String[]>();
    newValues.put("unchanged-not-null", new String[]
    {
      "unchanged"
    });
    newValues.put("unchanged-null", null);
    newValues.put("changed-not-null", new String[]
    {
      "changed-not-null-after"
    });
    newValues.put("changed-null", null);
    newValues.put("extra-new-value-not-null", new String[]
    {
      "extra-new-value-not-null"
    });
    newValues.put("extra-new-value-null", null);

    ZFormValues val1 = new ZFormValues();
    val1.getValues().putAll(oldValues);

    ZFormValues val2 = new ZFormValues();
    val2.getValues().putAll(newValues);

    Set<String> changed = ZFormValues.computeChangedPropertyNames(val1, val2);

    assertFalse(changed.toString(), changed.contains("unchanged-not-null"));
    assertFalse(changed.toString(), changed.contains("unchanged-null"));
    assertTrue(changed.toString(), changed.contains("changed-not-null"));
    assertTrue(changed.toString(), changed.contains("changed-null"));
    assertTrue(changed.toString(), changed.contains("extra-old-value-not-null"));
    assertFalse(changed.toString(), changed.contains("extra-old-value-null"));
    assertTrue(changed.toString(), changed.contains("extra-new-value-not-null"));
    assertFalse(changed.toString(), changed.contains("extra-new-value-null"));

    //    assertEquals(changed.toString(), 3, changed.size());
    //    assertTrue(changed.toString(), changed.contains("key1"));
    //    assertTrue(changed.toString(), changed.contains("key3"));
    //    assertTrue(changed.toString(), changed.contains("key5"));
    //    assertTrue(changed.toString(), changed.contains("extra-new-value"));
    //    assertTrue(changed.toString(), changed.contains("extra-new-null-value"));
  }

}
