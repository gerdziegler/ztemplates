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

import org.jgrapht.graph.DefaultEdge;
import org.ztemplates.property.ZProperty;

class ZDependencyEdge extends DefaultEdge
{
  private final ZProperty source;

  private final ZProperty target;

  private final ZIValueUpdater valueUpdater;


  public ZDependencyEdge(final ZProperty source,
      final ZProperty target,
      final ZIValueUpdater valueUpdater)
  {
    super();
    this.source = source;
    this.target = target;
    this.valueUpdater = valueUpdater;
  }


  public ZProperty getSource()
  {
    return source;
  }


  public ZProperty getTarget()
  {
    return target;
  }


  public ZIValueUpdater getValueUpdater()
  {
    return valueUpdater;
  }


  @Override
  public String toString()
  {
    return source.getName() + " --> " + target.getName();
  }
}
