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

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.ZUrlFactory;

public class NestedSecureTest extends TestCase
{
  static Logger log = Logger.getLogger(NestedSecureTest.class);


  public void test1() throws Exception
  {
    Handler h1 = new Handler();
    h1.setNested(new NestedHandler1());

    ZIUrlFactory urlFactory = new ZUrlFactory("utf-8");
    String url = urlFactory.createUrl(h1);
    Assert.assertEquals("/test/nested1", url);
  }


  public void test2() throws Exception
  {
    Handler h1 = new Handler();
    h1.setNested(new NestedHandler2());

    ZIUrlFactory urlFactory = new ZUrlFactory("utf-8");
    String url = urlFactory.createUrl(h1);
    Assert.assertEquals("/test/nested2", url);
  }


  public void test3() throws Exception
  {
    Handler2 h1 = new Handler2();
    h1.setNested(new NestedHandler2());

    ZIUrlFactory urlFactory = new ZUrlFactory("utf-8");
    String url = urlFactory.createUrl(h1);
    Assert.assertEquals("/test2/nested2", url.toString());
  }
}
