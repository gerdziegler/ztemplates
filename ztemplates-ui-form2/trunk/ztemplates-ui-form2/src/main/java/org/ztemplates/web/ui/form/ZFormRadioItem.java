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

public class ZFormRadioItem
{
  private final String id;

  private final String key;

  private final String value;

  private final boolean selected;


  public ZFormRadioItem(String id, final String key, final String value, boolean selected)
  {
    super();
    this.id = id;
    this.key = key;
    this.value = value;
    this.selected = selected;
  }


  public String getKey()
  {
    return key;
  }


  public String getValue()
  {
    return value;
  }


  public boolean isSelected()
  {
    return selected;
  }


  public String getId()
  {
    return id;
  }
}
