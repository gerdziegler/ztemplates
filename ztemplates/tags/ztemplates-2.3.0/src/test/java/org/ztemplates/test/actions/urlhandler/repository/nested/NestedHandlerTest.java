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

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.ZUrlFactory;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;

public class NestedHandlerTest extends TestCase
{
  static Logger log = Logger.getLogger(NestedHandlerTest.class);


  @Override
  protected void setUp() throws Exception
  {
    super.setUp();
  }


  @Override
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
    ZIUrlHandler up = ZTestUrlHandlerFactory.create(NestedHandlerTest.class.getPackage().getName(), ZTestUrlHandlerFactory.defaultSecurityService);

    Handler obj = (Handler) up.process("/audiobooks/nested/katzeklo");
    Assert.assertNotNull(obj);
    Assert.assertEquals("katzeklo", obj.getNested().getValue());
    Assert.assertEquals(1, obj.getBeforeCalled());
    Assert.assertEquals(1, obj.getAfterCalled());
    Assert.assertEquals(1, obj.getBeforeNestedCalled());
    Assert.assertEquals(1, obj.getAfterNestedCalled());

    obj.getNested().setValue("froh");

    ZIUrlFactory urlFactory = new ZUrlFactory(ZTestUrlHandlerFactory.defaultSecureUrlDecorator, "utf-8");
    String s = urlFactory.createUrl(obj);
    log.debug(s);

    Handler obj2 = (Handler) up.process(s);
    Assert.assertEquals("froh", obj2.getNested().getValue());
    Assert.assertEquals(1, obj2.getBeforeCalled());
    Assert.assertEquals(1, obj2.getAfterCalled());
    Assert.assertEquals(1, obj2.getNested().getBeforeCalled());
    Assert.assertEquals(1, obj2.getNested().getAfterCalled());
    Assert.assertEquals(1, obj2.getNested().getBeforeValueCalled());
    Assert.assertEquals(1, obj2.getNested().getAfterValueCalled());
  }


  public void test3() throws Exception
  {
    ZUrlFactory urlFact = new ZUrlFactory("UTF-8");
    Handler action = new Handler();
    NestedHandler nestedHandler = new NestedHandler();
    nestedHandler.setValue("testValue");
    action.setNested(nestedHandler);
    String url = urlFact.createUrl(action);
    assertEquals("/audiobooks/nested/testValue", url);
    TreeHandler tree = new TreeHandler();
    tree.setTreeId("treeId");
    action.setTree(tree);
    String url2 = urlFact.createUrl(action);
    assertEquals("/audiobooks/nested/testValue/tree/treeId", url2);

    action.getNested().setValue(null);
    try
    {
      String url3 = urlFact.createUrl(action);
      fail(url3);
    }
    catch (Exception e)
    {
    }
  }
}
