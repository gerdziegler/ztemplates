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

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZISecurityProvider;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;

public class NestedSecureTest extends TestCase
{
  static Logger log = Logger.getLogger(NestedSecureTest.class);


  public void test1() throws Exception
  {
    Handler h1 = new Handler();
    h1.setNested(new NestedHandler1());
    ZISecurityProvider sec = new ZISecurityProvider()
    {
      public String addSecurityToUrl(String url, Set<String> roles)
      {
        assertEquals(1, roles.size());
        assertEquals("katze", roles.iterator().next());
        return "/mysec" + url;
      }


      public boolean isUserInRole(String role)
      {
        return false;
      }


      public String removeSecurityFromUrl(String url)
      {
        return null;
      }


      public String getUserName()
      {
        return "defaultUser";
      }
    };

    ZIUrlHandler up = ZTestUrlHandlerFactory.create(NestedSecureTest.class.getPackage().getName(),
        sec);
    String url = up.createUrl(h1);
    assertEquals("/mysec/test/nested1", url);
  }


  public void test2() throws Exception
  {
    Handler h1 = new Handler();
    h1.setNested(new NestedHandler2());

    ZISecurityProvider sec = new ZISecurityProvider()
    {
      public String addSecurityToUrl(String url, Set<String> roles)
      {
        assertEquals(2, roles.size());
        return "/mysec" + url;
      }


      public boolean isUserInRole(String role)
      {
        return false;
      }


      public String removeSecurityFromUrl(String url)
      {
        return null;
      }


      public String getUserName()
      {
        return "defaultUser";
      }
    };

    ZIUrlHandler up = ZTestUrlHandlerFactory.create(NestedSecureTest.class.getPackage().getName(),
        sec);
    String url = up.createUrl(h1);
    assertEquals("/mysec/test/nested2", url);
  }


  public void test3() throws Exception
  {
    Handler2 h1 = new Handler2();
    h1.setNested(new NestedHandler2());

    ZISecurityProvider sec = new ZISecurityProvider()
    {
      public String addSecurityToUrl(String url, Set<String> roles)
      {
        assertEquals(1, roles.size());
        String role = roles.iterator().next();
        return role + url;
      }


      public boolean isUserInRole(String role)
      {
        return false;
      }


      public String removeSecurityFromUrl(String url)
      {
        return null;
      }


      public String getUserName()
      {
        return "defaultUser";
      }
    };

    ZIUrlHandler up = ZTestUrlHandlerFactory.create(NestedSecureTest.class.getPackage().getName(),
        sec);
    String url = up.createUrl(h1);
    assertEquals("maus/test2/nested2", url.toString());
  }
}
