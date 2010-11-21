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
 * 22.09.2007
 * @author www.gerdziegler.de
 */
package org.ztemplates.test.property;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.property.ZIntProperty;

public class PropertyTest extends TestCase
{
  static Logger log = Logger.getLogger(PropertyTest.class);


  protected void setUp() throws Exception
  {
    super.setUp();
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
  }


  public void testReadNullValue() throws Exception
  {
    ZIntProperty p = new ZIntProperty();
    assertNull(p.getValue());
  }


  public void testWriteNullValue() throws Exception
  {
    ZIntProperty p = new ZIntProperty();
    p.setValue(null);
    assertNull(p.getValue());
  }


  public void testValues() throws Exception
  {
    ZIntProperty p = new ZIntProperty();
    p.setValue(1);
    List<Integer> values = p.getValues();
    assertEquals(1, values.size());
    assertEquals(1, values.get(0).intValue());
  }

  // public void testFireAjaxListerer() throws Exception
  // {
  // final List<String> test = new ArrayList<String>();
  // ZProperty<String> p = new ZStringProperty();
  // p.setStringValue("1");
  // p.setEnableChangeListeners(true);
  // p.getChangeListeners().add(new ZIChangeListener()
  // {
  // public void onChange()
  // {
  // test.add("1");
  // }
  // });
  // p.setStringValue("2");
  // assertFalse(test.isEmpty());
  // }
  //
  //
  // public void testFireAjaxListerer2() throws Exception
  // {
  // final List<String> test = new ArrayList<String>();
  // ZProperty<String> p = new ZStringProperty();
  // p.setStringValue("1");
  // p.setEnableChangeListeners(true);
  // p.getChangeListeners().add(new ZIChangeListener()
  // {
  // public void onChange()
  // {
  // test.add("1");
  // }
  // });
  // p.setStringValue("1");
  // assertTrue(test.isEmpty());
  // }
}
