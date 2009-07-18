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
package org.ztemplates.test.render.css;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.render.impl.ZCssEngine;
import org.ztemplates.render.impl.ZIRenderContext;
import org.ztemplates.render.impl.ZRenderEngine;
import org.ztemplates.render.velocity.ZVelocityRendererFactory;
import org.ztemplates.test.ZMavenClassPath;
import org.ztemplates.test.ZTestApplication;
import org.ztemplates.web.application.ZApplication;

public class CssTest extends TestCase
{
  static Logger log = Logger.getLogger(CssTest.class);

  private ZApplication application;


  protected void setUp() throws Exception
  {
    super.setUp();
    ZIClassRepository classRepository = ZClassRepository.create(ZMavenClassPath.getItems(),
        CssTest.class.getPackage().getName());
    application = ZTestApplication.create(classRepository);
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
    application = null;
  }


  public void testCssId() throws Exception
  {
    String cssId = application.getRenderApplication().getCssIdRepository().getCssId(Css1.class);
    assertEquals("Css1", cssId);
  }


  public void testMergeCss() throws Exception
  {
    ZCssEngine cssFact = application.getRenderApplication().getCssEngine();
    String css = cssFact.getCss();
    assertTrue(css, css.indexOf("Css1") >= 0);
  }


  public void testMergeCss1() throws Exception
  {
    ZCssEngine cssFact = application.getRenderApplication().getCssEngine();
    String css = cssFact.getCss();
    assertTrue(css, css.indexOf("#Css1-bg") >= 0);
  }


  public void testRenderTemplate() throws Exception
  {
    ZIRenderContext ctx = ZVelocityRendererFactory.createStandaloneRenderEngine(application
        .getRenderApplication());
    ZRenderEngine re = new ZRenderEngine();
    Css1 css1 = new Css1();
    String merged = re.render(css1, ctx);
    assertTrue(merged, merged.indexOf("Css1") >= 0);
  }
}
