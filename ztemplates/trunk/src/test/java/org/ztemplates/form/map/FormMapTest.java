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
package org.ztemplates.form.map;

import junit.framework.TestCase;

import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.impl.ZFormWrapper;
import org.ztemplates.form.mirr.ZFormMirror;
import org.ztemplates.form.mirr.ZFormMirrorFactory;

public class FormMapTest extends TestCase
{
  private ZFormMirrorFactory formMirrorFactory = new ZFormMirrorFactory();


  //  public void test_createKey() throws Exception
  //  {
  //    FormWithMap f = new FormWithMap();
  //    String key = f.getModels().createKey("katze", 0);
  //    for (int i = 0; i < 112; i++)
  //    {
  //      System.out.println(String.format("%1$03d", i));
  //    }
  //    assertEquals("r00katze", key);
  //  }

  public void testAdd() throws Exception
  {
    FormWithMap f = new FormWithMap();
    assertNull(f.getModels().getName());
    f.getModels().add(new FormWithMapNested("test1"));
    assertNull(f.getModels().getFormWithId("test1").getProp().getName(), f.getModels().getFormWithId("test1").getProp().getName());
    ZFormWrapper wrapper = new ZFormWrapper(f);
    assertEquals("models", f.getModels().getName());
    assertEquals("models_test1_prop", f.getModels().getFormWithId("test1").getProp().getName());
  }


  public void testAdd2() throws Exception
  {
    FormWithMap f = new FormWithMap();
    ZFormWrapper wrapper = new ZFormWrapper(f);
    f.getModels().add(new FormWithMapNested("test1"));
    f.getModels().add(new FormWithMapNested("test2"));
    assertEquals("models_test1_prop", f.getModels().getFormWithId("test1").getProp().getName());
    assertEquals("models_test2_prop", f.getModels().getFormWithId("test2").getProp().getName());
  }


  public void testAdd3() throws Exception
  {
    FormWithMap f = new FormWithMap();
    ZFormWrapper wrapper = new ZFormWrapper(f);
    f.getModels().add(new FormWithMapNested("test1"));
    assertEquals("models", f.getModels().getName());
    assertEquals("models_test1_prop", f.getModels().getFormWithId("test1").getProp().getName());
    assertEquals("models_test1_modelsNested", f.getModels().getFormWithId("test1").getModelsNested().getName());

    f.getModels().add(new FormWithMapNested("test2"));
    assertEquals("models_test1_prop", f.getModels().getFormWithId("test1").getProp().getName());
    assertEquals("models_test1_modelsNested", f.getModels().getFormWithId("test1").getModelsNested().getName());
    assertEquals("models_test2_prop", f.getModels().getFormWithId("test2").getProp().getName());
    assertEquals("models_test2_modelsNested", f.getModels().getFormWithId("test2").getModelsNested().getName());
  }


  public void testAssignValueToProperty() throws Exception
  {
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models_test1_prop", new String[]
    {
        value
    });
    FormWithMap f = new FormWithMap();
    f.getModels().add(new FormWithMapNested("test1"));
    ZFormWrapper formWrapper = new ZFormWrapper(f);
    formWrapper.readFromValues(formValues);
    assertEquals(value, f.getModels().getFormWithId("test1").getProp().getValue());
  }


  public void testAssignValueToPropertyFactoryCall() throws Exception
  {
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models_test1_prop", new String[]
    {
        value
    });
    FormWithMap f = new FormWithMap();
    ZFormWrapper formWrapper = new ZFormWrapper(f);
    formWrapper.readFromValues(formValues);
    assertEquals(value, f.getModels().getFormWithId("test1").getProp().getValue());
  }


  public void testAssignMultipleValueToPropertyFactoryCall() throws Exception
  {
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models_test1_prop", new String[]
    {
        value
    });
    formValues.getValues().put("models_test2_prop", new String[]
    {
        value + "1"
    });
    FormWithMap f = new FormWithMap();
    ZFormWrapper formWrapper = new ZFormWrapper(f);
    formWrapper.readFromValues(formValues);
    assertEquals(value, f.getModels().getFormWithId("test1").getProp().getValue());
    assertEquals(value + "1", f.getModels().getFormWithId("test2").getProp().getValue());
  }


  public void testAssignValueToExistingNestedProperty() throws Exception
  {
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models_test1_modelsNested_nest1_prop", new String[]
    {
        value
    });
    FormWithMap f = new FormWithMap();
    FormWithMapNested nested = new FormWithMapNested("test1");
    f.getModels().add(nested);
    nested.getModelsNested().add(new FormWithMapNested("test1"));
    ZFormWrapper formWrapper = new ZFormWrapper(f);
    formWrapper.readFromValues(formValues);
    assertEquals(value, f.getModels().getFormWithId("test1").getModelsNested().getFormWithId("nest1").getProp().getValue());
  }


  public void testAssignValueToNotExistingNestedProperty() throws Exception
  {
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("models_test1_modelsNested_nest1_prop", new String[]
    {
        value
    });
    FormWithMap f = new FormWithMap();
    ZFormWrapper formWrapper = new ZFormWrapper(f);
    formWrapper.readFromValues(formValues);
    assertEquals(value, f.getModels().getFormWithId("test1").getModelsNested().getFormWithId("nest1").getProp().getValue());
  }


  public void testAssignNames() throws Exception
  {
    FormWithMap f = new FormWithMap();
    f.getModels().setName("katze");
    FormWithMapNested nested = new FormWithMapNested("matze");
    f.getModels().add(nested);
    nested.getModelsNested().setName("latze");
    FormWithMapNested nested2 = new FormWithMapNested("zatze");
    nested.getModelsNested().add(nested2);
    ZFormWrapper wrapper = new ZFormWrapper(f);
    assertEquals("katze", f.getModels().getName());
    assertEquals("katze_matze_latze_zatze_prop", f.getModels().getFormWithId("matze").getModelsNested().getFormWithId("zatze").getProp().getName());
    f.getModels().setName("batze");
    String nestedName = "batze_matze_latze_zatze_prop";
    assertEquals(nestedName, f.getModels().getFormWithId("matze").getModelsNested().getFormWithId("zatze").getProp().getName());
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put(nestedName, new String[]
    {
        value
    });
    wrapper.readFromValues(formValues);
    assertEquals(value, f.getModels().getFormWithId("matze").getModelsNested().getFormWithId("zatze").getProp().getValue());
  }


  public void testFormElementMirror() throws Exception
  {
    FormWithMap f = new FormWithMap();
    ZFormMirror mirr = formMirrorFactory.createFormMirror(f);
    assertEquals(0, mirr.getProperties().size());
    assertEquals(0, mirr.getOperations().size());
    assertEquals(0, mirr.getFormHolders().size());
    assertEquals(0, mirr.getForms().size());
    assertEquals(1, mirr.getFormMaps().size());
  }


  public void testSetValueWithName() throws Exception
  {
    FormWithMap f = new FormWithMap();
    f.getModels().setName("kkk");
    ZFormWrapper wrapper = new ZFormWrapper(f);
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put("kkk_batze_prop", new String[]
    {
        value
    });
    wrapper.readFromValues(formValues);
    assertEquals(value, f.getModels().getFormWithId("batze").getProp().getValue());
  }

}
