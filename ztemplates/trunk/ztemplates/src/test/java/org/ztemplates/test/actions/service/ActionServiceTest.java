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
package org.ztemplates.test.actions.service;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZUrlFactory;
import org.ztemplates.test.ZMavenClassPath;

public class ActionServiceTest extends TestCase
{
  static Logger log = Logger.getLogger(ActionServiceTest.class);

  ZIClassRepository classRepo;


  protected void setUp() throws Exception
  {
    super.setUp();
    classRepo = ZClassRepository.create(ZMavenClassPath.getItems(), ActionServiceTest.class.getPackage().getName());
  }


  protected void tearDown() throws Exception
  {
    classRepo = null;
    super.tearDown();
  }


  public void testZActionServiceImpl1() throws Exception
  {
    ZUrlFactory urlFactory = new ZUrlFactory();
    String url1 = urlFactory.createUrl("/index");
    String url2 = urlFactory.createUrl(url1);

    assertEquals(url1, url2);
  }

  // public void testZActionServiceImpl2() throws Exception
  // {
  // ZMatchTree matchTree = new ZMatchTree(classRepo);
  // ZISecurityProvider security = new ZTestSecurityProvider();
  // String contextPath = "/katze";
  // String prefix = null;
  // ZActionServiceImpl service = new ZActionServiceImpl(new
  // ZTreeUrlHandler(matchTree, security), contextPath, prefix);
  //
  // String url1 = service.createUrl("/index");
  // String url2 = service.createUrl(url1);
  //
  // assertEquals("/katze/index", url1);
  // assertEquals("/katze/index", url2);
  // }
}
