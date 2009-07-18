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
 * 
 * @author www.gerdziegler.de
 */
package org.ztemplates.property.impl;

class ZDependencyManager
{
  //  public static void addDependency(ZProperty source, ZProperty target, ZIValueUpdater updater)
  //      throws RuntimeException
  //  {
  //    ZDependencyEdge edge = new ZDependencyEdge(source, target, updater);
  //    if (source.getDependenciesOut().contains(edge))
  //    {
  //      throw new RuntimeException("dependency already defined " + edge);
  //    }
  //    source.getDependenciesOut().add(edge);
  //    if (target.getDependenciesIn().contains(edge))
  //    {
  //      throw new RuntimeException("dependency already defined " + edge);
  //    }
  //    target.getDependenciesIn().add(edge);
  //  }
  //
  //
  //  public static void addDependency(ZProperty[] source, ZProperty target, ZIValueUpdater updater)
  //      throws RuntimeException
  //  {
  //    for (ZProperty prop : source)
  //    {
  //      ZDependencyManager.addDependency(prop, target, updater);
  //    }
  //  }

  /**
   * add dependency from source to target with default updater:
   * target.updateValue
   * 
   * @param source
   * @param target
   * @throws RuntimeException
   */
  //  public static void addDependency(ZProperty[] source, final ZProperty target)
  //      throws RuntimeException
  //  {
  //    ZIValueUpdater updater = new ZIValueUpdater()
  //    {
  //      public void updateValue() throws Exception
  //      {
  ////        target.updateValue();
  //      }
  //    };
  //
  //    for (ZProperty prop : source)
  //    {
  //      ZDependencyManager.addDependency(prop, target, updater);
  //    }
  //  }
  //
  //
  //  public static void addDependency(ZProperty source, final ZProperty target)
  //      throws RuntimeException
  //  {
  //    ZIValueUpdater updater = new ZIValueUpdater()
  //    {
  //      public void updateValue() throws Exception
  //      {
  ////        target.updateValue();
  //      }
  //    };
  //
  //    ZDependencyManager.addDependency(source, target, updater);
  //  }
}
