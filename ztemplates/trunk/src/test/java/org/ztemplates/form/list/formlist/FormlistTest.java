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
package org.ztemplates.form.list.formlist;

import junit.framework.TestCase;

import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.impl.ZFormWrapper;
import org.ztemplates.form.mirr.ZFormMirror;
import org.ztemplates.form.mirr.ZFormMirrorFactory;

public class FormlistTest extends TestCase
{
  private ZFormMirrorFactory formMirrorFactory = new ZFormMirrorFactory();


  public void testAdd() throws Exception
  {
    FormWithList2 f = new FormWithList2();
    assertNull(f.getModels().getName());
    f.getModels().add(new FormWithListNested2());
    assertNull(f.getModels().get(0).getProp().getName(), f.getModels().get(0).getProp().getName());
    ZFormWrapper wrapper = new ZFormWrapper(f);
    assertEquals("models", f.getModels().getName());
    assertEquals("models-0-1_prop", f.getModels().get(0).getProp().getName());
  }


  public void testAdd2() throws Exception
  {
    FormWithList2 f = new FormWithList2();
    ZFormWrapper wrapper = new ZFormWrapper(f);
    f.getModels().add(new FormWithListNested2());
    f.getModels().add(new FormWithListNested2());
    assertEquals("models-0-2_prop", f.getModels().get(0).getProp().getName());
    assertEquals("models-1-2_prop", f.getModels().get(1).getProp().getName());
  }


  public void testAdd3() throws Exception
  {
    FormWithList2 f = new FormWithList2();
    ZFormWrapper wrapper = new ZFormWrapper(f);
    f.getModels().add(new FormWithListNested2());
    assertEquals("models", f.getModels().getName());
    assertEquals("models-0-1_prop", f.getModels().get(0).getProp().getName());
    assertEquals("models-0-1_modelsNested", f.getModels().get(0).getModelsNested().getName());

    f.getModels().add(new FormWithListNested2());
    assertEquals("models", f.getModels().getName());
    assertEquals("models-0-2_prop", f.getModels().get(0).getProp().getName());
    assertEquals("models-0-2_modelsNested", f.getModels().get(0).getModelsNested().getName());
    assertEquals("models-1-2_prop", f.getModels().get(1).getProp().getName());
    assertEquals("models-1-2_modelsNested", f.getModels().get(1).getModelsNested().getName());
  }


  public void testFormElementMirror() throws Exception
  {
    FormWithList2 f = new FormWithList2();
    ZFormWrapper formWrapper = new ZFormWrapper(f);
    f.getModels().add(new FormWithListNested2());
    f.getModels().add(new FormWithListNested2());
    f.getModels().get(0).getModelsNested().add(new FormWithListNested2());
    assertEquals("models-0-2_modelsNested", f.getModels().get(0).getModelsNested().getName());
    //    assertEquals("models-0-2_modelsNested-0-1_prop", f.getModels().get(0).getModelsNested().get(0).getProp().getName());
    ZFormMirror mirr = formMirrorFactory.createFormMirror(f);
    assertEquals(1, mirr.getFormLists().size());
    assertEquals(0, mirr.getProperties().size());
    assertEquals(0, mirr.getOperations().size());
    assertEquals(0, mirr.getFormHolders().size());
    assertEquals(0, mirr.getForms().size());

    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models-0-2_prop", new String[]
    {
        value
    });
    formWrapper.readFromValues(formValues);
    assertEquals(value, f.getModels().get(0).getProp().getValue());

