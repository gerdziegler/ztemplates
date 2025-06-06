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
package org.ztemplates.test.actions.urlhandler.prop.plain;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.ZUrlFactory;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;
import org.ztemplates.test.mock.ZMock;

public class PropTest extends TestCase
{
  static Logger log = Logger.getLogger(PropTest.class);

  ZIUrlHandler up;

  ZIUrlFactory urlFactory;


  @Override
  protected void setUp() throws Exception
  {
    super.setUp();
    ZMock.setUp();

    urlFactory = new ZUrlFactory(ZTestUrlHandlerFactory.defaultSecureUrlDecorator, "utf-8");

    up = ZTestUrlHandlerFactory.create(PropTest.class.getPackage().getName(), ZTestUrlHandlerFactory.defaultSecurityService);
  }


  @Override
  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testParamProp() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("paramProp", new String[]
    {
        "valParamProp",
    });

    Handler obj = (Handler) up.process("/proptest", param);
    Assert.assertEquals("valParamProp", obj.getParamProp().getStringValue());
  }


  public void testVarProp() throws Exception
  {
    Handler obj = (Handler) up.process("/proptest/varValue");
    Assert.assertEquals("varValue", obj.getVarProp().getStringValue());
  }


  public void testVarPropUrl() throws Exception
  {
    Handler obj = new Handler();
    obj.getVarProp().setStringValues(new String[]
    {
        "varValue"
    });
    String url = urlFactory.createUrl(obj);
    Assert.assertEquals("/proptest/varValue", url);
  }


  public void testVarPropUrl2() throws Exception
  {
    Handler obj = new Handler();
    obj.getVarProp().setStringValues(null);
    String url = urlFactory.createUrl(obj);
    Assert.assertEquals("/proptest", url.toString());
  }


  public void testParamPropUrl() throws Exception
  {
    Handler obj = new Handler();
    obj.getParamProp().setStringValues(new String[]
    {
        "value"
    });
    String url = urlFactory.createUrl(obj);
    Assert.assertEquals("/proptest?paramProp=value", url.toString());
  }


  public void testParamPropUrl2() throws Exception
  {
    Handler obj = new Handler();
    obj.getParamProp().setStringValues(null);
    String url = urlFactory.createUrl(obj);
    Assert.assertEquals("/proptest", url.toString());
  }

}
