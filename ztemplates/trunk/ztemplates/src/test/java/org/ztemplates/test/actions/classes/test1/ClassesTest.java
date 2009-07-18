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
 * 20.09.2007
 * @author www.gerdziegler.de
 */
package org.ztemplates.test.actions.classes.test1;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassRepository;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.test.ZMavenClassPath;

public class ClassesTest extends TestCase
{
  static Logger log = Logger.getLogger(ClassesTest.class);


  protected void setUp() throws Exception
  {
    super.setUp();
  }


  protected void tearDown() throws Exception
  {
    super.tearDown();
  }


  public void test1() throws Exception
  {
    ZIClassRepository classRepo = ZClassRepository.create(ZMavenClassPath.getItems(),
        ClassesTest.class.getPackage().getName());
    assertTrue(classRepo.getClassesAnnotatedWith(ZMatch.class).contains(Handler1.class));
    assertTrue(classRepo.getClassesAnnotatedWith(ZMatch.class).contains(Handler2.class));
    assertEquals(2, classRepo.getClassesAnnotatedWith(ZMatch.class).size());
  }
}
