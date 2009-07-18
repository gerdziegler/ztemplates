/*
 * Copyright 2006 Gerd Ziegler (www.gerdziegler.de) 
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
 * 
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

public class ZError extends ZState
{
  private static final long serialVersionUID = 1L;

  private Throwable cause;


  public ZError(String text)
  {
    super("error", text);
  }


  public ZError(Throwable t)
  {
    super("error", t.getLocalizedMessage());
    cause = t;
  }


  public Throwable getCause()
  {
    return cause;
  }
}
