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
package org.ztemplates.web.ui.form.samples.properties;

import org.ztemplates.property.ZSelectProperty;
import org.ztemplates.web.ui.form.samples.id.CountryId;

public class CountryIdSelectProperty extends ZSelectProperty<CountryId>
{
  public CountryIdSelectProperty(String label)
  {
    super(label);
  }


  @Override
  public String format(CountryId id)
  {
    return id.getRaw();
  }


  @Override
  public CountryId parse(String s) throws Exception
  {
    return CountryId.create(s);
  }


  @Override
  public String computeDisplayValue(CountryId id)
  {
    if (id == null || CountryId.NONE.equals(id))
    {
      return "";
    }
    return id.getRaw();
  }
}
