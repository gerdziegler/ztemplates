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
package org.ztemplates.examples.formprocessing.layers.passive.ui.properties;

import org.ztemplates.examples.formprocessing.layers.passive.ids.ContinentId;
import org.ztemplates.property.ZSelectProperty;

public class ContinentIdSelectProperty extends ZSelectProperty<ContinentId>
{
  public ContinentIdSelectProperty(String label)
  {
    super(label);
  }


  @Override
  public String format(ContinentId id)
  {
    return id.getRaw();
  }


  @Override
  public ContinentId parse(String s) throws Exception
  {
    return ContinentId.create(s);
  }


  @Override
  public String computeDisplayValue(ContinentId id)
  {
    if (id == null)
    {
      return "";
    }
    if (id.equals(ContinentId.NONE))
    {
      return "";
    }
    if (id.equals(ContinentId.AFRICA))
    {
      return "Africa";
    }
    if (id.equals(ContinentId.AMERICA))
    {
      return "America";
    }
    if (id.equals(ContinentId.ASIA))
    {
      return "Asia";
    }
    if (id.equals(ContinentId.AUSTRALIA))
    {
      return "Australia";
    }
    if (id.equals(ContinentId.EUROPE))
    {
      return "Europe";
    }

    return "unknown continent: " + id;
  }
}
