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
package org.ztemplates.test.actions.urlhandler.match.handler.tail;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchedUrl;

public class TailParseTreeTest extends TestCase
{
  static Logger log = Logger.getLogger(TailParseTreeTest.class);

  ZIClassRepository classRepo;

  ZMatchTree han;


  protected void setUp() throws Exception
  {
    super.setUp();
    classRepo = ZClassRepository.create(TailParseTreeTest.class);
    han = new ZMatchTree(classRepo);
  }


  protected void tearDown() throws Exception
  {
    classRepo = null;
    han = null;
    super.tearDown();
  }


  public void testTail1() throws Exception
  {
    ZMatchedUrl proc = han.match("/path6/123");
    assertNotNull(proc);
  }


  public void testTail2() throws Exception
  {
    ZMatchedUrl proc = han.match("/path6/123/456");
    assertNotNull(proc);
  }


  public void testTail3() throws Exception
  {
    ZMatchedUrl proc = han.match("/path6123/456/sdfsdf");
    assertNotNull(proc);
  }


  public void testTail4() throws Exception
  {
    ZMatchedUrl proc = han.match("/path7/123/456/sdfsdf");
    assertNotNull(proc);
  }


  public void testTail5() throws Exception
  {
    ZMatchedUrl proc = han.match("/path7123/456/sdfsdf");
    assertNull(proc);
  }
}
