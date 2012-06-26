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
package org.ztemplates.form.list.formlist;

import org.ztemplates.form.ZFormList;
import org.ztemplates.form.ZIForm;

public class FormWithList2 implements ZIForm
{
  private final ZFormList<FormWithListNested2> models = new ZFormList<FormWithListNested2>()
  {
    @Override
    public FormWithListNested2 createForm(int idx, int size)
    {
      return new FormWithListNested2();
    }
  };


  public FormWithList2()
  {
  }


  public ZFormList<FormWithListNested2> getModels()
  {
    return models;
  }
}