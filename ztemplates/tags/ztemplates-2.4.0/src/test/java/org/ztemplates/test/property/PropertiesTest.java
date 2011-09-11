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
package org.ztemplates.test.property;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.actions.util.impl.ZReflectionUtil;
import org.ztemplates.test.ZTestUrlHandlerFactory;
import org.ztemplates.test.mock.ZMock;

public class PropertiesTest extends TestCase
{
  static Logger log = Logger.getLogger(PropertiesTest.class);

  ZIUrlHandler up;


  protected void setUp() throws Exception
  {
    super.setUp();
    ZMock.setUp();
    up = ZTestUrlHandlerFactory.create(PropertiesTest.class.getPackage().getName(),
        ZTestUrlHandlerFactory.defaultSecurityService);
  }


  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testGetter() throws Exception
  {
    Method m = ZReflectionUtil.getGetter(HandlerNotAnnotated.class, "var1");
    assertNotNull(m);
  }


  public void testSetter() throws Exception
  {
    Method m = ZReflectionUtil.getSetter(HandlerNotAnnotated.class, "var1");
    assertNotNull(m);
  }


  public void testProperty() throws Exception
  {
    HandlerNotAnnotated obj = (HandlerNotAnnotated) up.process("/HandlerNotAnnotated/katzeklo");
    assertNotNull(obj);
    assertEquals("katzeklo", obj.getVar1().getValue());
  }
}
