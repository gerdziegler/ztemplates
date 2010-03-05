/*
 * Copyright 2010 Gerd Ziegler (www.gerdziegler.de)
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
package org.ztemplates.web.ui.form;

import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZTemplates;

@ZRenderer(ZVelocityRenderer.class)
public final class ZFormFile
{
  private final String id;

  private String cssClass;

  private final String name;

  private final String value;

  private final String accept;

  private String htmlAttributes;


  public ZFormFile(String name, String value, String accept)
  {
    ZIRenderService rs = ZTemplates.getRenderService();
    this.id = rs.createJavaScriptId();
    this.name = name;
    this.value = value;
    this.accept = accept;
  }


  public ZFormFile(final ZProperty prop)
  {
    this(prop.getName(), prop.getStringValue(), null);
  }


  @ZExpose
  public String getId()
  {
    return id;
  }


  @ZExpose
  public String getCssClass()
  {
    return cssClass;
  }


  public void setCssClass(String cssId)
  {
    this.cssClass = cssId;
  }


  @ZExpose
  public String getName()
  {
    return name;
  }


  @ZExpose
  public String getValue()
  {
    return value;
  }


  /**
   * pass-through html attributes to be added to the generated html tag
   * 
   * @return
   */
  @ZExpose
  public String getHtmlAttributes()
  {
    return htmlAttributes;
  }


  /**
   * pass-through html attributes to be added to the generated html tag
   * 
   * @return
   */
  public void setHtmlAttributes(String attributes)
  {
    this.htmlAttributes = attributes;
  }


  @ZExpose
  public String getAccept()
  {
    return accept;
  }

}
