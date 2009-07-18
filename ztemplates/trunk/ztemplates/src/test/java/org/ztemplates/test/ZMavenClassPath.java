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
 * 
 * @author www.gerdziegler.de
 */
package org.ztemplates.test;

import java.io.File;
import java.util.List;

import org.zclasspath.ZIClassPathItem;
import org.zclasspath.ZJavaClassPath;

public class ZMavenClassPath
{
  public static List<ZIClassPathItem> getItems()
  {
    File mavenTestClasses = new File("target/test-classes");
    //File mavenTestClasses = new File("src/test/resources");
    //    List<ZIClassPathItem> items = new ArrayList<ZIClassPathItem>();
    List<ZIClassPathItem> items = ZJavaClassPath.getItems();
    //    items.add(0, new ZClassPathItemFile(mavenTestClasses));
    //    System.out.println(items);
    return items;
  }
}
