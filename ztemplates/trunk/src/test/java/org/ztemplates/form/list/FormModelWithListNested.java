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
package org.ztemplates.form.list;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.form.ZIForm;
import org.ztemplates.property.ZStringProperty;

public class FormModelWithListNested implements ZIForm
{
  private final ZStringProperty prop = new ZStringProperty();

  private final List<FormModelWithListNested2> nested = new ArrayList<FormModelWithListNested2>();

  int initNestedCalled = 0;


  public void initNested(int size)
  {
    initNestedCalled++;
    for (int i = 0; i < size; i++)
    {
      nested.add(new FormModelWithListNested2());
    }
  }


  public ZStringProperty getProp()
  {
    return prop;
  }
}
