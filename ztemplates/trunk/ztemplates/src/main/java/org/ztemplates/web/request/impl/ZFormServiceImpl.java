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
package org.ztemplates.web.request.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.ztemplates.actions.util.ZBase64Util;
import org.ztemplates.form.ZDynamicFormModel;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.ZIFormModel;
import org.ztemplates.json.ZJsonUtil;
import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZScriptDependency;
import org.ztemplates.web.ZIFormService;

public class ZFormServiceImpl implements ZIFormService
{
  static final Logger log = Logger.getLogger(ZFormServiceImpl.class);


  public void setWriteable(ZIFormModel form, boolean b) throws Exception
  {
    ZDynamicFormModel mirr = new ZDynamicFormModel(form);
    mirr.setWriteable(b);
  }


  public void setReadable(ZIFormModel form, boolean b) throws Exception
  {
    ZDynamicFormModel mirr = new ZDynamicFormModel(form);
    mirr.setReadable(b);
  }


  public List<ZProperty> getPropertiesWithError(ZIFormModel form) throws Exception
  {
    ZDynamicFormModel mirr = new ZDynamicFormModel(form);
    return mirr.getPropertiesWithError();
  }


  public ZFormMembers getFormMembers(ZIFormModel form) throws Exception
  {
    ZDynamicFormModel mirr = new ZDynamicFormModel(form);
    return mirr.getFormMembers();
  }


  public ZScriptDependency getJavaScriptDependency(ZIFormModel form) throws Exception
  {
    ZDynamicFormModel mirr = new ZDynamicFormModel(form);
    return mirr.getJavaScriptDependency();
  }


  public ZFormValues getFormValues(ZIFormModel form) throws Exception
  {
    ZDynamicFormModel mirr = new ZDynamicFormModel(form);
    return mirr.getFormValues();
  }


  public void setFormValues(ZIFormModel form, ZFormValues values) throws Exception
  {
    ZDynamicFormModel mirr = new ZDynamicFormModel(form);
    mirr.setFormValues(values);
  }


  public Set<ZProperty> getPropertiesByName(ZIFormModel form, Set<String> propNames) throws Exception
  {
    ZDynamicFormModel mirr = new ZDynamicFormModel(form);
    return mirr.getPropertiesByName(propNames);
  }


  public void initPropertyNames(ZIFormModel form) throws Exception
  {
    ZDynamicFormModel mirr = new ZDynamicFormModel(form);
    // ZDynamicFormModel.initPropertyNames(form, "");
  }


  public JSONObject computeJson(ZIFormModel form) throws Exception
  {
    JSONObject ret = ZJsonUtil.computeJSON(form);
    return ret;
  }


  public String serialize(Serializable obj) throws Exception
  {
    String base64 = ZBase64Util.encodeObject(obj, ZBase64Util.GZIP | ZBase64Util.DONT_BREAK_LINES);
    // String ret = URLEncoder.encode(base64, "ISO-8859-1");
    return base64;
  }


  public Object deserialize(String s) throws Exception
  {
    // String decoded = URLDecoder.decode(s, "ISO-8859-1");
    Object ret = ZBase64Util.decodeToObject(s);
    return ret;
  }
}
