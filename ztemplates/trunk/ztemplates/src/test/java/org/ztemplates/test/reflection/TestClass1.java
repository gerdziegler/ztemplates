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
package org.ztemplates.test.reflection;

import java.util.ArrayList;
import java.util.List;

public class TestClass1
{
  public enum TestEnum {
    ENUM1, ENUM2
  };

  private TestEnum enumVal = TestEnum.ENUM1;

  private List<TestClass2> list = new ArrayList<TestClass2>();

  private TestClass2[] array = new TestClass2[]
  {
      new TestClass2(),
      new TestClass2(),
      new TestClass2()
  };


  public List<TestClass2> getList()
  {
    return list;
  }


  public TestClass2[] getArray()
  {
    return array;
  }


  public TestEnum getEnumVal()
  {
    return enumVal;
  }


  public void setEnumVal(TestEnum enumVal)
  {
    this.enumVal = enumVal;
  }
}
