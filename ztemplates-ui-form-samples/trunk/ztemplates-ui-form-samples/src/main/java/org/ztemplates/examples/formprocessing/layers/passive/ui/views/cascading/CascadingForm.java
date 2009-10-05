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
package org.ztemplates.examples.formprocessing.layers.passive.ui.views.cascading;

import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.ContinentIdSelectProperty;
import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.CountryIdSelectProperty;
import org.ztemplates.form.ZIForm;
import org.ztemplates.property.ZStringProperty;

public class CascadingForm implements ZIForm
{
  private final ContinentIdSelectProperty continent = new ContinentIdSelectProperty("Continent");

  private final CountryIdSelectProperty country = new CountryIdSelectProperty("Country");

  private final ZStringProperty city = new ZStringProperty();


  public ContinentIdSelectProperty getContinent()
  {
    return continent;
  }


  public CountryIdSelectProperty getCountry()
  {
    return country;
  }


  public ZStringProperty getCity()
  {
    return city;
  }
}
