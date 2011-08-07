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
 * 22.09.2010
 * @author www.gerdziegler.de
 */
package org.ztemplates.test.render.methodrepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.render.impl.ZExposedMethodRepository;
import org.ztemplates.render.impl.ZIExposedMethodRepository;
import org.ztemplates.render.impl.ZIExposedValue;
import org.ztemplates.test.ZTestApplication;

public class ZExposedMethodRepositoryTest extends TestCase
{
  static Logger log = Logger.getLogger(ZExposedMethodRepositoryTest.class);


  // private ZApplication application;

  // protected void setUp() throws Exception
  // {
  // super.setUp();
  // ZIClassRepository classRepository =
  // ZClassRepository.create(ZExposedMethodRepositoryTest.class);
  // application = ZTestApplication.create(classRepository);
  // }
  //
  //
  // protected void tearDown() throws Exception
  // {
  // super.tearDown();
  // application = null;
  // }

  public void testRootExposeRenderTrue() throws Exception
  {
    ZIClassRepository classRepo = ZClassRepository.create(Pojo1.class.getPackage().getName());
    ZIExposedMethodRepository repo = new ZExposedMethodRepository(ZTestApplication.create(classRepo).getRenderApplication().getApplicationContext());
    List<ZIExposedValue> values = repo.getExposedValues(Pojo1.class);
    Map<String, ZIExposedValue> map = new HashMap<String, ZIExposedValue>();
    for (ZIExposedValue val : values)
    {
      map.put(val.getName(), val);
    }
    assertEquals(5, map.size());
    assertTrue(map.containsKey("prop1"));
    assertFalse(map.containsKey("prop2"));
    assertTrue(map.containsKey("prop3"));
    assertTrue(map.containsKey("prop4"));
    assertTrue(map.containsKey("prop5"));
    assertTrue(map.containsKey("prop6"));
    Pojo1 pojo = new Pojo1();
    ZIExposedValue exp6 = map.get("prop6");
    assertEquals("prop6FromField", exp6.getValue(pojo));
  }
}
