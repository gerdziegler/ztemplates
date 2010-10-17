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
package org.ztemplates.test.json;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.form.ZIForm;
import org.ztemplates.json.ZExposeJson;
import org.ztemplates.property.ZStringProperty;

public class FormModel1 implements ZIForm
{
  private final ZStringProperty stringProp = new ZStringProperty();

  private final FormModel2 formData2 = new FormModel2();

  private final List<String> collection = new ArrayList<String>();

  private final List<FormModel2> collection2 = new ArrayList<FormModel2>();


  @ZExposeJson
  public ZStringProperty getStringProp()
  {
    return stringProp;
  }


  @ZExposeJson
  public FormModel2 getFormData2()
  {
    return formData2;
  }


  @ZExposeJson
  public List<String> getCollection()
  {
    return collection;
  }


  @ZExposeJson
  public List<FormModel2> getCollection2()
  {
    return collection2;
  }
}
