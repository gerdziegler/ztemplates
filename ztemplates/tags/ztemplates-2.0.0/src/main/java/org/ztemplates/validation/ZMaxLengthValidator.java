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

import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZScript;
import org.ztemplates.validation.assets.ZRegexValidatorScriptLoaderAction;

@ZScript(javaScript =
{
    @ZJavaScript(ZRegexValidatorScriptLoaderAction.REGEX_VALIDATOR_JS)
})
public class ZMaxLengthValidator extends ZLengthValidator
{
  public ZMaxLengthValidator(int maxlength, String message, ZProperty prop)
  {
    super(0, maxlength, message, prop);
  }
}
