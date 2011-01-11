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
package org.ztemplates.web.spring;

import java.io.Serializable;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.ZIForm;
import org.ztemplates.render.ZScriptDependency;
import org.ztemplates.web.ZIFormService;
import org.ztemplates.web.ZTemplates;

/**
 * Spring proxy for ZIFormService
 * 
 * @author gerd
 * 
 */

@Component("ZIFormService")
@Scope("request")
public class ZFormServiceSpring implements ZIFormService
{
  static final Logger log = Logger.getLogger(ZFormServiceSpring.class);

  private final ZIFormService service;


  public ZFormServiceSpring()
  {
    this.service = ZTemplates.getFormService();
  }


  public void setWriteable(ZIForm form, boolean b) throws Exception
  {
    service.setWriteable(form, b);
  }


  public void setRequired(ZIForm form, boolean b) throws Exception
  {
    service.setRequired(form, b);
  }


  public void setReadable(ZIForm form, boolean b) throws Exception
  {
    service.setReadable(form, b);
  }


  public ZFormMembers getFormMembers(ZIForm form) throws Exception
  {
    return service.getFormMembers(form);
  }


  public JSONObject computeJson(ZIForm form) throws Exception
  {
    return service.computeJson(form);
  }


  public String serialize(Serializable obj) throws Exception
  {
    return service.serialize(obj);
  }


  public Object deserialize(String s) throws Exception
  {
    return service.deserialize(s);
  }


  public void copyValuesToForm(ZFormValues values, ZIForm form) throws Exception
  {
    service.copyValuesToForm(values, form);
  }


  public void copyFormToValues(ZIForm form, ZFormValues values) throws Exception
  {
    service.copyFormToValues(form, values);
  }


  public void copyFormToSession(String name, ZIForm form) throws Exception
  {
    service.copyFormToSession(name, form);
  }


  public void copyFormToSession(ZIForm form) throws Exception
  {
    service.copyFormToSession(form);
  }


  public void copySessionToForm(String name, ZIForm form) throws Exception
  {
    service.copySessionToForm(name, form);
  }


  public void copySessionToForm(ZIForm form) throws Exception
  {
    service.copySessionToForm(form);
  }


  public ZScriptDependency getJavaScriptDependency(ZIForm form) throws Exception
  {
    return service.getJavaScriptDependency(form);
  }


  public void initPropertyNames(ZIForm form) throws Exception
  {
    service.initPropertyNames(form);
  }
}
