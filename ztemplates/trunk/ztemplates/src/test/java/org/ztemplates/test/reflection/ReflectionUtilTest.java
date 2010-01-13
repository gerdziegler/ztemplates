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
package org.ztemplates.test.reflection;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.util.ZReflectionUtil;

public class ReflectionUtilTest extends TestCase
{
  static Logger log = Logger.getLogger(ReflectionUtilTest.class);


  protected void setUp() throws Exception
  {
    super.setUp();
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
  }


  public void test1() throws Exception
  {
    TestClass1 tc = new TestClass1();
    TestClass2 tc2 = new TestClass2();
    tc2.setValue("val1");
    tc.getList().add(tc2);
    String s = (String) ZReflectionUtil.getObjectByBeanPath(tc, "list[0].value");
    assertEquals("val1", s);
  }

  public void test2() throws Exception
  {
    TestClass1 tc = new TestClass1();
    String[] vals = ZReflectionUtil.callParameterGetter(tc, "enumVal");
    assertEquals(TestClass1.TestEnum.ENUM1.name(), vals[0]);
    ZReflectionUtil.callParameterSetter(tc, "enumVal", new String[]{TestClass1.TestEnum.ENUM2.name()});    
    assertEquals(TestClass1.TestEnum.ENUM2, tc.getEnumVal());   

    String[] vals2 = ZReflectionUtil.callParameterGetter(tc, "enumVal");
    assertEquals(TestClass1.TestEnum.ENUM2.name(), vals2[0]);
  }

  public void test3() throws Exception
  {
    TestClass1 tc = new TestClass1();
    String vals = ZReflectionUtil.callVariableGetter(tc, "enumVal");
    assertEquals(TestClass1.TestEnum.ENUM1.name(), vals);
    ZReflectionUtil.callVariableSetter(tc, "enumVal", TestClass1.TestEnum.ENUM2.name());    
    assertEquals(TestClass1.TestEnum.ENUM2, tc.getEnumVal());   

    String vals2 = ZReflectionUtil.callVariableGetter(tc, "enumVal");
    assertEquals(TestClass1.TestEnum.ENUM2.name(), vals2);
  }
}
