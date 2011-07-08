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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ztemplates.property.ZSelectProperty;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(ZVelocityRenderer.class)
public final class ZFormRadio<T> extends ZPropertyHtml
{
  private Map<T, String> idMap = new HashMap<T, String>();

  private boolean vertical = false;

  private final List<ZFormRadioItem> items = new ArrayList<ZFormRadioItem>();

  @ZExpose
  public ZFormRadioItem selectedItem;


  public ZFormRadio(String id,
      ZSelectProperty<T> prop)
  {
    super(id, prop);

    final String myId = getId();
    for (int i = 0; i < prop.getAllowedValues().size(); i++)
    {
      T t = prop.getAllowedValues().get(i);
      String itemId = myId + "RB" + i;
      String key = prop.format(t);
      String value = prop.computeDisplayValue(t);
      boolean selected = key.equals(prop.getStringValue());
      ZFormRadioItem item = new ZFormRadioItem(itemId, key, value, selected);
      if (selected)
      {
        selectedItem = item;
      }
      items.add(item);
    }
  }


  public ZFormRadio(final ZSelectProperty<T> prop)
  {
    this(computeId(prop), prop);
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


  public Map<T, String> getIdMap()
  {
    return idMap;
  }
}
