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

import org.ztemplates.examples.formprocessing.layers.passive.ids.GenderId;
import org.ztemplates.property.ZSelectProperty;

public class GenderIdSelectProperty extends ZSelectProperty<GenderId>
{
  private static final String MALE = "m";

  private static final String FEMALE = "f";


  public GenderIdSelectProperty(String label)
  {
    super("label");
    getAllowedValues().add(GenderId.FEMALE);
    getAllowedValues().add(GenderId.MALE);
  }


  @Override
  public String format(GenderId id)
  {
    if (id == null)
    {
      return "";
    }
    if (GenderId.MALE.equals(id))
    {
      return MALE;
    }
    if (GenderId.FEMALE.equals(id))
    {
      return FEMALE;
    }
    return "unknown gender: " + id;
  }


  @Override
  public GenderId parse(String s) throws Exception
  {
    if (s == null || s.length() == 0)
    {
      return null;
    }
    if (MALE.equals(s))
    {
      return GenderId.MALE;
    }
    if (FEMALE.equals(s))
    {
      return GenderId.FEMALE;
    }
    throw new Exception("unknown gender: " + s);
  }


  @Override
  public String computeDisplayValue(GenderId id)
  {
    if (id == null)
    {
      return "";
    }
    if (GenderId.MALE.equals(id))
    {
      return "male";
    }
    if (GenderId.FEMALE.equals(id))
    {
      return "female";
    }
    return "unknown gender: " + id;
  }
}
