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
package org.ztemplates.web.ui.form;

import org.ztemplates.property.ZOperation;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZTemplates;

@ZRenderer(ZVelocityRenderer.class)
public class ZFormSubmit
{
  private final String inputId;

  private String cssId;

  private final String formId;

  private final String operationName;

  private final String allowedValue;

  private String htmlAttributes;


  public ZFormSubmit(String formId, final ZOperation prop)
  {
    ZIRenderService rs = ZTemplates.getRenderService();
    inputId = rs.createJavaScriptId();
    cssId = rs.getCssId(getClass());
    this.formId = formId;
    operationName = prop.getName();
    allowedValue = prop.getAllowedValue();
  }


  @ZExpose
  public String getInputId()
  {
    return inputId;
  }


  @ZExpose
  public String getCssId()
  {
    return cssId;
  }


  public void setCssId(String cssId)
  {
    this.cssId = cssId;
  }


  @ZExpose
  public String getFormId()
  {
    return formId;
  }


  @ZExpose
  public String getOperationName()
  {
    return operationName;
  }


  @ZExpose
  public String getAllowedValue()
  {
    return allowedValue;
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

}
