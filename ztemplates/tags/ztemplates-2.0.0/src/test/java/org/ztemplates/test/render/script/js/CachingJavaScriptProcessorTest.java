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
package org.ztemplates.test.render.script.js;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.web.script.javascript.ZCachingJavaScriptProcessor;
import org.ztemplates.web.script.javascript.ZCachingJavaScriptProcessorData;
import org.ztemplates.web.script.javascript.ZDefaultJavaScriptGroupingStrategy;
import org.ztemplates.web.script.javascript.ZDefaultJavaScriptPreprocessor;
import org.ztemplates.web.script.javascript.ZICachingJavaScriptProcessorContext;
import org.ztemplates.web.script.javascript.ZIJavaScriptGroupingStrategy;
import org.ztemplates.web.script.javascript.ZIJavaScriptPreprocessor;

public class CachingJavaScriptProcessorTest extends TestCase
{
  static Logger log = Logger.getLogger(CachingJavaScriptProcessorTest.class);

  final ZICachingJavaScriptProcessorContext jsContext = new ZICachingJavaScriptProcessorContext()
  {
    public byte[] mergeWebResources(List<String> urls) throws Exception
    {
      return new byte[0];
    }


    public ZCachingJavaScriptProcessorData getData()
    {
      return new ZCachingJavaScriptProcessorData();
    }


    public String getContextPath()
    {
      return null;
    }


    public String createUrl(String js) throws Exception
    {
      return js;
    }
  };


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
    // ZIJavaScriptStrategy p = new ZDefaultJavaScriptStrategy();
    // List<String> scripts = new ArrayList<String>();
    // scripts.add("/0");
    // scripts.add("/1");
    // scripts.add("/2");
    // scripts.add("http://2");
    // scripts.add("/4");
    // scripts.add("/5");
    // scripts.add("http://6");
    // List<List<String>> groups = p.group(scripts);
    // Assert.assertEquals(4, groups.size());
    // Assert.assertEquals(3, groups.get(0).size());
    // Assert.assertEquals(1, groups.get(1).size());
    // Assert.assertEquals(2, groups.get(2).size());
    // Assert.assertEquals(1, groups.get(3).size());
  }


  public void test2() throws Exception
  {
    // ZIClassRepository classRepository =
    // ZClassRepository.create(CachingJavaScriptProcessorTest.class);
    // ZApplication application = ZTestApplication.create(classRepository);
    // ZIServiceRepository serviceRepository = new
    // ZServiceRepositoryImpl(application);
    // ZTemplates.setServiceRepository(serviceRepository);

    ZCachingJavaScriptProcessor p = new ZCachingJavaScriptProcessor(jsContext);
    List<String> scripts = new ArrayList<String>();
    scripts.add("http://2");
    scripts.add("/2");
    ZIJavaScriptGroupingStrategy groupingStrategy = new ZDefaultJavaScriptGroupingStrategy();
    List<List<String>> groups = groupingStrategy.group(scripts);
    Assert.assertEquals(2, groups.size());
    ZIJavaScriptPreprocessor replacingStrategy = new ZDefaultJavaScriptPreprocessor();
    String s = p.computeHtmlTags(scripts, replacingStrategy, groupingStrategy);
    log.info(s);
    Assert.assertTrue(s, s.indexOf("http://2") >= 0);
  }
  // public void test3() throws Exception
  // {
  // ZIJavaScriptStrategy p = new ZDefaultJavaScriptStrategy();
  // SortedSet<ZJavaScriptExposed> scripts = new TreeSet<ZJavaScriptExposed>();
  // scripts.add(new ZJavaScriptExposed(0, "http://2" /* , true, true */));
  // scripts.add(new ZJavaScriptExposed(0, "http://2" /* , true, true */));
  // List<List<ZJavaScriptExposed>> groups = p.group(scripts);
  // Assert.assertEquals(1, groups.size());
  // Assert.assertEquals(1, groups.get(0).size());
  // }
}
