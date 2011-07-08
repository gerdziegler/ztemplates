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
package org.ztemplates.test.actions.urlhandler.secure.simple;

import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZISecureUrlDecorator;
import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.ZUrlFactory;

public class SimpleSecureTest extends TestCase
{
  static Logger log = Logger.getLogger(SimpleSecureTest.class);


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


  public void test1() throws Exception
  {
    Handler1 h1 = new Handler1();
    ZIUrlFactory urlFactory = new ZUrlFactory("utf-8");
    String url = urlFactory.createUrl(h1);
    Assert.assertEquals("/secure/test", url);
  }


  public void test2() throws Exception
  {
    Handler1 h1 = new Handler1();
    ZISecureUrlDecorator sec = new ZISecureUrlDecorator()
    {
      public String addSecurityToUrl(String url, Set<String> roles)
      {
        Assert.assertEquals(1, roles.size());
        Assert.assertEquals("katze", roles.iterator().next());
        return "/mysec" + url;
      }


      public String removeSecurityFromUrl(String url)
      {
        return null;
      }
    };

    ZIUrlFactory urlFactory = new ZUrlFactory(sec, "utf-8");
    String url = urlFactory.createUrl(h1);
    Assert.assertEquals("/mysec/test", url);
  }
}
