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
package org.ztemplates.web.ui.form.samples.views.cascading;

import java.io.Serializable;
import java.util.List;

import org.ztemplates.web.ui.form.samples.id.ContinentId;
import org.ztemplates.web.ui.form.samples.id.CountryId;

public class CascadingFormData implements Serializable
{
  private final ContinentId continent;

  private final CountryId country;
  
  private final String city;


  public CascadingFormData(ContinentId continent, CountryId country, String city)
  {
    super();
    this.continent = continent;
    this.country = country;
    this.city = city;
  }


  public ContinentId getContinent()
  {
    return continent;
  }


  public CountryId getCountry()
  {
    return country;
  }


  public String getCity()
  {
    return city;
  }
}
