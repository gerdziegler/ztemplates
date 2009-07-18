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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.ztemplates.actions.util.ZBase64Util;
import org.ztemplates.actions.util.ZReflectionUtil;
import org.ztemplates.form.ZFormElementMirror;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZIFormElement;
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


  public void process(ZIFormElement form) throws Exception
  {
    ZFormElementMirror mirr = new ZFormElementMirror(form);
    mirr.process();
  }


  public void readParameters(ZIFormElement form) throws Exception
  {
    ZFormElementMirror mirr = new ZFormElementMirror(form);
    mirr.readParameters();
  }


  //  public void revalidateAndUpdate(ZIFormElement form) throws Exception
  //  {
  //    ZFormElementMirror mirr = new ZFormElementMirror(form);
  //    mirr.revalidate();
  //    mirr.update();
  //  }
  //
  //
  //  public void revalidate(ZIFormElement form) throws Exception
  //  {
  //    ZFormElementMirror mirr = new ZFormElementMirror(form);
  //    mirr.revalidate();
  //  }

  public void update(ZIFormElement form) throws Exception
  {
    ZFormElementMirror mirr = new ZFormElementMirror(form);
    mirr.update();
  }


  public void setWriteable(ZIFormElement form, boolean b) throws Exception
  {
    ZFormElementMirror mirr = new ZFormElementMirror(form);
    mirr.setWriteable(b);
  }


  public void setReadable(ZIFormElement form, boolean b) throws Exception
  {
    ZFormElementMirror mirr = new ZFormElementMirror(form);
    mirr.setReadable(b);
  }


  public List<ZProperty> getPropertiesWithError(ZIFormElement form) throws Exception
  {
    ZFormElementMirror mirr = new ZFormElementMirror(form);
    return mirr.getPropertiesWithError();
  }


  public ZFormMembers getFormMembers(ZIFormElement form) throws Exception
  {
    ZFormElementMirror mirr = new ZFormElementMirror(form);
    return mirr.getFormMembers();
  }


  public ZScriptDependency getJavaScriptDependency(ZIFormElement form) throws Exception
  {
    ZFormElementMirror mirr = new ZFormElementMirror(form);
    return mirr.getJavaScriptDependency();
  }


  public JSONObject computeJson(ZIFormElement form) throws Exception
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


//  public ZProperty getPropertyByName(ZIFormElement form, String propName) throws Exception
//  {
//    try
//    {
//      Object ret = ZReflectionUtil.getObjectByBeanPath(form, propName);
//      return (ZProperty)ret;
//    }
//    catch(Exception e)
//    {
//      log.error("error while reading property " + propName + " from " + form, e);
//      return null;
//    }
//  }
  
  
  public Set<ZProperty> getPropertiesByName(ZIFormElement form, Set<String> propNames) throws Exception
  {
    Set<ZProperty> ret = new HashSet<ZProperty>();
    for (String propName : propNames)
    {
      try
      {
        Object prop = ZReflectionUtil.getObjectByBeanPath(form, propName);
        ret.add((ZProperty)prop);
      }
      catch(Exception e)
      {
        log.error("error while reading property " + propName + " from " + form, e);        
      }
    }
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
