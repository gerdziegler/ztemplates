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

import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.impl.ZFormWrapper;

public class FormWithListTest extends TestCase
{
  public void testFormElementMirror() throws Exception
  {
    FormWithList f = new FormWithList();
    ZFormWrapper mirr = new ZFormWrapper(f);
    assertEquals("models-0-2_prop", mirr.getForms().get(0).getProperties().get(0).getName());
    assertEquals(2, mirr.getForms().size());
    assertEquals(0, mirr.getProperties().size());
    assertEquals(0, mirr.getOperations().size());

    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models-0-2_prop", new String[]
    {
        value
    });
    mirr.readFromValues(formValues);
    assertEquals(value, mirr.getForms().get(0).getProperties().get(0).getProperty().getValue());

    // assertEquals("prop1", mirr.getProperties().get(0).getName());
    // assertEquals("op1", mirr.getOperations().get(0).getName());
  }


  public void testCreateListElement() throws Exception
  {
    FormWithList f = new FormWithList();
    ZFormWrapper mirr = new ZFormWrapper(f);
    assertEquals(2, mirr.getForms().size());

    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models-2-3_prop", new String[]
    {
        value
    });
    mirr.readFromValues(formValues);
    assertEquals(3, mirr.getForms().size());
    mirr.readFromValues(formValues);
    assertEquals(3, mirr.getForms().size());
    assertEquals(value, mirr.getForms().get(2).getProperties().get(0).getProperty().getValue());

    // assertEquals("prop1", mirr.getProperties().get(0).getName());
    // assertEquals("op1", mirr.getOperations().get(0).getName());
  }


  public void testCreateListElementNested2() throws Exception
  {
    FormWithList f = new FormWithList();
    ZFormWrapper mirr = new ZFormWrapper(f);
    assertEquals(2, mirr.getForms().size());

    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models-2-3_nested-3-4_prop", new String[]
    {
        value
    });
    mirr.readFromValues(formValues);
    assertEquals(value, mirr.getForms().get(2).getForms().get(3).getProperties().get(0).getProperty().getValue());
    assertEquals(1, f.initModelsCalled);
    assertEquals(1, f.getModels().get(0).initNestedCalled);
    // assertEquals("prop1", mirr.getProperties().get(0).getName());
    // assertEquals("op1", mirr.getOperations().get(0).getName());
  }

  // public void testPredefinedPropertyNames() throws Exception
  // {
  // FormModelWithList f = new FormModelWithList();
  // ZFormMirror.initPropertyNames(f, "");
  // assertEquals("myName", f.getProp().getName());
  // assertEquals("prop1", f.getProp1().getName());
  // }
}
