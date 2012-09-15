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
package org.ztemplates.test.actions.urlhandler.match.handler;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTreeFactory;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchedUrl;

public class ParseTreeTest extends TestCase
{
  static Logger log = Logger.getLogger(ParseTreeTest.class);

  ZIClassRepository classRepo;

  ZMatchTree matchTree;


  protected void setUp() throws Exception
  {
    super.setUp();
    classRepo = ZClassRepository.create(ParseTreeTest.class);
    matchTree = new ZMatchTree();
    new ZMatchTreeFactory(classRepo).addToMatchTree(matchTree);
  }


  protected void tearDown() throws Exception
  {
    classRepo = null;
    matchTree = null;
    super.tearDown();
  }


  public void testParseTree() throws Exception
  {
    ZMatchedUrl proc = matchTree.match("/path1/path2");
    assertNotNull(proc);
  }


  public void testParseTree2() throws Exception
  {
    ZMatchedUrl proc = matchTree.match("/path2/path2");
    assertNull(proc);
  }


  public void testParseTree3() throws Exception
  {
    ZMatchedUrl proc = matchTree.match("/path1/path3");
    assertNull(proc);
  }


  public void testParseTree4() throws Exception
  {
    ZMatchedUrl proc = matchTree.match("/path3/v3-id");
    assertNotNull(proc);
    assertEquals("v3", proc.getValues().get(0));
  }


  public void testParseTree5() throws Exception
  {
    ZMatchedUrl proc = matchTree.match("/path4/klo-x-id-a-klo/katze");
    assertNotNull(proc);
    assertEquals("x", proc.getValues().get(0));
    assertEquals("a", proc.getValues().get(1));
  }


  public void testParseTree6EmptyVariable() throws Exception
  {
    ZMatchedUrl proc = matchTree.match("/path4/klo--id-a-klo/katze");
    assertNull(proc);
  }


  public void testParseTree7() throws Exception
  {
    ZMatchedUrl proc = matchTree.match("/path5/klo-x");
    assertNotNull(proc);
    assertEquals("x", proc.getValues().get(0));
  }


  public void testParseTree8() throws Exception
  {
    ZMatchedUrl proc = matchTree.match("/path5/klo-");
    assertNull(proc);
  }
}
