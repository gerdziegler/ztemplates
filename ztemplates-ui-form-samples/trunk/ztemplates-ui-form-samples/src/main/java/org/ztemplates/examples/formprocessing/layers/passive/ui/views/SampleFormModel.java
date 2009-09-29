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

import org.ztemplates.examples.formprocessing.layers.passive.ui.views.cascading.CascadingFormModel;
import org.ztemplates.examples.formprocessing.layers.passive.ui.views.person.PersonFormModel;
import org.ztemplates.form.ZIFormModel;
import org.ztemplates.json.ZExposeJson;
import org.ztemplates.property.ZOperation;

public class SampleFormModel implements ZIFormModel
{
  private final PersonFormModel person = new PersonFormModel();

  private final CascadingFormModel cascading = new CascadingFormModel();

  private final ZOperation submit = new ZOperation("Submit Form");


  @ZExposeJson
  public PersonFormModel getPerson()
  {
    return person;
  }


  @ZExposeJson
  public CascadingFormModel getCascading()
  {
    return cascading;
  }


  @ZExposeJson
  public ZOperation getSubmit()
  {
    return submit;
  }
}
