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
import org.ztemplates.actions.ZGetter;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZFormMirror;
import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.ZIFormModel;
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
    @ZJavaScript(prefix = JQueryLoaderAction.LOADER_URL_PREFIX, value = JQueryLoaderAction.JQUERY_MIN_JS, standalone = JQueryLoaderAction.STANDALONE, merge = JQueryLoaderAction.MERGE),
    @ZJavaScript(prefix = ZFormScriptLoaderAction.PREFIX, value = ZFormScriptLoaderAction.FORM_SCRIPT)
}, property = "runtimeScripts")
public class ZFormScript
{
  private static final String UTF8 = "UTF-8";

  private final static Logger log = Logger.getLogger(ZFormScript.class);

  private final String formId;

  private final String ajaxUrl;

  private final String formJson;

  private final Set<String> ajaxPropertyNames;

  private final String ajaxPropertyNamesJson;

  private String beforeunloadMessage;

  private static final String formStateParameterName = "zformscriptHidden";

  private final String formStateParameterValue;

  private final ZScriptDependency runtimeScripts;


  public ZFormScript(String formId, ZIFormModel form, String ajaxUrl, Set<String> ajaxPropertyNames)
      throws Exception
  {
    super();
    this.formId = formId;
    this.ajaxUrl = ajaxUrl;
    this.ajaxPropertyNames = ajaxPropertyNames;
    ajaxPropertyNamesJson = computeAjaxPropertyNamesJson();
    ZIFormService formService = ZTemplates.getFormService();
    ZFormMirror mirr = new ZFormMirror(form);
    formJson = computeFormJson(mirr).toString(1);
    runtimeScripts = formService.getJavaScriptDependency(form);

    ZFormValues values = new ZFormValues();
    values.readFromForm(form);
    formStateParameterValue = values.writeToString();
  }


  public ZFormScript(String formId, ZIFormModel form) throws Exception
  {
    this(formId, form, (String) null, (Set<String>) null);
  }


  /**
   * utility that reads the old form values from a hidden request parameter
   * managed by zformscript.
   * @return
   * @throws Exception
   */
  public static ZFormValues computeOldFormValuesFromRequest() throws Exception
  {
    String oldValuesEncoded = ZTemplates.getServletService().getRequest()
        .getParameter(ZFormScript.formStateParameterName);
    ZFormValues values = ZFormValues.createFromString(oldValuesEncoded);
    return values;
  }


  /**
   * utility that computes the names of the form property changed by user in this submit. 
   * Old values are contained in hidden parameter that is managed by zformscript, so you have to 
   * use ZFormScript in your view to get theis feature. 
   * @param form
   * @return
   * @throws Exception
   */
  public static Set<String> computeChangedFormPropertyNames(ZIFormModel form) throws Exception
  {
    ZFormValues newValues = ZFormValues.createFromForm(form);
    ZFormValues oldValues = ZFormScript.computeOldFormValuesFromRequest();
    Set<String> changedNames = ZFormValues.computeChangedPropertyNames(oldValues, newValues);
    return changedNames;
  }


  /**
   * utility that computes the form properties changed by user in this submit. 
   * Old values are contained in hidden parameter that is managed by zformscript, so you have to 
   * use ZFormScript in your view to get theis feature. 
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


  private static JSONObject computeFormJson(ZFormMirror mirr) throws Exception
  {
    JSONObject json = ZJsonUtil.computeJSON(mirr.getFormModel());

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
    values.readFromForm(mirr.getFormModel());
    String hiddenParameterValue = values.writeToString();
    ztemplatesJson.put(formStateParameterName, hiddenParameterValue);

    json.put("ztemplates", ztemplatesJson);

    return json;
  }


  public static void sendAjaxResponse(ZIFormModel form) throws Exception
  {
    ZFormMirror mirr = new ZFormMirror(form);
    JSONObject json = computeFormJson(mirr);
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


  @ZGetter("runtimeScripts")
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
