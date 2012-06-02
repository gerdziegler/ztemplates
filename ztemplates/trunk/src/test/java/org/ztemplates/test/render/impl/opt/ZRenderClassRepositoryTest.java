/*
 * Copyright 2008 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 09.03.2008
 * @author www.gerdziegler.de
 */
package org.ztemplates.test.render.impl.opt;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.standalone.ZTemplatesStandaloneApplication;

public class ZRenderClassRepositoryTest extends TestCase
{
  static Logger log = Logger.getLogger(ZRenderClassRepositoryTest.class);

  private ZApplication application;

  private ZIClassRepository classRepo;


  protected void setUp() throws Exception
  {
    super.setUp();
    classRepo = ZClassRepository.create(Root.class);
    application = ZTemplatesStandaloneApplication.create(classRepo);
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
  }


  public void test1() throws Exception
  {
//    ZRenderClassRepository repo = ZRenderClassRepository.scan(classRepo);
//    ZIRenderContext ctx =  ZVelocityRendererFactory.createStandaloneRenderEngine2(application
//        .getRenderApplication(), repo);
//    final ZRenderEngine2 re = new ZRenderEngine2();
//    ZTemplateRenderAlgFactory fact = new ZTemplateRenderAlgFactory(repo);
//    ZRenderWithRenderer alg = (ZRenderWithRenderer)fact.getRenderAlg(repo.get(Root.class));
//    StringWriter sb = new StringWriter();
//    ZCodeGen.gen(alg, sb);
//    log.error("\n" + sb.toString());
//    String rendered = alg.exec(new Root(), ctx);
//    log.error("\n" + rendered);
//    assertTrue(rendered, rendered.indexOf("[root content]") >= 0);
//    assertTrue(rendered, rendered.indexOf("[nested content]") >= 0);
//    assertTrue(rendered, rendered.indexOf("nested-val0") >= 0);
//    assertTrue(rendered, rendered.indexOf("nested-val1") >= 0);
//    assertTrue(rendered, rendered.indexOf("nested-val2") >= 0);
//    assertTrue(rendered, rendered.indexOf("simple-value") >= 0);
  }

  //    public void test2() throws Exception
  //    {
  //      ZRenderClassRepository repo = ZRenderClassRepository.scan(classRepo);
  //      ZRenderEngine2 re = ZVelocityRendererFactory.createStandaloneRenderEngine2(application.getRenderApplication(), repo);
  //      Root root = new Root();
  //      String s = re.render(root);
  //      assertTrue(s, s.indexOf("[root content]") >= 0);
  //      assertTrue(s, s.indexOf("[nested content]") >= 0);
  //    }

}
