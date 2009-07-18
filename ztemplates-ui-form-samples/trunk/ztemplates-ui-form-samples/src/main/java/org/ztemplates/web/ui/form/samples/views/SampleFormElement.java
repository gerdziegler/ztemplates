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
package org.ztemplates.web.ui.form.samples.views;

import org.zdependency.ZIDependencyContext;
import org.zdependency.ZIDependencyManaged;
import org.zdependency.ZIDependencyManager;
import org.zdependency.ZIUpdateListener;
import org.ztemplates.form.ZIFormElement;
import org.ztemplates.json.ZExposeJson;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;
import org.ztemplates.web.ui.form.samples.id.ContinentId;
import org.ztemplates.web.ui.form.samples.id.GenderId;
import org.ztemplates.web.ui.form.samples.views.cascading.CascadingFormElement;
import org.ztemplates.web.ui.form.samples.views.cascading.CascadingFormElementContext;
import org.ztemplates.web.ui.form.samples.views.person.PersonFormElement;
import org.ztemplates.web.ui.form.validation.ZOperationValidator;

public class SampleFormElement implements ZIFormElement<SampleFormData>, ZIDependencyManaged<ZProperty>
{
  private final PersonFormElement person = new PersonFormElement();

  private final CascadingFormElement cascading;

  private final ZOperation submit = new ZOperation("Submit Form")
  {
    {
      getStringValidators().add(new ZOperationValidator("Fix form errors first.",
          SampleFormElement.this));
    }
  };


  public SampleFormElement(CascadingFormElementContext cascadingFormElementContext)
  {
    cascading = new CascadingFormElement(cascadingFormElementContext);

    submit.setAllowedValue("Submit Form");
    person.getEnabled().setValue(true);
  }


  //this happens every time the form is processed
  @Override
  public void update()
  {
    if ("Maria".equals(person.getSurname().getStringValue()))
    {
      person.getGender().setValue(GenderId.FEMALE);
      person.getGender().setWriteable(false);
    }
    else if ("Marc".equals(person.getSurname().getStringValue()))
    {
      person.getGender().setValue(GenderId.MALE);
      person.getGender().setWriteable(false);
    }
    else
    {
      person.getGender().setWriteable(true);
    }
  }


  //this happens only when dependencies are processed
  @Override
  public void declareDependencies(ZIDependencyManager<ZProperty> dependencies)
  {
    dependencies.addDependency(person.getSurname(), cascading.getContinent());
    dependencies.addUpdateListener(cascading.getContinent(), new ZIUpdateListener<ZProperty>()
    {
      @Override
      public boolean onUpdate(ZIDependencyContext<ZProperty> ctx) throws Exception
      {
        if ("Maria".equals(person.getSurname().getStringValue()))
        {
          return cascading.getContinent().setValue(ContinentId.ASIA);
        }
        else if ("Marc".equals(person.getSurname().getStringValue()))
        {
          return cascading.getContinent().setValue(ContinentId.EUROPE);
        }
        else
        {
          return false;
        }
      }
    });
  }


  public SampleFormData getValue() throws Exception
  {
    return new SampleFormData(person.getValue(), cascading.getValue());
  }


  public void setValue(SampleFormData t)
  {
    person.setValue(t.getPerson());
    cascading.setValue(t.getCascading());
  }


  @ZExposeJson
  public PersonFormElement getPerson()
  {
    return person;
  }


  @ZExposeJson
  public CascadingFormElement getCascading()
  {
    return cascading;
  }


  @ZExposeJson
  public ZOperation getSubmit()
  {
    return submit;
  }
}
