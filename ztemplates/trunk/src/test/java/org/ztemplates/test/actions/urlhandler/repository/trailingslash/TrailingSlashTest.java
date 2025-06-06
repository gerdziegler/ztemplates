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
package org.ztemplates.test.actions.urlhandler.repository.trailingslash;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.ZUrlFactory;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;

public class TrailingSlashTest extends TestCase
{
  static Logger log = Logger.getLogger(TrailingSlashTest.class);

  ZIUrlHandler up;

  ZIUrlFactory urlFactory;


  @Override
  protected void setUp() throws Exception
  {
    super.setUp();
    up = ZTestUrlHandlerFactory.create(TrailingSlashTest.class.getPackage().getName(), ZTestUrlHandlerFactory.defaultSecurityService);
    urlFactory = new ZUrlFactory("utf-8");

  }


  @Override
  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void test1() throws Exception
  {
    Handler1 obj = (Handler1) up.process("/index");
    Assert.assertNotNull(obj);
  }


  public void test2() throws Exception
  {
    Handler1 obj = (Handler1) up.process("/index/");
    Assert.assertNotNull(obj);
  }


  public void test3() throws Exception
  {
    Handler2 obj = (Handler2) up.process("/");
    Assert.assertNotNull(obj);
  }


  public void test4() throws Exception
  {
    Handler1 obj = new Handler1();
    String url = urlFactory.createUrl(obj);
    Assert.assertEquals("/index", url);
  }

}
