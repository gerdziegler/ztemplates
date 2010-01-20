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
package org.ztemplates.test.actions.urlhandler.i18n;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.ZUrlFactory;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;

public class I18nTest extends TestCase
{
  private static Logger log = Logger.getLogger(I18nTest.class);

  ZIUrlHandler up;

  ZIUrlFactory urlFactory;

  protected void setUp() throws Exception
  {
    super.setUp();
    up = ZTestUrlHandlerFactory.create(I18nTest.class.getPackage().getName(),
        ZTestUrlHandlerFactory.defaultSecurityService);
    urlFactory = new ZUrlFactory(ZTestUrlHandlerFactory.defaultSecureUrlDecorator);
  }


  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testUmlautProp() throws Exception
  {
    I18nHandler h1 = new I18nHandler();
    h1.setProp("הצü");

    String url = urlFactory.createUrl(h1);
    I18nHandler obj = (I18nHandler) up.process(url);
    assertNotNull(obj);
    assertEquals(url, h1.getProp(), obj.getProp());
  }


  public void testUmlautParam() throws Exception
  {
    I18nHandler h1 = new I18nHandler();
    h1.setParam("הצü");
    String url = urlFactory.createUrl(h1);
    I18nHandler obj = (I18nHandler) up.process(url);
    assertNotNull(obj);
    assertEquals(url, h1.getParam(), obj.getParam());
  }
}
