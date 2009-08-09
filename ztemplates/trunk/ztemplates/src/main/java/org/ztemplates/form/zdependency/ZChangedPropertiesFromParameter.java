/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
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
package org.ztemplates.form.zdependency;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.zdependency.util.ZDependencyUtils;
import org.ztemplates.form.ZFormElementMirror;
import org.ztemplates.form.ZIFormElement;
import org.ztemplates.property.ZProperty;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.ZTemplates;

public class ZChangedPropertiesFromParameter implements ZIChangedProperties
{
  private final static Logger log = Logger.getLogger(ZChangedPropertiesFromParameter.class);

  private final ZFormElementMirror mirr;

  private final String parameterName;


  public ZChangedPropertiesFromParameter(ZFormElementMirror mirr, String parameterName)
  {
    this.mirr = mirr;
    this.parameterName = parameterName;
  }


  public ZChangedPropertiesFromParameter(ZIFormElement form, String parameterName) throws Exception
  {
    this(new ZFormElementMirror(form), parameterName);
  }


  public Set<ZProperty> getChangedProperties() throws Exception
  {
    ZIServletService ss = ZTemplates.getServletService();
    Map<String, String> oldValues;
    String hiddenParam = ss.getRequest().getParameter(parameterName);
    if (hiddenParam == null)
    {
      oldValues = new HashMap<String, String>();
    }
    else
    {
      oldValues = ZDependencyUtils.decodeValues(hiddenParam);
    }

    Map<String, String> newValues = mirr.getStringValues();
    Set<String> changedPropertyNames = ZDependencyUtils.computeChangedValues(oldValues, newValues);
    Set<ZProperty> ret = mirr.getPropertiesByName(changedPropertyNames);
    return ret;
  }

}
