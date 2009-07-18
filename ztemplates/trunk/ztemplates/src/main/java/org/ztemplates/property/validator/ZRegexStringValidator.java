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
package org.ztemplates.property.validator;

import java.util.regex.Pattern;

import org.ztemplates.json.ZExposeJson;
import org.ztemplates.property.ZError;
import org.ztemplates.property.validator.assets.ZRegexValidatorScriptLoaderAction;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZScript;

@ZScript(javaScript =
{
  @ZJavaScript(prefix = ZRegexValidatorScriptLoaderAction.PREFIX, value = ZRegexValidatorScriptLoaderAction.REGEX_VALIDATOR_JS)
})
public class ZRegexStringValidator implements ZIJavaScriptValidator
{
  private final String regex;

  private final String message;

  private final Pattern pattern;


  public ZRegexStringValidator(String regex, String message)
  {
    super();
    this.regex = regex;
    this.message = message;
    pattern = Pattern.compile(regex);
  }


  public ZError validate(String value)
  {
    boolean match = pattern.matcher(value).matches();
    return match ? null : new ZError(message);
  }


  @ZExposeJson
  public String getRegex()
  {
    return regex;
  }


  @ZExposeJson
  public String getMessage()
  {
    return message;
  }


  @ZExposeJson
  public String getJavaScriptMethodName()
  {
    return "zregexValidatorScript";
  }
}
