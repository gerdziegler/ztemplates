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
package org.ztemplates.test.actions.urlhandler.constructor;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;

public class ConstructorTest extends TestCase
{
  private static Logger log = Logger.getLogger(ConstructorTest.class);

  ZIUrlHandler up;


  @Override
  protected void setUp() throws Exception
  {
    super.setUp();
    up = ZTestUrlHandlerFactory.create(ConstructorTest.class.getPackage().getName(), ZTestUrlHandlerFactory.defaultSecurityService, "utf-8");
  }


  @Override
  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testConstructor() throws Exception
  {
    ConstructorHandler obj = (ConstructorHandler) up.process("/test-constructor");
    Assert.assertNotNull(obj);
  }
}
