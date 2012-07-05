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

import org.ztemplates.form.ZFormList;
import org.ztemplates.form.ZIdForm;
import org.ztemplates.form.ZIdFormFactory;
import org.ztemplates.marshaller.ZStringMarshaller;
import org.ztemplates.property.ZStringProperty;

public class FormWithMapNested implements ZIdForm<String>
{
  private final ZStringProperty id = new ZStringProperty();

  private final ZFormList<FormWithMapNested, String> modelsNested = new ZFormList<FormWithMapNested, String>(new ZIdFormFactory<FormWithMapNested, String>()
  {
    @Override
    public FormWithMapNested createForm(String id)
    {
      return new FormWithMapNested(id);
    }
  }, new ZStringMarshaller());

  private final ZStringProperty prop = new ZStringProperty();


  public FormWithMapNested(String id)
  {
    super();
    this.id.setValue(id);
  }


  public ZFormList<FormWithMapNested, String> getModelsNested()
  {
    return modelsNested;
  }


  public ZStringProperty getProp()
  {
    return prop;
  }


  @Override
  public String getId()
  {
    return id.getValue();
  }
}