    // assertEquals("prop1", mirr.getProperties().get(0).getName());
    // assertEquals("op1", mirr.getOperations().get(0).getName());
  }


  public void testFormElementMirror2() throws Exception
  {
    FormWithList2 f = new FormWithList2();
    f.getModels().add(new FormWithListNested2());
    ZFormWrapper wrapper = new ZFormWrapper(f);

    assertEquals("models-0-1_prop", f.getModels().get(0).getProp().getName());
    f.getModels().add(new FormWithListNested2());
    assertEquals("models-0-2_prop", f.getModels().get(0).getProp().getName());

    f.getModels().get(1).getModelsNested().add(new FormWithListNested2());
    f.getModels().get(1).getModelsNested().add(new FormWithListNested2());
    assertEquals("models-1-2_modelsNested-1-2_prop", f.getModels().get(1).getModelsNested().get(1).getProp().getName());

    wrapper = new ZFormWrapper(f);
    ZFormMirror mirr = formMirrorFactory.createFormMirror(f);
    assertEquals(1, mirr.getFormLists().size());
    assertEquals(0, mirr.getForms().size());
    assertEquals(0, mirr.getFormHolders().size());
    assertEquals(0, mirr.getProperties().size());
    assertEquals(0, mirr.getOperations().size());

    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models-0-2_prop", new String[]
    {
        value
    });
    wrapper.readFromValues(formValues);
    assertEquals(value, f.getModels().get(0).getProp().getValue());

    // assertEquals("prop1", mirr.getProperties().get(0).getName());
    // assertEquals("op1", mirr.getOperations().get(0).getName());
  }


  public void testFormElementMirror_1() throws Exception
  {
    FormWithList2 f = new FormWithList2();
    f.getModels().add(new FormWithListNested2());
    ZFormWrapper wrapper = new ZFormWrapper(f);
    assertEquals("models", f.getModels().getName());
    assertEquals("models-0-1_modelsNested", f.getModels().get(0).getModelsNested().getName());
  }


  public void testFormElementMirror3() throws Exception
  {
    FormWithList2 f = new FormWithList2();
    f.getModels().add(new FormWithListNested2());
    ZFormWrapper wrapper = new ZFormWrapper(f);
    assertEquals("models", f.getModels().getName());
    assertEquals("models-0-1_modelsNested", f.getModels().get(0).getModelsNested().getName());
    f.getModels().get(0).getModelsNested().add(new FormWithListNested2());

    ZFormMirror mirr = formMirrorFactory.createFormMirror(f);
    assertEquals(1, mirr.getFormLists().size());
    assertEquals(0, mirr.getFormHolders().size());
    assertEquals(0, mirr.getForms().size());
    assertEquals(0, mirr.getProperties().size());
    assertEquals(0, mirr.getOperations().size());

    wrapper = new ZFormWrapper(f);
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models-1-2_prop", new String[]
    {
        value
    });
    wrapper.readFromValues(formValues);
    assertEquals(value, f.getModels().get(1).getProp().getValue());
    //    assertEquals(value, mirr.getFormLists().get(0).getForms().get(1).getProperties().get(0).getValue());

    // assertEquals("prop1", mirr.getProperties().get(0).getName());
    // assertEquals("op1", mirr.getOperations().get(0).getName());
  }


  public void testCreateListElement() throws Exception
  {
    FormWithList2 f = new FormWithList2();
    ZFormWrapper wrapper = new ZFormWrapper(f);

    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models-2-3_prop", new String[]
    {
        value
    });
    wrapper.readFromValues(formValues);

    ZFormMirror mirr = formMirrorFactory.createFormMirror(f);
    assertEquals(3, f.getModels().size());
    wrapper.readFromValues(formValues);
    assertEquals(3, f.getModels().size());
    assertEquals(value, f.getModels().get(2).getProp().getValue());

    // assertEquals("prop1", mirr.getProperties().get(0).getName());
    // assertEquals("op1", mirr.getOperations().get(0).getName());
  }


  public void testCreateListElementNested2() throws Exception
  {
    FormWithList2 f = new FormWithList2();
    ZFormWrapper wrapper = new ZFormWrapper(f);
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models-2-3_modelsNested-3-4_prop", new String[]
    {
        value
    });
    wrapper.readFromValues(formValues);
    //    ZFormMirror mirr = formMirrorFactory.createFormMirror(f);
    assertEquals(value, f.getModels().get(2).getModelsNested().get(3).getProp().getValue());

    //    assertEquals(value, mirr.getFormHolders().get(2).getFormHolders().get(3).getProperties().get(0).getValue());

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

  public void testSetValueWithName() throws Exception
  {
    FormWithList f = new FormWithList();
    f.getModels().setName("kkk");
    ZFormWrapper wrapper = new ZFormWrapper(f);
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("kkk-2-3_prop", new String[]
    {
        value
    });
    wrapper.readFromValues(formValues);
    assertEquals(3, f.getModels().size());
    assertEquals(value, f.getModels().get(2).getProp().getValue());
  }


  public void testSetNestedValueWithName() throws Exception
  {
    FormWithList f = new FormWithList();
    f.getModels().setName("kkk");
    ZFormWrapper wrapper = new ZFormWrapper(f);
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("kkk-2-3_myModelName-3-4_prop", new String[]
    {
        value
    });
    wrapper.readFromValues(formValues);
    assertEquals(3, f.getModels().size());
    assertEquals(4, f.getModels().get(2).getModelsNested().size());
    assertEquals(value, f.getModels().get(2).getModelsNested().get(3).getProp().getValue());
  }
}
