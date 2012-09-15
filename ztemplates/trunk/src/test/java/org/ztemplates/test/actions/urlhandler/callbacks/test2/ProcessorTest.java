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
package org.ztemplates.test.actions.urlhandler.callbacks.test2;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZActionApplication;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.actions.urlhandler.tree.ZTreeUrlHandler;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTreeFactory;
import org.ztemplates.commons.ZIObjectFactory;
import org.ztemplates.commons.ZObjectFactory;
import org.ztemplates.test.ZTestUrlHandlerFactory;
import org.ztemplates.web.standalone.ZTemplatesStandaloneApplicationContext;

public class ProcessorTest extends TestCase
{
  static Logger log = Logger.getLogger(ProcessorTest.class);

  ZIClassRepository classRepo;

  ZIUrlHandler proc;


  @Override
  protected void setUp() throws Exception
  {
    super.setUp();
    classRepo = ZClassRepository.create(ProcessorTest.class);
    ZTemplatesStandaloneApplicationContext applicationContext = new ZTemplatesStandaloneApplicationContext(classRepo);
    ZIObjectFactory objectFactory = new ZObjectFactory();
    ZActionApplication actionApplication = new ZActionApplication(applicationContext, classRepo, objectFactory);

    ZMatchTree matchTree = new ZMatchTree();
    new ZMatchTreeFactory(classRepo).addToMatchTree(matchTree);

    proc = new ZTreeUrlHandler(
        matchTree,
        ZTestUrlHandlerFactory.defaultSecurityService,
        null,
        actionApplication);
  }


  @Override
  protected void tearDown() throws Exception
  {
    classRepo = null;
    proc = null;
    super.tearDown();
  }


  public void testNestedType1() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo");
    Assert.assertEquals(NestedHandlerImpl.class, obj.getNested().getClass());
  }


  public void testNestedType2() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested2/katzeklo-");
    Assert.assertEquals(NestedHandlerImpl2.class, obj.getNested().getClass());
  }


  public void testBeforeCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo");
    Assert.assertEquals(1, obj.getBeforeCalled());
  }


  public void testAfterCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo");
    Assert.assertEquals(1, obj.getAfterCalled());
  }


  public void testBeforeNestedCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo");
    Assert.assertEquals(1, obj.getBeforeNestedCalled());
  }


  public void testAfterNestedCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo");
    Assert.assertEquals(1, obj.getAfterNestedCalled());
  }


  public void testInitNestedCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo");
    Assert.assertEquals(1, obj.getInitNestedCalled());
  }


  public void testNestedBeforeCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo");
    Assert.assertEquals(1, obj.getNested().getBeforeCalled());
  }


  public void testNestedAfterCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo");
    Assert.assertEquals(1, obj.getNested().getAfterCalled());
  }


  public void testNestedBeforeValueCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo");
    Assert.assertEquals(1, obj.getNested().getBeforeValueCalled());
  }


  public void testNestedBeforeValueNotCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo");
    Assert.assertNull(obj.getTree());
    Assert.assertEquals(0, obj.getBeforeTreeCalled());
  }


  public void testNestedAfterValueCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo");
    Assert.assertEquals(1, obj.getNested().getAfterValueCalled());
  }


  public void testBeforeTreeCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo/tree/123");
    Assert.assertEquals(1, obj.getBeforeTreeCalled());
  }


  public void testAfterTreeCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo/tree/123");
    Assert.assertEquals(1, obj.getAfterTreeCalled());
  }


  public void testBeforeTreeCalled_After_AfterNestedCalled() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo/tree/123");
    Assert.assertTrue(obj.getBeforeTreeCalled_After_AfterNestedCalled());
  }


  public void testTreeValue() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested/katzeklo/tree/123");
    Assert.assertEquals("123", obj.getTree().getTreeId());
  }


  public void testNested2WithTree() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested2/katzeklo/tree/123-");
    NestedHandlerImpl2 impl2 = (NestedHandlerImpl2) obj.getNested();
    Assert.assertEquals("123", impl2.getTree().getTreeId());
    Assert.assertNull(obj.getTree());
  }


  public void testNested3Consumed() throws Exception
  {
    Handler obj = (Handler) proc.process("/base/nested3");
    Assert.assertNull(obj.getNested());
  }

  // public void test2() throws Exception
  // {
  // ZUrlHandlerRepository up =
  // ZUrlHandlerRepository.create(NestedHandlerTest.class.getPackage().getName());
  // Handler obj = (Handler) up.getHandler("audiobooks/nested/katzeklo");
  // assertNotNull(obj);
  // assertEquals("katzeklo", obj.getNested().getValue());
  // assertEquals(1, obj.getBeforeCalled());
  // assertEquals(1, obj.getAfterCalled());
  // assertEquals(1, obj.getBeforeNestedCalled());
  // assertEquals(1, obj.getAfterNestedCalled());
  //
  // obj.getNested().setValue("froh");
  // ZUrl url = new ZUrl(obj);
  // String s = url.toString();
  // log.debug(s);
  //
  // Handler obj2 = (Handler) up.getHandler(s);
  // assertEquals("froh", obj2.getNested().getValue());
  // assertEquals(1, obj2.getBeforeCalled());
  // assertEquals(1, obj2.getAfterCalled());
  // assertEquals(1, obj2.getNested().getBeforeCalled());
  // assertEquals(1, obj2.getNested().getAfterCalled());
  // assertEquals(1, obj2.getNested().getBeforeValueCalled());
  // assertEquals(1, obj2.getNested().getAfterValueCalled());
  // }
}
