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
package org.ztemplates.form.map;

import org.ztemplates.form.ZFormMap;
import org.ztemplates.form.ZIForm;
import org.ztemplates.form.ZIdFormFactory;
import org.ztemplates.marshaller.ZStringMarshaller;

public class FormWithMap implements ZIForm
{
  private final ZFormMap<FormWithMapNested, String> models = new ZFormMap<FormWithMapNested, String>(new ZIdFormFactory<FormWithMapNested, String>()
  {
    @Override
    public FormWithMapNested createForm(String id)
    {
      return new FormWithMapNested(id);
    }
  }, new ZStringMarshaller());


  public FormWithMap()
  {
  }


  public ZFormMap<FormWithMapNested, String> getModels()
  {
    return models;
  }
}