/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
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
package org.ztemplates.examples.formprocessing.layers.active.form;

import java.util.Date;
import java.util.Set;

import org.zdependency.ZIDependencyContext;
import org.zdependency.ZIUpdateListener;
import org.zdependency.impl.ZDependencyManager;
import org.ztemplates.examples.formprocessing.layers.active.services.FormDataRespDTO;
import org.ztemplates.examples.formprocessing.layers.active.services.FormTutorialServiceLocator;
import org.ztemplates.examples.formprocessing.layers.active.services.IFormTutorialService;
import org.ztemplates.examples.formprocessing.layers.passive.ids.ContinentId;
import org.ztemplates.examples.formprocessing.layers.passive.ids.CountryId;
import org.ztemplates.examples.formprocessing.layers.passive.ids.GenderId;
import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.ContinentIdSelectProperty;
import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.CountryIdSelectProperty;
import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.DateProperty;
import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.GenderIdSelectProperty;
import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.NameProperty;
import org.ztemplates.examples.formprocessing.layers.passive.ui.views.SampleForm;
import org.ztemplates.form.ZIFormController;
import org.ztemplates.property.ZError;
import org.ztemplates.property.ZProperty;
import org.ztemplates.property.ZStringProperty;
import org.ztemplates.web.ui.form.script.ZFormScript;

public class SampleFormController implements ZIFormController
{
  private final SampleForm form;


  public SampleFormController(final SampleForm form)
  {
    this.form = form;
  }


  @Override
  public void updateRequired() throws Exception
  {
    final ContinentIdSelectProperty continent = form.getCascading().getContinent();
    final CountryIdSelectProperty country = form.getCascading().getCountry();
    final NameProperty name = form.getPerson().getName();
    final DateProperty dateFrom = form.getPerson().getDateFrom();
    final DateProperty dateTo = form.getPerson().getDateTo();

    continent.setRequired(true);
    country.setRequired(true);
    name.setRequired(true);
    dateTo.setRequired(!dateFrom.isEmpty());
  }


  @Override
  public void updateValidationState() throws Exception
  {
    final DateProperty dateFrom = form.getPerson().getDateFrom();
    final DateProperty dateTo = form.getPerson().getDateTo();

    if (!dateFrom.isEmpty() && !dateTo.isError())
    {
      if (dateFrom.getValue().after(new Date()))
      {
        dateFrom.setState(new ZError("Date cannot be in future"));
      }
    }

    if (!dateTo.isEmpty() && !dateTo.isError())
    {
      if (dateTo.getValue().after(new Date()))
      {
        dateTo.setState(new ZError("Date cannot be in future"));
      }
    }

    if (!dateFrom.isEmpty() && !dateFrom.isError() && !dateTo.isEmpty() && !dateTo.isError())
    {
      if (dateFrom.getValue().after(dateTo.getValue()))
      {
        dateFrom.setState(new ZError("Date from must be before date to"));
        dateTo.setState(new ZError("Date from must be before date to"));
      }
    }
  }


  public void updateForView() throws Exception
  {
    final IFormTutorialService service = FormTutorialServiceLocator.getService();
    form.getPerson().getEnabled().setValue(true);

    final ContinentIdSelectProperty continent = form.getCascading().getContinent();
    final CountryIdSelectProperty country = form.getCascading().getCountry();
    final ZStringProperty city = form.getCascading().getCity();
    final NameProperty surname = form.getPerson().getSurname();
    final NameProperty name = form.getPerson().getName();
    final GenderIdSelectProperty gender = form.getPerson().getGender();

    continent.getAllowedValues().addAll(service.getContinents());
    country.getAllowedValues().clear();
    country.getAllowedValues().add(CountryId.NONE);

    if (!continent.isEmpty())
    {
      ContinentId con = continent.getValue();
      country.getAllowedValues().addAll(service.getCountries(con));
    }

    city.setWriteable(!country.isEmpty());

    country.setWriteable(!continent.isEmpty());

    if ("m".equals(name.getStringValue()))
    {
      gender.setWriteable(false);
    }
    else if ("Maria".equals(surname.getStringValue()))
    {
      gender.setWriteable(false);
    }
    else if ("Marc".equals(surname.getStringValue()))
    {
      gender.setWriteable(false);
    }
    else
    {
      gender.setWriteable(true);
    }
  }


  public Set<ZProperty> getAjaxProperties()
  {
    ZDependencyManager<ZProperty> dependencyManager = createDependencyManager();
    Set<ZProperty> ret = dependencyManager.getTriggerNodes();
    ret.add(form.getPerson().getDateFrom());
    ret.add(form.getPerson().getDateTo());
    return ret;
  }


