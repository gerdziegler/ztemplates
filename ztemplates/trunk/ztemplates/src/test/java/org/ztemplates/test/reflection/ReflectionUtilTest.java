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

import java.lang.reflect.Method;
import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.util.ZReflectionUtil;
import org.ztemplates.form.ZDynamicFormModel;

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
    String s = (String) getObjectByBeanPath(tc, "list[0].value");
    assertEquals("val1", s);
  }
  
  //*
  //Saved here from ZReflectionUtil
  
  @Deprecated
  public static Object getObjectByBeanPath(Object obj, String path) throws Exception
  {
    String crtPath = path;
    Object crtObj = obj;
    int idx = crtPath.indexOf('.');
    while (idx > 0)
    {
      String propName = crtPath.substring(0, idx);
      int collectionIndex = -1;
      int openParanth = propName.indexOf('[');
      if (openParanth >= 0)
      {
        collectionIndex = Integer.parseInt(propName.substring(openParanth + 1, propName.length() - 1));
        propName = propName.substring(0, openParanth);
      }

      String getterName = ZReflectionUtil.computePrefixName("get", propName);
      Method m = crtObj.getClass().getMethod(getterName);
      if (m == null)
      {
        throw new Exception("getter not found: '" + getterName + "' in " + crtObj.getClass().getName() + " in path '" + path + "' starting from "
            + obj.getClass().getName());
      }
      crtObj = ZReflectionUtil.invoke(m, crtObj);
      if (collectionIndex >= 0)
      {
        if (List.class.isAssignableFrom(m.getReturnType()))
        {
          List l = (List) crtObj;
          crtObj = l.get(collectionIndex);
        }
        else if (m.getReturnType().isArray())
        {
          Object[] arr = (Object[]) crtObj;
          crtObj = arr[collectionIndex];
        }
        else
        {
          throw new Exception("unsupported collection: " + m.getReturnType().getName() + " --- only List and arrays allowed.");
        }
      }
      crtPath = crtPath.substring(idx + 1);
      idx = crtPath.indexOf('.');
    }

    String propName = crtPath;
    String getterName = ZReflectionUtil.computePrefixName("get", propName);
    Method m = crtObj.getClass().getMethod(getterName);
    if (m == null)
    {
      throw new Exception("getter not found: '" + getterName + "' in " + crtObj.getClass().getName() + " in path '" + path + "' starting from "
          + obj.getClass().getName());
    }
    crtObj = ZReflectionUtil.invoke(m, crtObj);
    return crtObj;
  }


  public void test2() throws Exception
  {
    TestClass1 tc = new TestClass1();
    String[] vals = ZReflectionUtil.callParameterGetter(tc, "enumVal");
    assertEquals(TestClass1.TestEnum.ENUM1.name(), vals[0]);
    ZReflectionUtil.callParameterSetter(tc, "enumVal", new String[]
    {
      TestClass1.TestEnum.ENUM2.name()
    });
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
