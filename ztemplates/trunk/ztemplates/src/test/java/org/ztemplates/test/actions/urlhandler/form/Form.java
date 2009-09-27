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
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZStringProperty;

public class Form implements ZIFormModel
{
  private int revalidateCalledProp1 = 0;

  private int revalidateCalledOp1 = 0;

  int updateCalled = 0;
  
  
  private final ZStringProperty predefined = new ZStringProperty() {
    {
      setName("predefined");
    }
  };

  private final ZStringProperty prop1 = new ZStringProperty()
  {
    @Override
    public void revalidate()
    {
      super.revalidate();
      revalidateCalledProp1++;
    }
  };

  private final ZOperation op1 = new ZOperation("submit")
  {
    @Override
    public void revalidate()
    {
      super.revalidate();
      revalidateCalledOp1++;
      if (revalidateCalledProp1 != 1)
      {
        throw new RuntimeException("call revalidate for prop first " + revalidateCalledProp1);
      }
    }
  };

  private final TopSection topSection = new TopSection();



  public ZStringProperty getProp1()
  {
    return prop1;
  }

  public TopSection getTopSection()
  {
    return topSection;
  }


  public ZOperation getOp1()
  {
    return op1;
  }




  public int getUpdateCalled()
  {
    return updateCalled;
  }


  public int getRevalidateCalledProp1()
  {
    return revalidateCalledProp1;
  }


  public int getRevalidateCalledOp1()
  {
    return revalidateCalledOp1;
  }

  public ZStringProperty getPredefined()
  {
    return predefined;
  }
}
