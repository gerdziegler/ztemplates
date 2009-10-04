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
package org.ztemplates.test.actions.urlhandler.form;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;
import org.ztemplates.test.mock.ZMock;
import org.ztemplates.test.mock.ZMockServiceRepository;
import org.ztemplates.test.mock.ZMockServletService;
import org.ztemplates.web.ZTemplates;

public class FormTest extends TestCase
{
  static Logger log = Logger.getLogger(FormTest.class);

  ZIUrlHandler up;


  protected void setUp() throws Exception
  {
    super.setUp();
    ZMock.setUp();
    up = ZTestUrlHandlerFactory.create(FormTest.class.getPackage().getName(),
        ZTestUrlHandlerFactory.defaultSecurityService);
  }


  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testNames() throws Exception
  {
    FormModel form = new FormModel();
    ZMockServiceRepository repo = ZMock.getMock();
    repo.setServletService(new ZMockServletService());

    ZTemplates.getFormService().process(form);

    assertEquals("op1", form.getOp1().getName());
    assertEquals("prop1", form.getProp1().getName());
    assertEquals("topSection.field1", form.getTopSection().getField1().getName());
    assertEquals("topSection.field2", form.getTopSection().getField2().getName());
    assertEquals("topSection.op1", form.getTopSection().getOp1().getName());
  }


  public void testValidationParameterWithDot() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    try
    {
      FormAction obj = (FormAction) up.process("/act", param);
      fail();
    }
    catch (Exception e)
    {

    }
    // assertNotNull(obj.getForm().getTopSection().getOp1().getState());
    // assertNotNull(obj.getForm().getTopSection().getError());
    // assertNotNull(obj.getOp1().getState());
    // assertNotNull(obj.getProp1().getState());
    // assertEquals("katze",
    // obj.getForm().getTopSection().getOp1().getState().getText());
    // assertEquals("maus", obj.getForm().getTopSection().getError().getText());
  }


  public void testParamPropParameterWithDot() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("form.topSection.field1", new String[]
    {
      "val1",
    });
    param.put("katzeko", new String[]
    {
      "val4",
    });

    try
    {
      FormAction obj = (FormAction) up.process("/act", param);
      // assertEquals("val1",
      // obj.getForm().getTopSection().getField1().getStringValue());
      fail();
    }
    catch (Exception e)
    {

    }
  }


//  public void testParamPropForm() throws Exception
//  {
//    Map<String, String[]> param = new HashMap<String, String[]>();
//    param.put("topSection.field1", new String[]
//    {
//      "val1",
//    });
//    param.put("topSection.field2", new String[]
//    {
//      "val2",
//    });
//    param.put("predefined", new String[]
//    {
//      "predefinedVal",
//    });
//
//    ZMockServiceRepository repo = ZMock.getMock();
//    ZMockServletService ss = new ZMockServletService();
//    ss.parameterMap = param;
//    repo.setServletService(ss);
//
//    FormAction2 obj = (FormAction2) up.process("/act2", param);
//    ZFormProcessor<FormController> wf = new ZFormProcessor<FormController>(obj.getForm());
//    wf.assign();
//
//    assertEquals("topSection.field1", "val1", obj.getForm().getForm().getTopSection().getField1().getStringValue());
//    assertEquals("topSection.field2", "val2", obj.getForm().getForm().getTopSection().getField2().getStringValue());
//    assertEquals("predefined", "predefinedVal", obj.getForm().getForm().getPredefined().getStringValue());
//  }
}
