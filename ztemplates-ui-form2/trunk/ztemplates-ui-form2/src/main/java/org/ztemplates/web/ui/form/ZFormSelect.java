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
import java.util.List;

import org.ztemplates.property.ZSelectProperty;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZTemplates;

@ZRenderer(ZVelocityRenderer.class)
public final class ZFormSelect
{
  private final String id;

  private final int size;

  private String cssClass;

  private final String name;

  private final List<ZFormSelectOption> options = new ArrayList<ZFormSelectOption>();

  private String htmlAttributes;


  public <T> ZFormSelect(final ZSelectProperty<T> prop)
  {
    this(prop, 1);
  }


  public <T> ZFormSelect(final ZSelectProperty<T> prop, int size)
  {
    this.size = size;
    ZIRenderService rs = ZTemplates.getRenderService();
    id = rs.createJavaScriptId();
    name = prop.getName();

    String stringValue = prop.getStringValue();
    for (T t : prop.getAllowedValues())
    {
      String key = prop.format(t);
      String value = prop.computeDisplayValue(t);
      boolean selected;
      if (key == null)
      {
        selected = stringValue == null;
      }
      else
      {
        selected = stringValue != null && key.equals(stringValue);
      }
      ZFormSelectOption item = new ZFormSelectOption(key, value, selected);
      options.add(item);
    }
  }


  @ZExpose
  public List<ZFormSelectOption> getOptions() throws Exception
  {
    return options;
  }


  @ZExpose
  public int getSize()
  {
    return size;
  }


  @ZExpose
  public String getId()
  {
    return id;
  }


  @ZExpose
  public String getName()
  {
    return name;
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