  public void loadInitialData() throws Exception
  {
    //get the service layer
    final IFormTutorialService service = FormTutorialServiceLocator.getService();
    FormDataRespDTO data = service.getInitialFormData();
    //copy data into form here

    //  public SampleFormData getValue() throws Exception
    //  {
    //    return new SampleFormData(person.getValue(), cascading.getValue());
    //  }
    //
    //
    //  public void setValue(SampleFormData t)
    //  {
    //    person.setValue(t.getPerson());
    //    cascading.setValue(t.getCascading());
    //  }
    //  public PersonFormData getValue() throws Exception
    //  {
    //    return new PersonFormData(enabled.getValue(), name.getValue(), surname.getValue(), gender
    //        .getValue(), dateFrom.getValue(), occupation.getValue(), password.getValue(), text
    //        .getValue(), taxRate.getValue(), married.getValue());
    //  }
    //
    //
    //  public void setValue(PersonFormData data)
    //  {
    //    enabled.setValue(data.getEnabled());
    //    name.setValue(data.getName());
    //    surname.setValue(data.getSurname());
    //    gender.setValue(data.getGender());
    //    dateFrom.setValue(data.getDateOfBirth());
    //    occupation.setValue(data.getOccupation());
    //    password.setValue(data.getPassword());
    //    text.setValue(data.getText());
    //    taxRate.setValue(data.getTaxRate());
    //    married.setValue(data.getMarried());
    //  }
    //  public CascadingFormData getValue() throws Exception
    //  {
    //    ContinentId continent = this.continent.getValue();
    //    CountryId country = this.country.getValue();
    //    String city = this.city.getValue();
    //    return new CascadingFormData(continent, country, city);
    //  }
    //
    //
    //  public void setValue(CascadingFormData data)
    //  {
    //    continent.setValue(data.getContinent());
    //    country.setValue(data.getCountry());
    //    city.setValue(data.getCity());
    //  }
  }


  @Override
  public void updateValues() throws Exception
  {
    final NameProperty surname = form.getPerson().getSurname();
    final NameProperty name = form.getPerson().getName();
    final GenderIdSelectProperty gender = form.getPerson().getGender();
    //apply invariants, means constraints that are always true
    if ("Maria".equals(surname.getStringValue()))
    {
      gender.setValue(GenderId.FEMALE);
    }
    else if ("Marc".equals(surname.getStringValue()))
    {
      gender.setValue(GenderId.MALE);
    }
  }


  public void processDependencies() throws Exception
  {
    Set<ZProperty> changed = ZFormScript.computeChangedFormProperties(form);
    ZDependencyManager<ZProperty> dependencyManager = createDependencyManager();
    dependencyManager.update(changed);
  }


  private ZDependencyManager<ZProperty> createDependencyManager()
  {
    ZDependencyManager<ZProperty> dependencyManager = new ZDependencyManager<ZProperty>();

    final ContinentIdSelectProperty continent = form.getCascading().getContinent();
    final CountryIdSelectProperty country = form.getCascading().getCountry();
    final ZStringProperty city = form.getCascading().getCity();
    final NameProperty surname = form.getPerson().getSurname();
    final NameProperty name = form.getPerson().getName();
    final GenderIdSelectProperty gender = form.getPerson().getGender();
    final ZStringProperty occupation = form.getPerson().getOccupation();

    dependencyManager.addDependency(surname, continent);
    dependencyManager.addDependency(continent, country);
    dependencyManager.addDependency(country, city);
    dependencyManager.addDependency(name, surname);
    dependencyManager.addDependency(name, gender);
    dependencyManager.addDependency(gender, occupation);

    dependencyManager.addUpdateListener(form.getCascading().getContinent(),
        new ZIUpdateListener<ZProperty>()
        {
          @Override
          public boolean onUpdate(ZIDependencyContext<ZProperty> ctx) throws Exception
          {
            if ("Maria".equals(surname.getStringValue()))
            {
              return continent.setValue(ContinentId.ASIA);
            }
            else if ("Marc".equals(form.getPerson().getSurname().getStringValue()))
            {
              return continent.setValue(ContinentId.EUROPE);
            }
            else
            {
              return false;
            }
          }
        });

    dependencyManager.addUpdateListener(country, new ZIUpdateListener<ZProperty>()
    {
      @Override
      public boolean onUpdate(ZIDependencyContext<ZProperty> ctx) throws Exception
      {
        return country.setValue(null);
      }
    });

    dependencyManager.addUpdateListener(city, new ZIUpdateListener<ZProperty>()
    {
      @Override
      public boolean onUpdate(ZIDependencyContext<ZProperty> ctx) throws Exception
      {
        return city.setValue(null);
      }
    });

    dependencyManager.addUpdateListener(surname, new ZIUpdateListener<ZProperty>()
    {
      @Override
      public boolean onUpdate(ZIDependencyContext<ZProperty> ctx) throws Exception
      {
        if ("m".equals(name.getStringValue()))
        {
          return surname.setValue("Marc");
        }
        else if ("f".equals(name.getStringValue()))
        {
          return surname.setValue("Maria");
        }
        else
        {
          return false;
        }
      }
    });

    dependencyManager.addUpdateListener(gender, new ZIUpdateListener<ZProperty>()
    {
      @Override
      public boolean onUpdate(ZIDependencyContext<ZProperty> ctx) throws Exception
      {
        if ("m".equals(name.getStringValue()))
        {
          return gender.setValue(GenderId.MALE);
        }
        else if ("f".equals(name.getStringValue()))
        {
          return gender.setValue(GenderId.FEMALE);
        }
        else
        {
          return false;
        }
      }
    });

    dependencyManager.addUpdateListener(occupation, new ZIUpdateListener<ZProperty>()
    {
      @Override
      public boolean onUpdate(ZIDependencyContext<ZProperty> ctx) throws Exception
      {
        if (GenderId.MALE.equals(gender.getValue()))
        {
          return occupation.setValue("pilot");
        }
        else if (GenderId.FEMALE.equals(gender.getValue()))
        {
          return occupation.setValue("actress");
        }
        else
        {
          return false;
        }
      }
    });

    return dependencyManager;
  }

}
