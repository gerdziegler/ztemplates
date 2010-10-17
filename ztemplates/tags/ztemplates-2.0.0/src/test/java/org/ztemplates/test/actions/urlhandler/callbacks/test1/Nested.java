/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 23.11.2007
 * @author www.gerdziegler.de
 */
package org.ztemplates.test.actions.urlhandler.callbacks.test1;

import org.ztemplates.actions.ZAfter;
import org.ztemplates.actions.ZBefore;
import org.ztemplates.actions.ZMatch;

@ZMatch("nested")
public class Nested
{
  private boolean beforeCalled = false;

  private boolean afterCalled = false;


  @ZBefore
  public void callThisBefore()
  {
    beforeCalled = true;
  }


  @ZAfter
  public void callThisAfter()
  {
    afterCalled = true;
  }


  public boolean isAfterCalled()
  {
    return afterCalled;
  }


  public void setAfterCalled(boolean afterCalled)
  {
    this.afterCalled = afterCalled;
  }


  public boolean isBeforeCalled()
  {
    return beforeCalled;
  }


  public void setBeforeCalled(boolean beforeCalled)
  {
    this.beforeCalled = beforeCalled;
  }

}
