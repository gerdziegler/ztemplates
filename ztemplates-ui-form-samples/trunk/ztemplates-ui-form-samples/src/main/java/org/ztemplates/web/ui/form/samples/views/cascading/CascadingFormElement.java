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

import org.zdependency.ZIDependencyContext;
import org.zdependency.ZIDependencyManaged;
import org.zdependency.ZIDependencyManager;
import org.zdependency.ZIUpdateListener;
import org.ztemplates.form.ZIFormElement;
import org.ztemplates.json.ZExposeJson;
import org.ztemplates.property.ZProperty;
import org.ztemplates.property.ZStringProperty;
import org.ztemplates.web.ui.form.samples.id.ContinentId;
import org.ztemplates.web.ui.form.samples.id.CountryId;
import org.ztemplates.web.ui.form.samples.properties.ContinentIdSelectProperty;
import org.ztemplates.web.ui.form.samples.properties.CountryIdSelectProperty;

public class CascadingFormElement implements ZIFormElement<CascadingFormData>,
    ZIDependencyManaged<ZProperty>
{
  private final CascadingFormElementContext context;

  private final ContinentIdSelectProperty continent = new ContinentIdSelectProperty("Continent")
  {
    {
      getAllowedValues().add(ContinentId.NONE);
      getAllowedValues().add(ContinentId.EUROPE);
      getAllowedValues().add(ContinentId.AMERICA);
      getAllowedValues().add(ContinentId.ASIA);
      getAllowedValues().add(ContinentId.AUSTRALIA);
      getAllowedValues().add(ContinentId.AFRICA);
    }
  };

  private final CountryIdSelectProperty country = new CountryIdSelectProperty("Country");

  private final ZStringProperty city = new ZStringProperty();


  public CascadingFormElement(CascadingFormElementContext context)
  {
    this.context = context;
    continent.setRequired(true);
    country.setRequired(true);
    //    continent.setAjaxReloadState(true);
    //    city.setAjaxReloadState(true);
    //    country.setAjaxReloadState(true);
  }


  @Override
  public void update() throws Exception
  {
    city.setWriteable(!country.isEmpty());
    country.setWriteable(!continent.isEmpty());

    country.getAllowedValues().clear();
    country.getAllowedValues().add(CountryId.NONE);
    if (!continent.isEmpty())
    {
      ContinentId con = continent.getValue();
      country.getAllowedValues().addAll(context.getCountries(con));
    }
  }


  @Override
  public void declareDependencies(ZIDependencyManager<ZProperty> dependencies)
  {
    dependencies.addDependency(continent, country);
    dependencies.addUpdateListener(country, new ZIUpdateListener<ZProperty>()
    {
      @Override
      public boolean onUpdate(ZIDependencyContext<ZProperty> ctx) throws Exception
      {
        return country.setValue(null);
      }
    });
    dependencies.addDependency(country, city);
    dependencies.addUpdateListener(city, new ZIUpdateListener<ZProperty>()
    {
      @Override
      public boolean onUpdate(ZIDependencyContext<ZProperty> ctx) throws Exception
      {
        return city.setValue(null);
      }
    });
  }


  public CascadingFormData getValue() throws Exception
  {
    return new CascadingFormData(continent.getValue(), country.getValue(), city.getValue());
  }


  public void setValue(CascadingFormData data)
  {
    continent.setValue(data.getContinent());
    country.setValue(data.getCountry());
    city.setValue(data.getCity());
  }


  @ZExposeJson
  public ContinentIdSelectProperty getContinent()
  {
    return continent;
  }


  @ZExposeJson
  public CountryIdSelectProperty getCountry()
  {
    return country;
  }


  @ZExposeJson
  public ZStringProperty getCity()
  {
    return city;
  }
}
