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
package org.ztemplates.test.actions.expression.test1;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.expression.ZExpression;
import org.ztemplates.actions.expression.ZParserException;
import org.ztemplates.actions.expression.ZTail;
import org.ztemplates.actions.expression.ZTerm;

public class ExpressionTest extends TestCase
{
  static Logger log = Logger.getLogger(ExpressionTest.class);


  protected void setUp() throws Exception
  {
    super.setUp();
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
  }


  public void test1() throws Exception
  {
    doit("audiobooks/category/${title}_${categoryId}[/page/${pageNum}][/sortby-${sortBy}]");
  }


  public void test2() throws Exception
  {
    doit("audiobooks/category/${title}_${categoryId}[/page/${pageNum}[/sortby-${sortBy}]katze]");
  }


  public void test3() throws Exception
  {
    doit("${title}_${categoryId}[/page/${pageNum}[/sortby-${sortBy}]katze]");
  }


  public void test4() throws Exception
  {
    doit("[]");
  }


  public void test5() throws Exception
  {
    doit("[katze]");
  }


  public void test6() throws Exception
  {
    doit("${i}");
  }


  public void test7() throws Exception
  {
    doit("${i}");
  }


  public void test8() throws Exception
  {
    doit("[${i}]");
  }


  public void test9() throws Exception
  {
    doit("[[[[[[a]a]a]a]a]a]");
  }


  public void test10() throws Exception
  {
    doit("[[[[[[a]a]a]a]a]a]/*{tail}");
  }


  public void test11() throws Exception
  {
    ZExpression exp = doit("katze*{tail}");
    ZTerm term = exp.getContent().get(exp.getContent().size() - 1);
    assertTrue(term instanceof ZTail);
  }


  public void testFail1() throws Exception
  {
    try
    {
      ZExpression p = new ZExpression("${}");
      fail("empty variable not allowed");
    }
    catch (ZParserException e)
    {
    }
  }


  public void testFail2() throws Exception
  {
    try
    {
      ZExpression p = new ZExpression("${");
      fail("missing parantheses");
    }
    catch (ZParserException e)
    {
    }
  }


  public void testFail3() throws Exception
  {
    try
    {
      ZExpression p = new ZExpression("[${i$}]");
      fail("misplaced '$'");
    }
    catch (ZParserException e)
    {
    }
  }


  public void testFail4() throws Exception
  {
    try
    {
      ZExpression p = new ZExpression("[${i[}]");
      fail("misplaced '['");
    }
    catch (ZParserException e)
    {
    }
  }


  public void testFail5() throws Exception
  {
    try
    {
      ZExpression p = new ZExpression("]");
      fail("misplaced ']'");
    }
    catch (ZParserException e)
    {
    }
  }


  public void testFail6() throws Exception
  {
    try
    {
      ZExpression p = new ZExpression("[test]ka]tze");
      fail("misplaced ']'");
    }
    catch (ZParserException e)
    {
    }
  }


  public void testFail7() throws Exception
  {
    try
    {
      ZExpression p = new ZExpression("[test]ka[tze");
      fail("misplaced '['");
    }
    catch (ZParserException e)
    {
    }
  }


  public void testFail8() throws Exception
  {
    try
    {
      ZExpression p = new ZExpression("[test]ka$tze");
      fail("misplaced '$'");
    }
    catch (ZParserException e)
    {
    }
  }


  public void testFail9() throws Exception
  {
    try
    {
      ZExpression p = new ZExpression("*{tail}-");
      fail("tail must be last");
    }
    catch (ZParserException e)
    {
    }
  }


  public void testFail10() throws Exception
  {
    try
    {
      ZExpression exp = new ZExpression("${var1}${var2}");
      fail("variables without literal in between");
    }
    catch (ZParserException e)
    {
    }
  }


  public void testFail11() throws Exception
  {
    try
    {
      ZExpression exp = new ZExpression("${var1}*{var2}");
      fail("variables without literal in between");
    }
    catch (ZParserException e)
    {
    }
  }


  public void testFail12() throws Exception
  {
    try
    {
      ZExpression exp = new ZExpression("${var1}#{var2}");
      fail("variables without literal in between");
    }
    catch (ZParserException e)
    {
    }
  }


  private ZExpression doit(String pattern) throws Exception
  {
    ZExpression exp = new ZExpression(pattern);
    log.debug(exp.toXml());
    return exp;
    // String regex = exp.toRegex();
    // log.debug(regex);
    // List<String> samples = exp.createSamples();
    // for (String crt : samples)
    // {
    // log.debug("sample: '" + crt + "'");
    // assertTrue(Pattern.matches(regex, crt));
    // }
  }

  //  public void testSortNodes() throws Exception
  //  {
  //    ZMatchTree matchTree = new ZMatchTree();
  //    
  //    String[] arr = {"/agb", "/aaaaaaaaaa", "/index"};
  //    for(String s: arr)
  //    {
  //      ZTreeTermList ttl = new ZTreeTermList();
  //      ZTreeTerm term = new ZTreeTerm()
  //      ttl.getTerms().add();
  //      matchTree.add(ttl);
  //
  //      ZExpression exp = new ZExpression(s);
  //      
  //      ZMatchTreeNode node = new ZMatchTreeNode();
  //      crt.getSegments().add(new ZSegmentLiteral(tl.getText()));
  //    }    
  //    matchTree.sort();
  //  }
  //  
  //  if (term instanceof ZTreeSlash)
  //  {
  //    ZMatchTreeNode newNode = new ZMatchTreeNode();
  //    ret.add(newNode);
  //    crt = newNode;
  //  }
  //  else if (term instanceof ZTreeLiteral)
  //  {
  //    ZTreeLiteral tl = (ZTreeLiteral) term;
  //    crt.getSegments().add(new ZSegmentLiteral(tl.getText()));
  //  }
  //  else if (term instanceof ZTreeVariable)
  //  {
  //    ZTreeVariable tl = (ZTreeVariable) term;
  //    crt.getSegments().add(new ZSegmentVariable(tl.getName()));
  //  }
  //  else if (term instanceof ZTreeTail)
  //  {
  //    ZTreeTail tl = (ZTreeTail) term;
  //    crt.getSegments().add(new ZSegmentTail(tl.getName()));
  //  }

  // private void doit(String pattern, String match) throws Exception
  // {
  // Expression exp = new Expression(pattern);
  // log.debug(exp.toXml());
  // String regex = exp.toRegex();
  // log.debug(regex);
  // assertTrue(Pattern.matches(regex, match));
  // }

}
