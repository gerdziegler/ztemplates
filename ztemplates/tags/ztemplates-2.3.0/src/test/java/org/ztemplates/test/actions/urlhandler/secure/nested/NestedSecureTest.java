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
package org.ztemplates.test.actions.urlhandler.secure.nested;

import java.util.Set;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZISecureUrlDecorator;
import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.ZUrlFactory;

public class NestedSecureTest extends TestCase
{
  static Logger log = Logger.getLogger(NestedSecureTest.class);


  public void test1() throws Exception
  {
    Handler h1 = new Handler();
    h1.setNested(new NestedHandler1());
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
    Assert.assertEquals("/mysec/test/nested1", url);
  }


  public void test2() throws Exception
  {
    Handler h1 = new Handler();
    h1.setNested(new NestedHandler2());

    ZISecureUrlDecorator sec = new ZISecureUrlDecorator()
    {
      public String addSecurityToUrl(String url, Set<String> roles)
      {
        Assert.assertEquals(2, roles.size());
        return "/mysec" + url;
      }


      public String removeSecurityFromUrl(String url)
      {
        return null;
      }

    };

    ZIUrlFactory urlFactory = new ZUrlFactory(sec, "utf-8");
    String url = urlFactory.createUrl(h1);
    Assert.assertEquals("/mysec/test/nested2", url);
  }


  public void test3() throws Exception
  {
    Handler2 h1 = new Handler2();
    h1.setNested(new NestedHandler2());

    ZISecureUrlDecorator sec = new ZISecureUrlDecorator()
    {
      public String addSecurityToUrl(String url, Set<String> roles)
      {
        Assert.assertEquals(1, roles.size());
        String role = roles.iterator().next();
        return role + url;
      }


      public String removeSecurityFromUrl(String url)
      {
        return null;
      }

    };

    ZIUrlFactory urlFactory = new ZUrlFactory(sec, "utf-8");
    String url = urlFactory.createUrl(h1);
    Assert.assertEquals("maus/test2/nested2", url.toString());
  }
}
