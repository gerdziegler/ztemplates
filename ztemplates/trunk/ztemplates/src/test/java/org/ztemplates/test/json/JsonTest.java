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
package org.ztemplates.test.json;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.ztemplates.json.ZJsonUtil;
import org.ztemplates.test.mock.ZMock;

public class JsonTest extends TestCase
{
  static Logger log = Logger.getLogger(JsonTest.class);


  protected void setUp() throws Exception
  {
    super.setUp();
    ZMock.setUp();
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
  }


  public void test1() throws Exception
  {
    FormModel1 dataWrite = new FormModel1();
    dataWrite.getStringProp().setValue("stringValue");
    dataWrite.getStringProp().setDescription("stringDescription");
    dataWrite.getFormData2().getStringProp().setValue("formData2-stringValue");
    dataWrite.getFormData2().getStringProp().setDescription("formData2-stringDescription");
    dataWrite.getCollection().add("collection-string1");
    dataWrite.getCollection().add("collection-string2");

    FormModel2 fd = new FormModel2();
    fd.getStringProp().setValue("fd2-sv1");
    dataWrite.getCollection2().add(fd);
    FormModel2 fd2 = new FormModel2();
    fd2.getStringProp().setValue("fd2-sv2");
    dataWrite.getCollection2().add(fd2);

    //    ZTemplates.getFormService().process(dataWrite);
    JSONObject json = ZJsonUtil.computeJSON(dataWrite);

    String ret = json.toString(4);
    log.debug(ret);
    assertTrue(ret, ret.indexOf(dataWrite.getStringProp().getStringValue()) >= 0);
    assertTrue(ret, ret.indexOf(dataWrite.getStringProp().getDescription()) >= 0);
    assertTrue(ret, ret.indexOf(dataWrite.getFormData2().getStringProp().getStringValue()) >= 0);
    assertTrue(ret, ret.indexOf(dataWrite.getFormData2().getStringProp().getDescription()) >= 0);

    for (String s : dataWrite.getCollection())
    {
      assertTrue(ret, ret.indexOf(s) >= 0);
    }
    for (FormModel2 s : dataWrite.getCollection2())
    {
      assertTrue(ret, ret.indexOf(s.getStringProp().getStringValue()) >= 0);
    }
  }
}
