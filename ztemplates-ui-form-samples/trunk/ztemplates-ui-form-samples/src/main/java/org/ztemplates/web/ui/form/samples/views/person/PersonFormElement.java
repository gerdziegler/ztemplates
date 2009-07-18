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
package org.ztemplates.web.ui.form.samples.views.person;

import java.math.BigDecimal;

import org.zdependency.ZIDependencyContext;
import org.zdependency.ZIDependencyManaged;
import org.zdependency.ZIDependencyManager;
import org.zdependency.ZIUpdateListener;
import org.ztemplates.form.ZIFormElement;
import org.ztemplates.json.ZExposeJson;
import org.ztemplates.property.ZBigDecimalProperty;
import org.ztemplates.property.ZBooleanProperty;
import org.ztemplates.property.ZError;
import org.ztemplates.property.ZProperty;
import org.ztemplates.property.ZStringProperty;
import org.ztemplates.property.validator.ZRegexStringValidator;
import org.ztemplates.web.ui.form.samples.id.GenderId;
import org.ztemplates.web.ui.form.samples.properties.CheckboxProperty;
import org.ztemplates.web.ui.form.samples.properties.DateProperty;
import org.ztemplates.web.ui.form.samples.properties.GenderIdSelectProperty;
import org.ztemplates.web.ui.form.samples.properties.NameProperty;
import org.ztemplates.web.ui.form.samples.properties.PasswordProperty;

public class PersonFormElement implements ZIFormElement<PersonFormData>, ZIDependencyManaged<ZProperty>
{
  private final CheckboxProperty enabled = new CheckboxProperty();

  private final ZStringProperty occupation = new ZStringProperty("Occupation");

  private final NameProperty name = new NameProperty("Name");

  private final NameProperty surname = new NameProperty("Prename");

  private final GenderIdSelectProperty gender = new GenderIdSelectProperty("Gender");

  private final DateProperty dateOfBirth = new DateProperty("Date of birth", "dd.MM.yyyy")
  {
    private String MESSAGE = "Date format: DD.MM.YYYY";
    {
      getStringValidators().add(new ZRegexStringValidator("^[0-3]?\\d\\.[01]?\\d\\.[12]\\d{3}$",
          MESSAGE));
    }
  };

  private final PasswordProperty password = new PasswordProperty("Password");

  private final ZStringProperty text = new ZStringProperty("Text");

  private final ZBigDecimalProperty taxRate = new ZBigDecimalProperty("Tax rate")
  {
    @Override
    public ZError validate() throws Exception
    {
      ZError err = super.validate();
      if (err != null)
      {
        return err;
      }
      BigDecimal value = getValue();
      BigDecimal max = new BigDecimal("10");
      if (value != null && value.compareTo(max) > 0)
      {
        return new ZError("tax rate must be below 10% (he he).");
      }
      return null;
    }
  };

  private final ZBooleanProperty married = new ZBooleanProperty("Married");

  private final ZBooleanProperty noAjax = new ZBooleanProperty("No Ajax");


  public PersonFormElement()
  {
    enabled.setValue(true);
    name.setRequired(true);

//    enabled.setAjaxReloadState(true);
//    occupation.setAjaxReloadState(true);
//    surname.setAjaxReloadState(true);
//    gender.setAjaxReloadState(true);
//    dateOfBirth.setAjaxReloadState(false);
//    name.setAjaxReloadState(true);
//    password.setAjaxReloadState(true);
//    taxRate.setAjaxReloadState(true);
//    married.setAjaxReloadState(false);
  }


  @Override
  public void declareDependencies(ZIDependencyManager<ZProperty> dependencies)
  {
    dependencies.addDependency(name, surname);
    dependencies.addUpdateListener(surname, new ZIUpdateListener<ZProperty>()
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
    dependencies.addDependency(name, gender);

    dependencies.addUpdateListener(gender, new ZIUpdateListener<ZProperty>()
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
  }


  @Override
  public void update() throws Exception
  {
    if ("m".equals(name.getStringValue()))
    {
      gender.setWriteable(false);
    }
    else
    {
      gender.setWriteable(true);
    }
  }


  public PersonFormData getValue() throws Exception
  {
    return new PersonFormData(enabled.getValue(), name.getValue(), surname.getValue(), gender
        .getValue(), dateOfBirth.getValue(), occupation.getValue(), password.getValue(), text
        .getValue(), taxRate.getValue(), married.getValue());
  }


  public void setValue(PersonFormData data)
  {
    enabled.setValue(data.getEnabled());
    name.setValue(data.getName());
    surname.setValue(data.getSurname());
    gender.setValue(data.getGender());
    dateOfBirth.setValue(data.getDateOfBirth());
    occupation.setValue(data.getOccupation());
    password.setValue(data.getPassword());
    text.setValue(data.getText());
    taxRate.setValue(data.getTaxRate());
    married.setValue(data.getMarried());
  }


  @ZExposeJson
  public CheckboxProperty getEnabled()
  {
    return enabled;
  }


  @ZExposeJson
  public DateProperty getDateOfBirth()
  {
    return dateOfBirth;
  }


  @ZExposeJson
  public GenderIdSelectProperty getGender()
  {
    return gender;
  }


  @ZExposeJson
  public NameProperty getName()
  {
    return name;
  }


  @ZExposeJson
  public NameProperty getSurname()
  {
    return surname;
  }


  @ZExposeJson
  public ZStringProperty getOccupation()
  {
    return occupation;
  }


  @ZExposeJson
  public PasswordProperty getPassword()
  {
    return password;
  }


  @ZExposeJson
  public ZStringProperty getText()
  {
    return text;
  }


  @ZExposeJson
  public ZBigDecimalProperty getTaxRate()
  {
    return taxRate;
  }


  @ZExposeJson
  public ZBooleanProperty getMarried()
  {
    return married;
  }


  @ZExposeJson
  public ZBooleanProperty getNoAjax()
  {
    return noAjax;
  }
}
