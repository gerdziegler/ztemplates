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
package org.ztemplates.test.actions.urlhandler.tree.parameters;

import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.ZUrlFactory;
import org.ztemplates.actions.urlhandler.ZUrl;
import org.ztemplates.actions.urlhandler.tree.ZTreeUrlHandler;
import org.ztemplates.test.ZTestUrlHandlerFactory;
import org.ztemplates.test.mock.ZMock;

public class ParameterTest extends TestCase
{
  static Logger log = Logger.getLogger(ParameterTest.class);

  ZTreeUrlHandler up;

  ZIUrlFactory urlFactory;


  @Override
  protected void setUp() throws Exception
  {
    super.setUp();
    ZMock.setUp();
    up = (ZTreeUrlHandler) ZTestUrlHandlerFactory.create(ParameterTest.class.getPackage().getName(), ZTestUrlHandlerFactory.defaultSecurityService);
    urlFactory = new ZUrlFactory(ZTestUrlHandlerFactory.defaultSecureUrlDecorator, "utf-8");
  }


  @Override
  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testParametersInRootClass() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("param2", new String[]
    {
        "value2"
    });

    Handler obj = (Handler) up.process("/mytext/nested/katzeklo", param);
    Assert.assertNotNull(obj);
    Assert.assertEquals(obj.getParam2(), "value2", obj.getParam2());
  }


  public void testParametersInRootClassNull() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("param2", new String[]
    {
        ""
    });

    Handler obj = (Handler) up.process("/mytext/nested/katzeklo", param);
    Assert.assertNotNull(obj);
    Assert.assertNull("'" + obj.getParam2() + "'", obj.getParam2());
  }


  public void testParametersInNestedClass() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("param3", new String[]
    {
        "value3"
    });

    Handler obj = (Handler) up.process("/mytext/nested/katzeklo", param);
    Assert.assertNotNull(obj);
    Assert.assertEquals(obj.getNested().getParam3(), "value3", obj.getNested().getParam3());
  }


  public void testParametersAreAssignedToAllActions() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("param1", new String[]
    {
        "value1"
    });

    Handler obj = (Handler) up.process("/mytext/nested/katzeklo", param);
    Assert.assertNotNull(obj);
    Assert.assertNotNull(obj.getParam1(), obj.getParam1());
    Assert.assertEquals(obj.getNested().getParam1(), "value1", obj.getNested().getParam1());
  }


  public void testUrlParamInRoot() throws Exception
  {
    Handler obj1 = new Handler();
    obj1.setParam1("value1");
    String surl = urlFactory.createUrl(obj1);
    ZUrl parsedUrl = up.parse(surl);

    Assert.assertEquals("value1", parsedUrl.getParameterMap().get("param1")[0]);
  }


  public void testFieldParamInRoot() throws Exception
  {
    Handler obj1 = new Handler();
    obj1.field = "fieldValue";
    String surl = urlFactory.createUrl(obj1);
    ZUrl parsedUrl = up.parse(surl);

    Assert.assertEquals("fieldValue", parsedUrl.getParameterMap().get("field")[0]);
  }


  public void testFieldPropetyParamInRoot() throws Exception
  {
    Handler obj1 = new Handler();
    obj1.fieldProp.setValue("fieldValue");
    String surl = urlFactory.createUrl(obj1);
    ZUrl parsedUrl = up.parse(surl);

    Assert.assertEquals("fieldValue", parsedUrl.getParameterMap().get("fieldProp")[0]);
  }


  public void testUrlParametersInNested() throws Exception
  {
    Handler obj1 = new Handler();

    NestedHandler nested = new NestedHandler();
    obj1.setNested(nested);
    nested.setParam1("value1n");
    String surl = urlFactory.createUrl(obj1);
    ZUrl parsedUrl = up.parse(surl);

    Assert.assertEquals("value1n", parsedUrl.getParameterMap().get("param1")[0]);
  }


  public void testUrlParametersInBoth() throws Exception
  {
    Handler obj1 = new Handler();
    obj1.setParam1("value1");

    NestedHandler nested = new NestedHandler();
    nested.setParam1("value1n");

    obj1.setNested(nested);
    String surl = urlFactory.createUrl(obj1);
    ZUrl parsedUrl = up.parse(surl);

    Assert.assertEquals("value1n", parsedUrl.getParameterMap().get("param1")[0]);
  }


  public void testUrlParametersInBothAll() throws Exception
  {
    Handler obj1 = new Handler();
    obj1.setParam1("value1");
    obj1.setParam2("value2");

    NestedHandler nested = new NestedHandler();
    nested.setParam1("value1n");
    nested.setParam3("value3n");

    obj1.setNested(nested);
    String surl = urlFactory.createUrl(obj1);
    ZUrl parsedUrl = up.parse(surl);

    Assert.assertEquals("value1n", parsedUrl.getParameterMap().get("param1")[0]);
    Assert.assertEquals("value2", parsedUrl.getParameterMap().get("param2")[0]);
    Assert.assertEquals("value3n", parsedUrl.getParameterMap().get("param3")[0]);
  }


  public void testParametersArray() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("paramArr4", new String[]
    {
        "valueArr1", "valueArr2",
    });

    Handler obj = (Handler) up.process("/mytext/nested/katzeklo", param);
    Assert.assertEquals("valueArr1", obj.getParamArr4()[0]);
    Assert.assertEquals("valueArr2", obj.getParamArr4()[1]);
  }


  public void testParametersEncoded() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("paramArr4", new String[]
    {
        "valueArr1 val2", "valueArr2 val2",
    });

    Handler obj = (Handler) up.process("/mytext/nested/katzeklo", param);
    Assert.assertEquals("valueArr1 val2", obj.getParamArr4()[0]);
    Assert.assertEquals("valueArr2 val2", obj.getParamArr4()[1]);
  }


  public void testParamProp() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("paramProp", new String[]
    {
        "valParamProp",
    });

    Handler obj = (Handler) up.process("/mytext/nested/katzeklo", param);
    Assert.assertEquals("valParamProp", obj.getParamProp().getStringValue());
  }
}
