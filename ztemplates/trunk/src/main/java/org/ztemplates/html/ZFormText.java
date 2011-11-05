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
package org.ztemplates.html;

import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(ZVelocityRenderer.class)
public final class ZFormText extends ZPropertyHtml
{
  @ZExpose
  int idx = 0;


  /**
   * 
   * @param id
   * @param prop
   * @param idx the index to assign to this text field for multivalued properties
   */
  public ZFormText(String id,
      final ZProperty prop,
      int idx)
  {
    super(id, prop);
    this.idx = idx;
  }


  public ZFormText(String id,
      final ZProperty prop)
  {
    this(id, prop, 0);
  }


  public ZFormText(final ZProperty prop)
  {
    this(computeId(prop, 0), prop, 0);
  }


  public ZFormText(final ZProperty prop,
      int idx)
  {
    this(computeId(prop, idx), prop, idx);
  }


  @ZExpose
  public String getValue()
  {
    String[] values = getProperty().getStringValues();
    if (idx > values.length - 1)
    {
      return null;
    }
    return values[idx];
  }
}
