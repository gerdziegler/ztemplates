package org.ztemplates.form.form;

import junit.framework.TestCase;

import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.impl.ZFormWrapper;

public class FormTest extends TestCase
{
  private final String PROP_NAME = "form_prop";


  public void testSetValue() throws Exception
  {
    Form f = new Form();
    //    f.getForm().setName("kkk");
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put(PROP_NAME, new String[]
      {
          value
      });

    ZFormWrapper wrapper = new ZFormWrapper(f);
    assertEquals("form", f.getForm().getName());
    wrapper.readFromValues(formValues);
    assertEquals(value, f.getForm().getForm().getProp().getValue());
  }


  public void testSetValue2() throws Exception
  {
    FormWithFinalForm f = new FormWithFinalForm();
    //    f.getForm().setName("kkk");
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put(PROP_NAME, new String[]
      {
          value
      });

    ZFormWrapper wrapper = new ZFormWrapper(f);
    wrapper.readFromValues(formValues);
    assertEquals(value, f.getForm().getProp().getValue());
  }


  public void testSetValue3() throws Exception
  {
    FormWithNamedForm f = new FormWithNamedForm();
    //    f.getForm().setName("kkk");
    String value = "katze";
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().put(PROP_NAME, new String[]
      {
          value
      });

    ZFormWrapper wrapper = new ZFormWrapper(f);
    wrapper.readFromValues(formValues);
    assertEquals(value, f.getForm().getForm().getProp().getValue());
  }
}
