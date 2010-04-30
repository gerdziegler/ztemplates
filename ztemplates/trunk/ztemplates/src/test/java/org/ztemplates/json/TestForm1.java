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

import org.ztemplates.form.ZIForm;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZStringProperty;

public class TestForm1 implements ZIForm
{
  private final ZStringProperty prop1 = new ZStringProperty();

  private final ZOperation op1 = new ZOperation("opValue");

  private final TestForm2 form = new TestForm2();

  private final TestObject1 object1 = new TestObject1();

  private final TestObject2 object2 = new TestObject2();


  public ZStringProperty getProp1()
  {
    return prop1;
  }


  public ZOperation getOp1()
  {
    return op1;
  }


  public TestForm2 getForm()
  {
    return form;
  }


  public TestObject1 getObject1()
  {
    return object1;
  }


  @ZExposeJson
  public TestObject2 getObject2()
  {
    return object2;
  }


  public String getText()
  {
    return "katze";
  }

}
