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

import org.ztemplates.form.ZIFormModel;
import org.ztemplates.property.ZError;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZStringProperty;

public class TopSection implements ZIFormModel
{
  private final ZOperation op1 = new ZOperation("submit")
  {
    @Override
    public ZError validate()
    {
      return new ZError("katze");
    }
  };

  private final ZStringProperty field1 = new ZStringProperty();

  private final ZStringProperty field2 = new ZStringProperty();


  public ZStringProperty getField1()
  {
    return field1;
  }


  public ZStringProperty getField2()
  {
    return field2;
  }


  public ZOperation getOp1()
  {
    return op1;
  }
}
