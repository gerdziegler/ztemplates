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
 * 20.09.2007
 * @author www.gerdziegler.de
 */
package org.ztemplates.test.actions.urlhandler.repository.simple;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;

public class UrlHandlerRepositoryTest extends TestCase
{
  static Logger log = Logger.getLogger(UrlHandlerRepositoryTest.class);

  ZIUrlHandler up;


  protected void setUp() throws Exception
  {
    super.setUp();
    up = ZTestUrlHandlerFactory.create(UrlHandlerRepositoryTest.class.getPackage().getName(),
        ZTestUrlHandlerFactory.defaultSecurityService);
  }


  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testSimple() throws Exception
  {
    Simple obj = (Simple) up.process("/index");
    assertNotNull(obj);
  }


  public void test1() throws Exception
  {
    Handler1 obj = (Handler1) up.process("/audiobooks/category/Katzeklo_123/page/1/sortby-4.5.6");
    assertNotNull(obj);
    assertEquals("Katzeklo", obj.getTitle());
    assertEquals("123", obj.getCategoryId());
    assertEquals("4.5.6", obj.getSortBy());
    assertEquals("1", obj.getPageNum());
  }


  public void test2() throws Exception
  {
    ZIUrlHandler up = ZTestUrlHandlerFactory.create(UrlHandlerRepositoryTest.class.getPackage()
        .getName(), ZTestUrlHandlerFactory.defaultSecurityService);
    Handler1 obj = (Handler1) up.process("/audiobooks/category/Katzeklo_123/page/1");
    assertNotNull(obj);
    assertEquals("Katzeklo", obj.getTitle());
    assertEquals("123", obj.getCategoryId());
    assertNull(obj.getSortBy());
    assertEquals("1", obj.getPageNum());
  }


  public void test3() throws Exception
  {
    ZIUrlHandler up = ZTestUrlHandlerFactory.create(UrlHandlerRepositoryTest.class.getPackage()
        .getName(), ZTestUrlHandlerFactory.defaultSecurityService);
    Handler1 obj = (Handler1) up.process("/audiobooks/category/Katzeklo_123/sortby-456");
    assertNotNull(obj);
    assertEquals("Katzeklo", obj.getTitle());
    assertEquals("123", obj.getCategoryId());
    assertNull(obj.getPageNum());
    assertEquals("456", obj.getSortBy());
  }


  public void testParameter1() throws Exception
  {
    ZIUrlHandler up = ZTestUrlHandlerFactory.create(UrlHandlerRepositoryTest.class.getPackage()
        .getName(), ZTestUrlHandlerFactory.defaultSecurityService);
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("param1", new String[]
    {
      "value1"
    });
    Handler1 obj = (Handler1) up.process("/audiobooks/category/Katzeklo_123/sortby-456", param);
    assertNotNull(obj);
    assertEquals("Katzeklo", obj.getTitle());
    assertEquals("123", obj.getCategoryId());
    assertNull(obj.getPageNum());
    assertEquals("456", obj.getSortBy());
    assertEquals("value1", obj.getParam1());
  }
}
