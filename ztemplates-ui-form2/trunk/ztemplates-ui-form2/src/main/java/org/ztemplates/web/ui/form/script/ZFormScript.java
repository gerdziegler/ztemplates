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
package org.ztemplates.web.ui.form.script;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.ZIFormModel;
import org.ztemplates.form.impl.ZFormModelWrapper;
import org.ztemplates.jquery.JQueryLoaderAction;
import org.ztemplates.json.ZJsonUtil;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.ZScriptDependency;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ZIFormService;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.ui.form.script.assets.ZFormScriptLoaderAction;

@ZRenderer(ZVelocityRenderer.class)
@ZScript(javaScript =
{
    @ZJavaScript(value = JQueryLoaderAction.JQUERY_MIN_JS, standalone = JQueryLoaderAction.STANDALONE, merge = JQueryLoaderAction.MERGE),
    @ZJavaScript(ZFormScriptLoaderAction.FORM_SCRIPT)
}, property = "runtimeScripts")
public final class ZFormScript
{
  private static final String UTF8 = "UTF-8";

  private final static Logger log = Logger.getLogger(ZFormScript.class);

  private final String formId;

  private final String submitUrl;

  private final String ajaxUrl;

  private final String formJson;

  private final Set<String> ajaxPropertyNames;

  private final String ajaxPropertyNamesJson;

  private String beforeunloadMessage;

  private static final String formStateParameterName = "zformscriptHidden";

  private final String formStateParameterValue;

  private final ZScriptDependency runtimeScripts;


  public ZFormScript(String formId, ZIFormModel form, String submitUrl, String ajaxUrl, Set<String> ajaxPropertyNames) throws Exception
  {
    super();
    this.formId = formId;
    this.submitUrl = submitUrl;
    this.ajaxUrl = ajaxUrl;
    this.ajaxPropertyNames = ajaxPropertyNames;
    ajaxPropertyNamesJson = computeAjaxPropertyNamesJson();
    ZIFormService formService = ZTemplates.getFormService();
    ZFormModelWrapper mirr = new ZFormModelWrapper(form);
    formJson = computeFormJson(form, mirr).toString(1);
    runtimeScripts = formService.getJavaScriptDependency(form);

    ZFormValues values = new ZFormValues();
    mirr.writeToValues(values); 
    formStateParameterValue = values.writeToString();
  }


  public ZFormScript(String formId, ZIFormModel form, String ajaxUrl, Set<String> ajaxPropertyNames) throws Exception
  {
    this(formId, form, (String) null, ajaxUrl, ajaxPropertyNames);
  }


  public ZFormScript(String formId, ZIFormModel form) throws Exception
  {
    this(formId, form, (String) null, (String) null, (Set<String>) null);
  }


  /**
   * utility that reads the old form values from a hidden request parameter
   * managed by zformscript.
   * 
   * @return
   * @throws Exception
   */
  public static ZFormValues computeOldFormValuesFromRequest() throws Exception
  {
    String oldValuesEncoded = ZTemplates.getServletService().getRequest().getParameter(ZFormScript.formStateParameterName);
    ZFormValues values = ZFormValues.createFromString(oldValuesEncoded);
    return values;
  }


  /**
   * utility that computes the names of the form property changed by user in
   * this submit. Old values are contained in hidden parameter that is managed
   * by zformscript, so you have to use ZFormScript in your view to get theis
   * feature.
   * 
   * @param form
   * @return
   * @throws Exception
   */
  public static Set<String> computeChangedFormPropertyNames(ZIFormModel form) throws Exception
  {
    ZFormModelWrapper mirr = new ZFormModelWrapper(form);
    ZFormValues newValues = new ZFormValues();
    mirr.writeToValues(newValues);
    ZFormValues oldValues = ZFormScript.computeOldFormValuesFromRequest();
    Set<String> changedNames = ZFormValues.computeChangedPropertyNames(oldValues, newValues);
    return changedNames;
  }


  /**
   * utility that computes the form properties changed by user in this submit.
   * Old values are contained in hidden parameter that is managed by
   * zformscript, so you have to use ZFormScript in your view to get theis
   * feature.
   * 
   * @param form
   * @return
   * @throws Exception
   */
  public static Set<ZProperty> computeChangedFormProperties(ZIFormModel form) throws Exception
  {
    Set<String> changedNames = ZFormScript.computeChangedFormPropertyNames(form);
    Set<ZProperty> changed = ZTemplates.getFormService().getPropertiesByName(form, changedNames);
    return changed;
  }


  private static JSONObject computeFormJson(ZIFormModel form, ZFormModelWrapper mirr) throws Exception
  {
    //TODO dynamic forms 
    JSONObject json = ZJsonUtil.computeJSON(form);

    ZFormMembers members = mirr.getFormMembers();
    JSONObject ztemplatesJson = new JSONObject();
    JSONArray propertiesJson = new JSONArray();
    for (ZProperty prop : members.getProperties())
    {
      propertiesJson.put(prop.getName());
    }
    JSONArray operationsJson = new JSONArray();
    for (ZOperation prop : members.getOperations())
    {
      operationsJson.put(prop.getName());
    }
    ztemplatesJson.put("properties", propertiesJson);
    ztemplatesJson.put("operations", operationsJson);

    ZFormValues values = new ZFormValues();
    mirr.writeToValues(values);
    String hiddenParameterValue = values.writeToString();
    ztemplatesJson.put(formStateParameterName, hiddenParameterValue);

    json.put("ztemplates", ztemplatesJson);

    return json;
  }


  public static void sendAjaxResponse(ZIFormModel form) throws Exception
  {
    ZFormModelWrapper mirr = new ZFormModelWrapper(form);
    JSONObject json = computeFormJson(form, mirr);
    String ret = json.toString(2);
    ZIServletService servletService = ZTemplates.getServletService();
    HttpServletResponse response = servletService.getResponse();
    response.setContentType("text/plain");
    response.setCharacterEncoding(UTF8);
    response.getWriter().print(ret);
  }


  private String computeAjaxPropertyNamesJson()
  {
    if (ajaxPropertyNames == null)
    {
      return new String("[]");
    }
    JSONArray ret = new JSONArray(ajaxPropertyNames);
    try
    {
      return ret.toString(1);
    }
    catch (JSONException e)
    {
      log.error("", e);
      return e.getMessage();
    }
  }


  public static Set<String> getPropertyNames(Set<ZProperty> props)
  {
    Set<String> ret = new HashSet<String>();
    for (ZProperty prop : props)
    {
      ret.add(prop.getName());
    }
    return ret;
  }


  public ZScriptDependency getRuntimeScripts() throws Exception
  {
    return runtimeScripts;
  }


  @ZExpose
  public String getFormJson() throws Exception
  {
    return formJson;
  }


  @ZExpose
  public String getFormId()
  {
    return formId;
  }


  @ZExpose
  public String getSubmitUrl()
  {
    return submitUrl;
  }


  @ZExpose
  public String getAjaxUrl()
  {
    return ajaxUrl;
  }


  @ZExpose
  public String getAjaxPropertyNamesJson()
  {
    return ajaxPropertyNamesJson;
  }


  @ZExpose
  public String getBeforeunloadMessage()
  {
    return beforeunloadMessage;
  }


  public void setBeforeunloadMessage(String beforeunloadMessage)
  {
    this.beforeunloadMessage = beforeunloadMessage;
  }


  @ZExpose
  public String getFormStateParameterName()
  {
    return formStateParameterName;
  }


  @ZExpose
  public String getFormStateParameterValue()
  {
    return formStateParameterValue;
  }
}
