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
import org.ztemplates.web.ZTemplates;

@ZRenderer(ZVelocityRenderer.class)
public final class ZFormTextArea extends ZPropertyHtml
{
  private final String value;

  private Integer rows;

  private Integer cols;


  public ZFormTextArea(String id, final ZProperty prop)
  {
    super(id, prop);
    this.value = prop.getStringValue();
  }


  public ZFormTextArea(final ZProperty prop)
  {
    this(ZTemplates.getRenderService().createJavaScriptId(), prop);
  }


  @ZExpose
  public String getValue()
  {
    return value;
  }


  @ZExpose
  public Integer getRows()
  {
    return rows;
  }


  public void setRows(Integer rows)
  {
    this.rows = rows;
  }


  @ZExpose
  public Integer getCols()
  {
    return cols;
  }


  public void setCols(Integer cols)
  {
    this.cols = cols;
  }
}
