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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ztemplates.formatter.ZIFormatter;
import org.ztemplates.property.ZProperty;
import org.ztemplates.property.ZSelectProperty;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(ZVelocityRenderer.class)
public final class ZFormSelect extends ZPropertyHtml
{
  static final Logger log = Logger.getLogger(ZFormSelect.class);

  @ZExpose
  int size = 1;

  @ZExpose
  final List<ZFormSelectOption> options = new ArrayList<ZFormSelectOption>();


  public <T> ZFormSelect(final ZSelectProperty<T> prop,
      ZIFormatter<T> formatter)
  {
    super(computeId(prop), prop);
    init(prop, prop.getAllowedValues(), formatter);
    size = 1;
  }


  public <T> ZFormSelect(String id,
      final ZSelectProperty<T> prop,
      ZIFormatter<T> formatter,
      int size)
  {
    super(id, prop);
    init(prop, prop.getAllowedValues(), formatter);
    this.size = size;
  }


  public <T> ZFormSelect(
        final ZProperty<T> prop,
        List<T> allowedValues,
        ZIFormatter<T> formatter)
  {
    this(computeId(prop), prop, allowedValues, formatter);
  }


  public <T> ZFormSelect(
        String id,
        ZProperty<T> prop,
        List<T> allowedValues,
        ZIFormatter<T> formatter)
  {
    super(id, prop);
    init(prop, allowedValues, formatter);
  }


  private <T> void init(ZProperty<T> prop,
      List<T> allowedValues,
      ZIFormatter<T> formatter)
  {
    String[] values = prop.getStringValues();
    for (T allowedValue : allowedValues)
    {
      String key = prop.format(allowedValue);
      String value = formatter.computeDisplayValue(allowedValue);
      boolean selected = false;
      for (String stringValue : values)
      {
        if (key == null)
        {
          selected = stringValue == null;
        }
        else
        {
          selected = stringValue != null && key.equals(stringValue);
        }
        if (selected)
        {
          break;
        }
      }
      ZFormSelectOption item = new ZFormSelectOption(key, value, selected);
      options.add(item);
    }
  }


  public int getSize()
  {
    return size;
  }


  public void setSize(int size)
  {
    this.size = size;
  }


  public List<ZFormSelectOption> getOptions()
  {
    return options;
  }

}
