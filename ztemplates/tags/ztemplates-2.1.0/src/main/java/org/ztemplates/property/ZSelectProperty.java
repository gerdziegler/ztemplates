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
package org.ztemplates.property;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.json.ZExposeJson;

public abstract class ZSelectProperty<T> extends ZProperty<T>
{
  private List<T> allowedValues = new ArrayList<T>();


  public ZSelectProperty()
  {
  }


  public abstract String computeDisplayValue(T value);


  @ZExposeJson
  public String getDisplayValue() throws Exception
  {
    return computeDisplayValue(getValue());
  }


  @ZExposeJson
  public List<String> getAllowedDisplayValues()
  {
    List<String> ret = new ArrayList<String>();
    for (T t : allowedValues)
    {
      ret.add(computeDisplayValue(t));
    }
    return ret;
  }


  @ZExposeJson
  public List<String> getAllowedStringValues()
  {
    List<String> ret = new ArrayList<String>();
    for (T t : allowedValues)
    {
      ret.add(format(t));
    }
    return ret;
  }


  public List<T> getAllowedValues()
  {
    return allowedValues;
  }


  public void setAllowedValues(List<T> selectFrom)
  {
    this.allowedValues = selectFrom;
  }
}
