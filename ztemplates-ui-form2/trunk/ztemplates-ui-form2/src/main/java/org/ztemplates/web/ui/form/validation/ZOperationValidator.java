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
package org.ztemplates.web.ui.form.validation;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZIFormModel;
import org.ztemplates.json.ZExposeJson;
import org.ztemplates.property.ZError;
import org.ztemplates.property.ZProperty;
import org.ztemplates.property.validator.ZIJavaScriptValidator;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZScript;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.ui.form.validation.assets.ZOperationValidatorScriptLoaderAction;

@ZScript(javaScript =
{
  @ZJavaScript(ZOperationValidatorScriptLoaderAction.OPERATION_VALIDATOR_JS)
})
public class ZOperationValidator implements ZIJavaScriptValidator
{
  private final ZIFormModel[] properties;

  private final String message;


  public ZOperationValidator(String message, ZIFormModel... properties)
  {
    super();
    this.message = message;
    this.properties = properties;
  }


  public ZError validate(String value)
  {
    try
    {
      for (ZIFormModel fe : properties)
      {
        if (fe instanceof ZProperty)
        {
          ZProperty prop = (ZProperty) fe;
          if (prop.isError())
          {
            return new ZError(message);
          }
        }
        else
        {
          List<ZProperty> errors = ZTemplates.getFormService().getPropertiesWithError(fe);
          if (!errors.isEmpty())
          {
            return new ZError(message);
          }
        }
      }
      return null;
    }
    catch (Exception e)
    {
      return new ZError(e);
    }
  }


  @ZExposeJson
  public List<String> getPropertyNames() throws Exception
  {
    List<String> ret = new ArrayList<String>();
    for (ZIFormModel fe : properties)
    {
      if (fe instanceof ZProperty)
      {
        ZProperty prop = (ZProperty) fe;
        if (prop.isError())
        {
          ret.add(prop.getName());
        }
      }
      else
      {
        ZFormMembers members = ZTemplates.getFormService().getFormMembers(fe);
        for (ZProperty prop : members.getProperties())
        {
          ret.add(prop.getName());
        }
      }
    }

    return ret;
  }


  @ZExposeJson
  public String getJavaScriptMethodName()
  {
    return "ZTEMPLATES.validators.ZOperationValidator";
  }


  @ZExposeJson
  public String getMessage()
  {
    return message;
  }
}
