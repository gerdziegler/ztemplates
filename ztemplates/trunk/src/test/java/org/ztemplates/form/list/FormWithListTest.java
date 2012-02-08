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
package org.ztemplates.form.list;

import junit.framework.TestCase;

import org.ztemplates.form.impl.ZFormWrapper;

public class FormWithListTest extends TestCase
{
  public void testFormElementMirror() throws Exception
  {
    FormWithList f = new FormWithList();
    // ZDynamicFormModel.initPropertyNames(f, "");
    ZFormWrapper mirr = new ZFormWrapper(f);
    assertEquals(2, mirr.getForms().size());
    assertEquals(0, mirr.getProperties().size());
    assertEquals(0, mirr.getOperations().size());

    // assertEquals("prop1", mirr.getProperties().get(0).getName());
    // assertEquals("op1", mirr.getOperations().get(0).getName());
    // assertEquals("topSection.op1",
    // mirr.getFormModels().get(0).getOperations().get(0).getName());
  }

  // public void testPredefinedPropertyNames() throws Exception
  // {
  // FormModelWithList f = new FormModelWithList();
  // ZFormMirror.initPropertyNames(f, "");
  // assertEquals("myName", f.getProp().getName());
  // assertEquals("prop1", f.getProp1().getName());
  // }
}
