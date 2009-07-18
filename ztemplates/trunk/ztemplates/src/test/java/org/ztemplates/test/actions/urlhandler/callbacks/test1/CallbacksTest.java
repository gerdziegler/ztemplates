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
package org.ztemplates.test.actions.urlhandler.callbacks.test1;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;

public class CallbacksTest extends TestCase
{
  static Logger log = Logger.getLogger(CallbacksTest.class);

  ZIUrlHandler up;


  protected void setUp() throws Exception
  {
    super.setUp();
    up = ZTestUrlHandlerFactory.create(CallbacksTest.class.getPackage().getName(),
        ZTestUrlHandlerFactory.defaultSecurityService);
  }


  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testBefore() throws Exception
  {
    HandlerAnnotated obj = (HandlerAnnotated) up.process("/annotated/katzeklo");
    assertNotNull(obj);
    assertTrue(obj.isBeforeCalled());
  }


  public void testAfter() throws Exception
  {
    HandlerAnnotated obj = (HandlerAnnotated) up.process("/annotated/katzeklo");
    assertNotNull(obj);
    assertTrue(obj.isAfterCalled());
  }


  public void testAfterVar1() throws Exception
  {
    HandlerAnnotated obj = (HandlerAnnotated) up.process("/annotated/katzeklo");
    assertNotNull(obj);
    assertTrue(obj.isAfterVar1Called());
  }


  public void testBeforeVar1() throws Exception
  {
    HandlerAnnotated obj = (HandlerAnnotated) up.process("/annotated/katzeklo");
    assertNotNull(obj);
    assertTrue(obj.isBeforeVar1Called());
  }


  public void testBeforeNotAnnotated() throws Exception
  {
    HandlerNotAnnotated obj = (HandlerNotAnnotated) up.process("/notannotated/katzeklo");
    assertNotNull(obj);
    assertTrue(obj.isBeforeCalled());
  }


  public void testAfterNotAnnotated() throws Exception
  {
    HandlerNotAnnotated obj = (HandlerNotAnnotated) up.process("/notannotated/katzeklo");
    assertNotNull(obj);
    assertTrue(obj.isAfterCalled());
  }


  public void testAfterVar1NotAnnotated() throws Exception
  {
    HandlerNotAnnotated obj = (HandlerNotAnnotated) up.process("/notannotated/katzeklo");
    assertNotNull(obj);
    assertTrue(obj.isAfterVar1Called());
  }


  public void testBeforeVar1NotAnnotated() throws Exception
  {
    HandlerNotAnnotated obj = (HandlerNotAnnotated) up.process("/notannotated/katzeklo");
    assertNotNull(obj);
    assertTrue(obj.isBeforeVar1Called());
  }


  public void testInitNestedNotAnnotated() throws Exception
  {
    HandlerNotAnnotated obj = (HandlerNotAnnotated) up.process("/notannotated/katzeklo");
    assertNotNull(obj);
    assertFalse(obj.isInitNestedCalled());
  }


  public void testInitNestedAnnotated() throws Exception
  {
    HandlerAnnotated obj = (HandlerAnnotated) up.process("/annotated/katzeklo");
    assertNotNull(obj);
    assertFalse(obj.isInitNestedCalled());
  }


  public void testInitNestedNotAnnotatedNested() throws Exception
  {
    HandlerNotAnnotated obj = (HandlerNotAnnotated) up.process("/notannotated/katzeklo/nested");
    assertNotNull(obj);
    assertTrue(obj.isInitNestedCalled());
  }


  public void testInitNestedAnnotatedNested() throws Exception
  {
    HandlerAnnotated obj = (HandlerAnnotated) up.process("/annotated/katzeklo/nested");
    assertNotNull(obj);
    assertTrue(obj.isInitNestedCalled());
  }


  public void testInitNestedAnnotatedNestedBefore() throws Exception
  {
    HandlerAnnotated obj = (HandlerAnnotated) up.process("/annotated/katzeklo/nested");
    assertNotNull(obj);
    assertTrue(obj.getNested().isBeforeCalled());
  }


  public void testInitNestedAnnotatedNestedAfter() throws Exception
  {
    HandlerAnnotated obj = (HandlerAnnotated) up.process("/annotated/katzeklo/nested");
    assertNotNull(obj);
    assertTrue(obj.getNested().isAfterCalled());
  }
}
