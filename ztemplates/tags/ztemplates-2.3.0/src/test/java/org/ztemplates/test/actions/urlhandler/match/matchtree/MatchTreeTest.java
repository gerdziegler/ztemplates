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
package org.ztemplates.test.actions.urlhandler.match.matchtree;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchedUrl;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeLiteral;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeSlash;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTermList;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeVariable;

public class MatchTreeTest extends TestCase
{
  static Logger log = Logger.getLogger(MatchTreeTest.class);


  protected void setUp() throws Exception
  {
    super.setUp();
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
  }


  public void testMatchLiteralFirst() throws Exception
  {
    ZMatchTree tree = new ZMatchTree();

    ZTreeTermList tl1 = new ZTreeTermList();
    tl1.getTerms().add(new ZTreeSlash(this.getClass()));
    tl1.getTerms().add(new ZTreeVariable(this.getClass(), "var"));

    ZTreeTermList tl2 = new ZTreeTermList();
    tl2.getTerms().add(new ZTreeSlash(this.getClass()));
    tl2.getTerms().add(new ZTreeLiteral(this.getClass(), "123"));

    tree.add(tl1);
    tree.add(tl2);
    tree.sort();
    log.info(tree.toConsoleString());
    ZMatchedUrl mu = tree.match("/123");
    assertEquals(tree.toConsoleString() + "\n " + mu.getTermList(), tl2.toString(), mu
        .getTermList().toString());
  }


  public void testMatchOrder2() throws Exception
  {
    ZMatchTree tree = new ZMatchTree();

    ZTreeTermList tl1 = new ZTreeTermList();
    tl1.getTerms().add(new ZTreeSlash(this.getClass()));
    tl1.getTerms().add(new ZTreeVariable(this.getClass(), "var"));
    tl1.getTerms().add(new ZTreeLiteral(this.getClass(), "_"));
    tl1.getTerms().add(new ZTreeVariable(this.getClass(), "var"));

    ZTreeTermList tl2 = new ZTreeTermList();
    tl2.getTerms().add(new ZTreeSlash(this.getClass()));
    tl2.getTerms().add(new ZTreeLiteral(this.getClass(), "123_"));
    tl2.getTerms().add(new ZTreeVariable(this.getClass(), "var"));

    tree.add(tl1);
    tree.add(tl2);
    tree.sort();
    log.info(tree.toConsoleString());
    ZMatchedUrl mu = tree.match("/123_456");
    assertEquals(tree.toConsoleString() + "\n " + mu.getTermList(), tl2.toString(), mu
        .getTermList().toString());
  }


  public void testMatchOrder3() throws Exception
  {
    ZMatchTree tree = new ZMatchTree();

    ZTreeTermList tl1 = new ZTreeTermList();
    tl1.getTerms().add(new ZTreeSlash(this.getClass()));
    tl1.getTerms().add(new ZTreeVariable(this.getClass(), "12"));
    tl1.getTerms().add(new ZTreeVariable(this.getClass(), "var"));
    tl1.getTerms().add(new ZTreeVariable(this.getClass(), "45"));
    tl1.getTerms().add(new ZTreeVariable(this.getClass(), "var1"));
    tl1.getTerms().add(new ZTreeVariable(this.getClass(), "78"));
    tl1.getTerms().add(new ZTreeVariable(this.getClass(), "var2"));

    ZTreeTermList tl2 = new ZTreeTermList();
    tl2.getTerms().add(new ZTreeSlash(this.getClass()));
    tl2.getTerms().add(new ZTreeLiteral(this.getClass(), "12345678"));
    tl2.getTerms().add(new ZTreeVariable(this.getClass(), "var"));

    tree.add(tl1);
    tree.add(tl2);
    tree.sort();
    log.info(tree.toConsoleString());
    ZMatchedUrl mu = tree.match("/123456789");
    assertEquals(tree.toConsoleString() + "\n " + mu.getTermList(), tl2.toString(), mu
        .getTermList().toString());
  }
}
