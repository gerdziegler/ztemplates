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
package org.ztemplates.examples.formprocessing.layers.passive.ui.views;

import org.ztemplates.examples.formprocessing.layers.passive.ui.views.cascading.CascadingForm;
import org.ztemplates.examples.formprocessing.layers.passive.ui.views.person.PersonForm;
import org.ztemplates.form.ZIFormModel;
import org.ztemplates.property.ZOperation;

public class SampleForm implements ZIFormModel
{
  private final PersonForm person = new PersonForm();

  private final CascadingForm cascading = new CascadingForm();

  private final ZOperation submit = new ZOperation("Submit Form");


  public PersonForm getPerson()
  {
    return person;
  }


  public CascadingForm getCascading()
  {
    return cascading;
  }


  public ZOperation getSubmit()
  {
    return submit;
  }
}
