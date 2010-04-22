/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de) Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. 22.09.2007 @author
 * www.gerdziegler.de
 */
package org.ztemplates.test.property;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.property.ZIntListProperty;

public class ListPropertyTest extends TestCase
{
  static Logger log = Logger.getLogger(ListPropertyTest.class);


  protected void setUp() throws Exception
  {
    super.setUp();
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
  }


//  public void testReadNullValue() throws Exception
//  {
//    ZIntListProperty p = new ZIntListProperty();
//    assertTrue(p.getValue().isEmpty());
//  }
//
//
//  public void testWriteNullValue() throws Exception
//  {
//    ZIntListProperty p = new ZIntListProperty();
//    p.setValue(null);
//    assertTrue(p.getValue().isEmpty());
//  }


  public void testIntList() throws Exception
  {
    ZIntListProperty p = new ZIntListProperty("-");
    List<Integer> val = new ArrayList<Integer>();
    val.add(Integer.valueOf(1));
    val.add(Integer.valueOf(2));
    val.add(Integer.valueOf(3));
    p.setValue(val);
    assertEquals("1-2-3", p.getStringValue());

    p.setStringValues(new String[]{p.getStringValue()});
    List<Integer> val2 = p.getValue();
    assertEquals(val, val2);
  }


  public void testIntList2() throws Exception
  {
    ZIntListProperty p = new ZIntListProperty("-");
    p.setStringValues(new String[]{"1-2-"});
    List<Integer> val = p.getValue();
    assertEquals(2, val.size());
  }
}
