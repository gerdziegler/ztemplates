/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.

 * @author www.gerdziegler.de
 */
package org.ztemplates.test.render.script.duplicates;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.render.impl.ZIWebRenderContext;
import org.ztemplates.render.impl.ZRenderEngine;
import org.ztemplates.test.ZMavenClassRepository;
import org.ztemplates.test.ZTestApplication;
import org.ztemplates.test.ZTestWebRenderContextFactory;
import org.ztemplates.web.application.ZApplication;

public class VariableNamesScriptTest extends TestCase
{
  static Logger log = Logger.getLogger(VariableNamesScriptTest.class);

  private ZApplication application;


  protected void setUp() throws Exception
  {
    super.setUp();
    ZIClassRepository classRepository = ZMavenClassRepository.create(VariableNamesScriptTest.class);
    application = ZTestApplication.create(classRepository);
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
    application = null;
  }


  public void testRootExposeRenderTrue() throws Exception
  {
    ZIWebRenderContext ctx = ZTestWebRenderContextFactory.createWebRenderContext(application
        .getRenderApplication());
    ZRenderEngine re = new ZRenderEngine(ctx);
    Root root = new Root();
    String s = re.render(root);
    assertTrue(s, s.indexOf("root.js") >= 0);
    int idx1 = s.indexOf("maps.google.com");
    assertTrue(s, idx1 >= 0);
    int idx2 = s.indexOf("maps.google.com", idx1 + 1);
    assertTrue(s, idx2 < 0);
  }
}
