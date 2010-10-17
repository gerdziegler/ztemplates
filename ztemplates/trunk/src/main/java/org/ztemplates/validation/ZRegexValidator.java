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

import java.util.regex.Pattern;

import org.ztemplates.json.ZExposeJson;
import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZScript;
import org.ztemplates.validation.assets.ZRegexValidatorScriptLoaderAction;
import org.ztemplates.validation.message.ZErrorMessage;
import org.ztemplates.validation.message.ZMessages;

@ZScript(javaScript =
{
    @ZJavaScript(ZRegexValidatorScriptLoaderAction.REGEX_VALIDATOR_JS)
})
public class ZRegexValidator implements ZIValidator, ZIJavaScriptValidator
{
  private final ZProperty prop;

  private final String regex;

  private final String message;

  private final Pattern pattern;


  public ZRegexValidator(String regex, String message, ZProperty prop)
  {
    super();
    this.prop = prop;
    this.regex = regex;
    this.message = message;
    pattern = Pattern.compile(regex);
  }


  // @Override
  public void validate(ZMessages res) throws Exception
  {
    for (String value : prop.getStringValues())
    {
      boolean match = pattern.matcher(value).matches();
      if (!match)
      {
        res.addMessage(new ZErrorMessage(message, prop.getName()));
      }
    }
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
