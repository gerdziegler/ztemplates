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
package org.ztemplates.test.actions.urlhandler.match.test1;

import java.util.List;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTreeFactory;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTreeNode;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTermFactory;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTermList;

public class TreeTest extends TestCase
{
  static Logger log = Logger.getLogger(TreeTest.class);

  ZIClassRepository classRepo;


  protected void setUp() throws Exception
  {
    super.setUp();
    classRepo = ZClassRepository.create(TreeTest.class);
  }


  protected void tearDown() throws Exception
  {
    classRepo = null;
    super.tearDown();
  }


  public void test1() throws Exception
  {
    ZTreeTermFactory factory = new ZTreeTermFactory();

    List<ZTreeTermList> l = factory.expand(classRepo, Handler1.class);
    log.info(l);
    assertEquals(1, l.size());
  }


  public void testParseTree() throws Exception
  {
    ZMatchTree tree = new ZMatchTreeFactory().createMatchTree(classRepo);
    List<ZMatchTreeNode> roots = tree.getRoots();
    assertNotNull(tree.toConsoleString(), roots.get(0).getChildren().get(0).getHandler());
  }
}
