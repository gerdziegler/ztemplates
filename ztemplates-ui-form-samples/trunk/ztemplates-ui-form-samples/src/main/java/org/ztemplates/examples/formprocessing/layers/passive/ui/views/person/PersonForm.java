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
package org.ztemplates.examples.formprocessing.layers.passive.ui.views.person;

import java.math.BigDecimal;

import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.CheckboxProperty;
import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.DateProperty;
import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.GenderIdSelectProperty;
import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.NameProperty;
import org.ztemplates.examples.formprocessing.layers.passive.ui.properties.PasswordProperty;
import org.ztemplates.form.ZIFormModel;
import org.ztemplates.property.ZBigDecimalProperty;
import org.ztemplates.property.ZBooleanProperty;
import org.ztemplates.property.ZError;
import org.ztemplates.property.ZStringProperty;
import org.ztemplates.property.validator.ZRegexStringValidator;

public class PersonForm implements ZIFormModel
{
  private final CheckboxProperty enabled = new CheckboxProperty();

  private final ZStringProperty occupation = new ZStringProperty("Occupation");

  private final NameProperty name = new NameProperty("Name");

  private final NameProperty surname = new NameProperty("Prename");

  private final GenderIdSelectProperty gender = new GenderIdSelectProperty("Gender");

  private final DateProperty dateFrom = new DateProperty("Date from", "dd.MM.yyyy")
  {
    private final static String MESSAGE = "Date format: DD.MM.YYYY";
    {
      getStringValidators().add(new ZRegexStringValidator("^[0-3]?\\d\\.[01]?\\d\\.[12]\\d{3}$",
          MESSAGE));
    }
  };

  private final DateProperty dateTo = new DateProperty("Date to", "dd.MM.yyyy")
  {
    private final static String MESSAGE = "Date format: DD.MM.YYYY";
    {
      getStringValidators().add(new ZRegexStringValidator("^[0-3]?\\d\\.[01]?\\d\\.[12]\\d{3}$",
          MESSAGE));
    }
  };

  private final PasswordProperty password = new PasswordProperty("Password");

  private final ZStringProperty text = new ZStringProperty("Text");

  private final ZBigDecimalProperty taxRate = new ZBigDecimalProperty("Tax rate")
  {
    //simple validations can be made here, but better put this into the FormController 
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


  public CheckboxProperty getEnabled()
  {
    return enabled;
  }


  public DateProperty getDateFrom()
  {
    return dateFrom;
  }


  public DateProperty getDateTo()
  {
    return dateTo;
  }


  public GenderIdSelectProperty getGender()
  {
    return gender;
  }


  public NameProperty getName()
  {
    return name;
  }


  public NameProperty getSurname()
  {
    return surname;
  }


  public ZStringProperty getOccupation()
  {
    return occupation;
  }


  public PasswordProperty getPassword()
  {
    return password;
  }


  public ZStringProperty getText()
  {
    return text;
  }


  public ZBigDecimalProperty getTaxRate()
  {
    return taxRate;
  }


  public ZBooleanProperty getMarried()
  {
    return married;
  }


  public ZBooleanProperty getNoAjax()
  {
    return noAjax;
  }

}
