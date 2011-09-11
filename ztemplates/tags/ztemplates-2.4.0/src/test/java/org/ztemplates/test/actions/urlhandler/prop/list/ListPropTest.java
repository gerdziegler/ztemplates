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
package org.ztemplates.test.actions.urlhandler.prop.list;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;
import org.ztemplates.test.mock.ZMock;

public class ListPropTest extends TestCase
{
  static Logger log = Logger.getLogger(ListPropTest.class);

  ZIUrlHandler up;


  protected void setUp() throws Exception
  {
    super.setUp();
    ZMock.setUp();
    up = ZTestUrlHandlerFactory.create(ListPropTest.class.getPackage().getName(),
        ZTestUrlHandlerFactory.defaultSecurityService);
  }


  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testVarProp() throws Exception
  {
    ListHandler obj = (ListHandler) up.process("/listproptest/1-2-3");
    List<Integer> val = obj.getVarProp().getValue();
    assertEquals(3, val.size());
    assertEquals(1, val.get(0).intValue());
    assertEquals(2, val.get(1).intValue());
    assertEquals(3, val.get(2).intValue());
  }


  public void testParamProp() throws Exception
  {
    ListHandler obj = (ListHandler) up.process("/listproptest/1-2-3?paramProp=katze_klo_123");
    List<String> val = obj.getParamProp().getValue();
    assertEquals(3, val.size());
    assertEquals("katze", val.get(0));
    assertEquals("klo", val.get(1));
    assertEquals("123", val.get(2));
  }
}
