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
package org.ztemplates.test.actions.urlhandler.repository.nested;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;

public class NestedHandlerTest extends TestCase
{
  static Logger log = Logger.getLogger(NestedHandlerTest.class);


  protected void setUp() throws Exception
  {
    super.setUp();
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
  }


  // public void test1() throws Exception
  // {
  // ZIUrlHandler up =
  // ZUrlHandlerFactory.create(NestedHandlerTest.class.getPackage().getName());
  // NestedHandler obj = (NestedHandler) up.process("nested/katzeklo");
  // assertNotNull(obj);
  // assertEquals("katzeklo", obj.getValue());
  // assertEquals(1, obj.getBeforeCalled());
  // assertEquals(1, obj.getAfterCalled());
  // }

  public void test2() throws Exception
  {
    ZIUrlHandler up = ZTestUrlHandlerFactory.create(NestedHandlerTest.class.getPackage().getName(),
        ZTestUrlHandlerFactory.defaultSecurityService);
    Handler obj = (Handler) up.process("/audiobooks/nested/katzeklo");
    assertNotNull(obj);
    assertEquals("katzeklo", obj.getNested().getValue());
    assertEquals(1, obj.getBeforeCalled());
    assertEquals(1, obj.getAfterCalled());
    assertEquals(1, obj.getBeforeNestedCalled());
    assertEquals(1, obj.getAfterNestedCalled());

    obj.getNested().setValue("froh");
    String s = up.createUrl(obj);
    log.debug(s);

    Handler obj2 = (Handler) up.process(s);
    assertEquals("froh", obj2.getNested().getValue());
    assertEquals(1, obj2.getBeforeCalled());
    assertEquals(1, obj2.getAfterCalled());
    assertEquals(1, obj2.getNested().getBeforeCalled());
    assertEquals(1, obj2.getNested().getAfterCalled());
    assertEquals(1, obj2.getNested().getBeforeValueCalled());
    assertEquals(1, obj2.getNested().getAfterValueCalled());
  }
}
