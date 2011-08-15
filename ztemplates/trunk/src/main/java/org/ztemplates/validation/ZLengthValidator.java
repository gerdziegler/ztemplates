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
package org.ztemplates.validation;

import org.ztemplates.message.ZMessages;
import org.ztemplates.property.ZProperty;

public class ZLengthValidator implements ZIValidator
{
  private final int minlength;

  private final int maxlength;

  private final String message;

  private final ZProperty<String> prop;


  public ZLengthValidator(int minlength,
      int maxlength,
      String message,
      ZProperty<String> prop)
  {
    this.minlength = minlength;
    this.maxlength = maxlength;
    this.message = message;
    this.prop = prop;
  }


  public void validate(ZMessages messages) throws Exception
  {
    if (prop.isEmpty())
    {
      return;
    }
    String val = prop.getValue();
    if (val.length() > maxlength)
    {
      messages.addError(message, prop);
    }
    else if (val.length() < minlength)
    {
      messages.addError(message, prop);
    }
  }
}
