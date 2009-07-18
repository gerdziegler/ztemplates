package org.ztemplates.test.render.script.cycle;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.render.impl.ZIRenderContext;
import org.ztemplates.render.impl.ZRenderEngine;
import org.ztemplates.render.velocity.ZVelocityRendererFactory;
import org.ztemplates.test.ZTestApplication;
import org.ztemplates.web.application.ZApplication;

public class ScriptCycleTest extends TestCase
{
  static Logger log = Logger.getLogger(ScriptCycleTest.class);

  private ZApplication application;


  protected void setUp() throws Exception
  {
    super.setUp();
    ZIClassRepository classRepository = ZClassRepository.create(ScriptCycleTest.class);
    application = ZTestApplication.create(classRepository);
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
    application = null;
  }


  public void test1() throws Exception
  {
    ZIRenderContext ctx = ZVelocityRendererFactory.createStandaloneRenderEngine(application
        .getRenderApplication());
    ZRenderEngine re = new ZRenderEngine();
  }
}
