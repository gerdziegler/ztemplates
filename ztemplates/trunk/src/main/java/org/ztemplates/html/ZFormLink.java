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

import org.ztemplates.property.ZOperation;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(ZVelocityRenderer.class)
public final class ZFormLink extends ZPropertyHtml
{
  @ZExpose
  final String value;

  @ZExpose
  String href = "javascript:void(0);";


  public ZFormLink(String id,
      final ZOperation prop,
      String text)
  {
    super(id, prop);
    this.value = text;
  }


  public ZFormLink(final ZOperation prop,
      String text)
  {
    this(computeId(prop), prop, text);
  }


  public ZFormLink(String id,
      final ZOperation prop)
  {
    super(id, prop);
    this.value = prop.getAllowedValue();
  }


  public ZFormLink(final ZOperation prop)
  {
    this(computeId(prop), prop);
  }


  public String getHref()
  {
    return href;
  }


  public void setHref(String href)
  {
    this.href = href;
  }
}