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
package org.ztemplates.test.render.maps;

import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.render.impl.ZIWebRenderContext;
import org.ztemplates.render.impl.ZRenderEngine;
import org.ztemplates.test.ZTestWebRenderContextFactory;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.standalone.ZTemplatesStandaloneApplication;

public class MapTestPojoTest extends TestCase
{
  static Logger log = Logger.getLogger(MapTestPojoTest.class);

  private ZApplication application;


  protected void setUp() throws Exception
  {
    super.setUp();
    ZIClassRepository classRepository = ZClassRepository.create(MapTestPojoTest.class);

    application = ZTemplatesStandaloneApplication.create(classRepository);
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
    application = null;
  }


  public void test1() throws Exception
  {
    ZIWebRenderContext ctx = ZTestWebRenderContextFactory.createWebRenderContext(application.getRenderApplication());
    ZRenderEngine re = new ZRenderEngine(ctx);
    MapTestPojo root = new MapTestPojo();
    root.articleMap.put("klo", "katze");
    //    root.articleMap.put("maus", new MapTestPojo2("hund"));
    Map<String, Object> exposed = re.getExposed(root);

    Map<String, String> articleMap = (Map<String, String>) exposed.get("articleMap");
    assertEquals("katze", articleMap.get("klo"));
    //    assertEquals("hund", articleMap.get("maus"));
  }
}
