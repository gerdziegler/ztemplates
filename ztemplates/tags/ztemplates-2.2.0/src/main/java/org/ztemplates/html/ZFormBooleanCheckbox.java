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

import org.ztemplates.property.ZBooleanProperty;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(ZVelocityRenderer.class)
public final class ZFormBooleanCheckbox extends ZPropertyHtml
{
  private final String value;

  private final boolean checked;


  public ZFormBooleanCheckbox(String id, final ZBooleanProperty prop)
  {
    super(id, prop);
    this.checked = "true".equals(prop.getStringValue());
    this.value = "true";
  }


  public ZFormBooleanCheckbox(final ZBooleanProperty prop)
  {
    this(computeId(prop), prop);
  }


  @ZExpose
  public boolean isChecked()
  {
    return checked;
  }


  @ZExpose
  public String getValue()
  {
    return value;
  }
}
