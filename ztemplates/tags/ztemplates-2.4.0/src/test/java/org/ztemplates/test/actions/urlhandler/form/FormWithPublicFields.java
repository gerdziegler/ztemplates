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
package org.ztemplates.test.actions.urlhandler.form;

import org.ztemplates.form.ZIForm;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZStringProperty;

public class FormWithPublicFields implements ZIForm
{
  public final ZStringProperty prop1 = new ZStringProperty();

  public final ZOperation op1 = new ZOperation("submit", "submit");

  public final Form form = new Form();


  public ZStringProperty getProp1()
  {
    return prop1;
  }


  public ZOperation getOp1()
  {
    return op1;
  }


  public Form getForm()
  {
    return form;
  }

}
