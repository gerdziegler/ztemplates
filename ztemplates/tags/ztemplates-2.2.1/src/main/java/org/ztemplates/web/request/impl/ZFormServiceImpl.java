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
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.ztemplates.actions.ZIActionApplicationContext;
import org.ztemplates.actions.util.ZBase64Util;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.ZIForm;
import org.ztemplates.form.impl.ZFormWrapper;
import org.ztemplates.json.ZJsonUtil;
import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZScriptDependency;
import org.ztemplates.web.ZIFormService;
import org.ztemplates.web.ZIServletService;

public class ZFormServiceImpl implements ZIFormService
{
  static final Logger log = Logger.getLogger(ZFormServiceImpl.class);

  private final ZIActionApplicationContext actionContext;

  private final ZIServletService servletService;


  public ZFormServiceImpl(ZIServletService servletService, ZIActionApplicationContext actionContext)
  {
    super();
    this.servletService = servletService;
    this.actionContext = actionContext;
  }


  public void setWriteable(ZIForm form, boolean b) throws Exception
  {
    ZFormWrapper mirr = new ZFormWrapper(form);
    mirr.setWriteable(b);
  }


  public void setReadable(ZIForm form, boolean b) throws Exception
  {
    ZFormWrapper mirr = new ZFormWrapper(form);
    mirr.setReadable(b);
  }


  public void setRequired(ZIForm form, boolean b) throws Exception
  {
    ZFormWrapper mirr = new ZFormWrapper(form);
    mirr.setRequired(b);
  }


  // public List<ZProperty> getPropertiesWithError(ZIForm form) throws Exception
  // {
  // ZFormWrapper mirr = new ZFormWrapper(form);
  // return mirr.getPropertiesWithError();
  // }

  public ZFormMembers getFormMembers(ZIForm form) throws Exception
  {
    ZFormWrapper mirr = new ZFormWrapper(form);
    return mirr.getFormMembers();
  }


  public ZScriptDependency getJavaScriptDependency(ZIForm form) throws Exception
  {
    ZFormWrapper mirr = new ZFormWrapper(form);
    return mirr.getJavaScriptDependency();
  }


  public void copyValuesToForm(ZFormValues values, ZIForm form) throws Exception
  {
    ZFormWrapper mirr = new ZFormWrapper(form);
    mirr.readFromValues(values);
  }


  public void copyFormToValues(ZIForm form, ZFormValues values) throws Exception
  {
    ZFormWrapper mirr = new ZFormWrapper(form);
    mirr.writeToValues(values);
  }


  public Set<ZProperty> getPropertiesByName(ZIForm form, Set<String> propNames) throws Exception
  {
    ZFormWrapper mirr = new ZFormWrapper(form);
    return mirr.getPropertiesByName(propNames);
  }


  public void initPropertyNames(ZIForm form) throws Exception
  {
    ZFormWrapper mirr = new ZFormWrapper(form);
    // ZDynamicFormModel.initPropertyNames(form, "");
  }


  // public void copyFormToValues(ZISessionFormModel form) throws Exception
  // {
  // String key = form.getSessionKey();
  // if (key == null)
  // {
  // return;
  // }
  // ZFormModelWrapper mirr = new ZFormModelWrapper(form);
  // ZFormValues values = (ZFormValues) actionContext.getAttribute(key);
  // if (values == null)
  // {
  // values = new ZFormValues();
  // actionContext.setAttribute(key, values);
  // }
  // mirr.writeToValues(values);
  // }

  // public void copyValuesToForm(ZISessionFormModel form) throws Exception
  // {
  // String key = form.getSessionKey();
  // if (key == null)
  // {
  // return;
  // }
  // ZFormModelWrapper mirr = new ZFormModelWrapper(form);
  // ZFormValues values = (ZFormValues) actionContext.getAttribute(key);
  // if (values == null)
  // {
  // values = new ZFormValues();
  // actionContext.setAttribute(key, values);
  // }
  // mirr.readFromValues(values);
  // }

  public JSONObject computeJson(ZIForm form) throws Exception
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


  // @Override
  public void copyFormToSession(ZIForm form) throws Exception
  {
    copyFormToSession(form.getClass().getName(), form);
  }


  // @Override
  public void copyFormToSession(String name, ZIForm form) throws Exception
  {
    HttpSession session = servletService.getRequest().getSession();
    ZFormValues values = new ZFormValues();
    copyFormToValues(form, values);
    session.setAttribute(name, values);
  }


  // @Override
  public void copySessionToForm(ZIForm form) throws Exception
  {
    copySessionToForm(form.getClass().getName(), form);
  }


  // @Override
  public void copySessionToForm(String name, ZIForm form) throws Exception
  {
    HttpSession session = servletService.getRequest().getSession();
    ZFormValues values = (ZFormValues) session.getAttribute(name);
    if (values != null)
    {
      copyValuesToForm(values, form);
    }
  }
}
