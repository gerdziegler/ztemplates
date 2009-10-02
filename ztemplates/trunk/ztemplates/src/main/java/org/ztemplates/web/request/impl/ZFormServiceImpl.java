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
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZFormMirror;
import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.ZIForm;
import org.ztemplates.json.ZJsonUtil;
import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZScriptDependency;
import org.ztemplates.web.ZIFormService;
import org.ztemplates.web.ZIServletService;

public class ZFormServiceImpl implements ZIFormService
{
  static final Logger log = Logger.getLogger(ZFormServiceImpl.class);

  private final ZIServletService servletService;


  public ZFormServiceImpl(ZIServletService servletService)
  {
    this.servletService = servletService;
  }


  public void process(ZIForm form) throws Exception
  {
    ZFormMirror mirr = new ZFormMirror(form);
    ZFormValues formValues = new ZFormValues();
    formValues.getValues().putAll(servletService.getRequest().getParameterMap());
    mirr.setFormValues(formValues);

    //    ZFormProcessor proc = new ZFormProcessor<T>(controller);
    //    ZOperation op = proc.assign();
    //    proc.adjustValues();
    //    proc.validate();
    //    //    if (op != null)
    //    //    {
    //    //    }
    //    return proc;
  }


  //  public void assign(ZIForm form) throws Exception
  //  {
  //    ZFormMirror mirr = new ZFormMirror(form);
  //    mirr.assign();
  //  }

  //  public void adjustValues(ZIForm form) throws Exception
  //  {
  //    ZFormMirror mirr = new ZFormMirror(form);
  //    mirr.adjustValues();
  //  }

  public void setWriteable(ZIForm form, boolean b) throws Exception
  {
    ZFormMirror mirr = new ZFormMirror(form);
    mirr.setWriteable(b);
  }


  public void setReadable(ZIForm form, boolean b) throws Exception
  {
    ZFormMirror mirr = new ZFormMirror(form);
    mirr.setReadable(b);
  }


  public List<ZProperty> getPropertiesWithError(ZIForm form) throws Exception
  {
    ZFormMirror mirr = new ZFormMirror(form);
    return mirr.getPropertiesWithError();
  }


  public ZFormMembers getFormMembers(ZIForm form) throws Exception
  {
    ZFormMirror mirr = new ZFormMirror(form);
    return mirr.getFormMembers();
  }


  public ZScriptDependency getJavaScriptDependency(ZIForm form) throws Exception
  {
    ZFormMirror mirr = new ZFormMirror(form);
    return mirr.getJavaScriptDependency();
  }


  public JSONObject computeJson(ZIForm form) throws Exception
  {
    JSONObject ret = ZJsonUtil.computeJSON(form);
    return ret;
  }


  public String serialize(Serializable obj) throws Exception
  {
    String base64 = ZBase64Util.encodeObject(obj, ZBase64Util.GZIP | ZBase64Util.DONT_BREAK_LINES);
    //    String ret = URLEncoder.encode(base64, "ISO-8859-1");
    return base64;
  }


  public Object deserialize(String s) throws Exception
  {
    //    String decoded = URLDecoder.decode(s, "ISO-8859-1");
    Object ret = ZBase64Util.decodeToObject(s);
    return ret;
  }


  public ZFormValues getStringValues(ZIForm form) throws Exception
  {
    ZFormValues ret = new ZFormValues();
    ret.readFromForm(form);
    return ret;
  }


  public void setStringValues(ZIForm form, ZFormValues values) throws Exception
  {
    ZFormMirror mirr = new ZFormMirror(form);
    mirr.setFormValues(values);
  }


  public Set<ZProperty> getPropertiesByName(ZIForm form, Set<String> propNames) throws Exception
  {
    ZFormMirror mirr = new ZFormMirror(form);
    Set<ZProperty> ret = mirr.getPropertiesByName(propNames);
    return ret;
  }

  //  public <T> ZProperty<T> getPropertyByParameterName(ZIFormElement form, String parameterName)
  //      throws Exception
  //  {
  //    String propName = servletService.getRequest().getParameter(parameterName);
  //    if (propName == null || propName.length() == 0)
  //    {
  //      return null;
  //    }
  //    return getPropertyByName(form, propName);
  //  }
}
