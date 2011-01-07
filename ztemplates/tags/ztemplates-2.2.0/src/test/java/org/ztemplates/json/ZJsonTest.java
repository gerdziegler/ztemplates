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
package org.ztemplates.json;

import junit.framework.TestCase;

import org.json.JSONObject;
import org.ztemplates.test.mock.ZMock;

public class ZJsonTest extends TestCase
{
  protected void setUp() throws Exception
  {
    super.setUp();
    ZMock.setUp();
  }

  
  public void test_Json() throws Exception
  {
    TestForm1 tf = new TestForm1();
    JSONObject json = ZJsonUtil.computeJSON(tf);
    String s = json.toString(4);
    assertTrue(s, s.indexOf("\"prop1\"")>=0);
    assertTrue(s, s.indexOf("\"op1\"")>=0);
    assertTrue(s, s.indexOf("object2Prop1")>=0);
    assertTrue(s, s.indexOf("object2Prop2")<0);
    assertTrue(s, s.indexOf("object1Prop1")<0);
    assertTrue(s, s.indexOf("object1Prop2")<0);
    assertTrue(s, s.indexOf("text")<0);
    assertTrue(s, s.indexOf("katze")<0);   
  }
}
