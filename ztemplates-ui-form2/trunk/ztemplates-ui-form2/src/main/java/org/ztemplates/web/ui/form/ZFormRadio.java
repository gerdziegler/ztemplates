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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ztemplates.property.ZSelectProperty;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZTemplates;

@ZRenderer(ZVelocityRenderer.class)
public final class ZFormRadio<T>
{
  private Map<T, String> idMap = new HashMap<T, String>();

  private boolean vertical = false;

  private String cssId;

  private final String id;

  private final String formId;

  private final String propertyName;

  private final List<ZFormRadioItem> items = new ArrayList<ZFormRadioItem>();

  private String htmlAttributes;


  public ZFormRadio(String formId, final ZSelectProperty<T> prop)
  {
    ZIRenderService rs = ZTemplates.getRenderService();
    id = rs.createJavaScriptId();
    cssId = rs.getCssId(getClass());
    this.formId = formId;
    propertyName = prop.getName();

    for (int i = 0; i < prop.getAllowedValues().size(); i++)
    {
      T t = prop.getAllowedValues().get(i);
      String id = this.id + "RB" + i;
      String key = prop.format(t);
      String value = prop.computeDisplayValue(t);
      boolean selected = key.equals(prop.getStringValue());
      ZFormRadioItem item = new ZFormRadioItem(id, key, value, selected);
      items.add(item);
    }
  }


  @ZExpose
  public List<ZFormRadioItem> getItems()
  {
    return items;
  }


  @ZExpose
  public boolean isVertical()
  {
    return vertical;
  }


  public void setVertical(boolean vertical)
  {
    this.vertical = vertical;
  }


  @ZExpose
  public String getFormId()
  {
    return formId;
  }


  @ZExpose
  public String getPropertyName()
  {
    return propertyName;
  }


  @ZExpose
  public String getCssId()
  {
    return cssId;
  }


  @ZExpose
  public String getId()
  {
    return id;
  }


  public Map<T, String> getIdMap()
  {
    return idMap;
  }


  public void setCssId(String cssId)
  {
    this.cssId = cssId;
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
