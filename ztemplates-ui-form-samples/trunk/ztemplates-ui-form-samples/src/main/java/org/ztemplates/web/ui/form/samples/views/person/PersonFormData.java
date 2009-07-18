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

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import org.ztemplates.web.ui.form.samples.id.GenderId;

public class PersonFormData implements Serializable
{
  private final Boolean enabled;

  private final String name;

  private final String surname;

  private final GenderId gender;

  private final Date dateOfBirth;

  private final String occupation;

  private final String password;

  private final String text;

  private final BigDecimal taxRate;

  private final Boolean married;


  public PersonFormData(Boolean enabled,
      String name,
      String surname,
      GenderId gender,
      Date dateOfBirth,
      String occupation,
      String password,
      String text,
      BigDecimal taxRate,
      Boolean married)
  {
    super();
    this.enabled = enabled;
    this.name = name;
    this.surname = surname;
    this.gender = gender;
    this.dateOfBirth = dateOfBirth;
    this.occupation = occupation;
    this.password = password;
    this.text = text;
    this.taxRate = taxRate;
    this.married = married;
  }


  public Boolean getEnabled()
  {
    return enabled;
  }


  public String getName()
  {
    return name;
  }


  public String getSurname()
  {
    return surname;
  }


  public GenderId getGender()
  {
    return gender;
  }


  public Date getDateOfBirth()
  {
    return dateOfBirth;
  }


  public String getOccupation()
  {
    return occupation;
  }


  public String getPassword()
  {
    return password;
  }


  public String getText()
  {
    return text;
  }


  public BigDecimal getTaxRate()
  {
    return taxRate;
  }


  public Boolean getMarried()
  {
    return married;
  }
}
