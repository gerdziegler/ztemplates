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
package org.ztemplates.test.actions.urlhandler.repository.simple;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.ZUrlFactory;

public class ZUrlFactoryTest extends TestCase
{
  static Logger log = Logger.getLogger(ZUrlFactoryTest.class);


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
    h1.setTitle("my_title");
    h1.setCategoryId("my_category");
    ZIUrlFactory urlFactory = new ZUrlFactory("utf-8");
    String url = urlFactory.createUrl(h1);
    Assert.assertEquals("/audiobooks/category/my_title_my_category", url);
  }


  public void test2() throws Exception
  {
    Handler2 h1 = new Handler2();
    h1.setTitle("my_title");
    h1.setCategoryId("my_category");
    h1.setPageNum("my_page_num");
    ZIUrlFactory urlFactory = new ZUrlFactory("utf-8");
    String url = urlFactory.createUrl(h1);
    Assert.assertEquals("/audiobooks2/category/my_title/my_category/page/my_page_num", url);
  }


  public void test3() throws Exception
  {
    Handler2 h1 = new Handler2();
    h1.setTitle("my_title");
    h1.setCategoryId("my_category");
    h1.setSortBy("my_sortBy");
    ZIUrlFactory urlFactory = new ZUrlFactory("utf-8");
    String url = urlFactory.createUrl(h1);
    Assert.assertEquals("/audiobooks2/category/my_title/my_category", url);
  }
}
