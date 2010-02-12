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

public class FormTestOpCallback extends TestCase
{
  static Logger log = Logger.getLogger(FormTestOpCallback.class);

  ZIUrlHandler up;


  protected void setUp() throws Exception
  {
    super.setUp();
    ZMock.setUp();
    up = ZTestUrlHandlerFactory.create(FormTestOpCallback.class.getPackage().getName(), ZTestUrlHandlerFactory.defaultSecurityService);
  }


  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testOperationHandler() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("op", new String[]
    {
      "called",
    });

    FormActionCallback obj = (FormActionCallback) up.process("/act-handler", param);
    assertTrue(obj.isCallbackCalled());
    assertFalse(obj.isAfterCalled());
  }
}
