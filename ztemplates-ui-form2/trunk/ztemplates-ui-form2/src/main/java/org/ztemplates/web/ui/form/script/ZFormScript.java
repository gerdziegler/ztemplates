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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.zdependency.ZIDependencyManager;
import org.zdependency.util.ZDependencyUtils;
import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.util.ZReflectionUtil;
import org.ztemplates.form.ZDependencyFormWorkflow;
import org.ztemplates.form.ZFormElementMirror;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZFormUtils;
import org.ztemplates.form.ZIFormElement;
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
    @ZJavaScript(prefix = ZFormScriptLoaderAction.PREFIX, value = ZFormScriptLoaderAction.FORM_SCRIPT),
    @ZJavaScript(prefix = ZFormScriptLoaderAction.PREFIX, value = ZFormScriptLoaderAction.FORM_TEXT_SCRIPT),
    @ZJavaScript(prefix = ZFormScriptLoaderAction.PREFIX, value = ZFormScriptLoaderAction.FORM_CHECKBOX_SCRIPT),
    @ZJavaScript(prefix = ZFormScriptLoaderAction.PREFIX, value = ZFormScriptLoaderAction.FORM_RADIO_SCRIPT),
    @ZJavaScript(prefix = ZFormScriptLoaderAction.PREFIX, value = ZFormScriptLoaderAction.FORM_SUBMIT_SCRIPT),
    @ZJavaScript(prefix = ZFormScriptLoaderAction.PREFIX, value = ZFormScriptLoaderAction.FORM_SELECT_SCRIPT)
}, property = "runtimeScripts")
public class ZFormScript
{
  private static final String UTF8 = "UTF-8";

  private final static Logger log = Logger.getLogger(ZFormScript.class);

  //  private final static String onChangeParameterName = "zformscript.changed";

  //  private final ZIFormElement form;

  private final String formId;

  private final String ajaxUrl;

  private final String formJson;

  private final Set<String> ajaxPropertyNames;

  private final String ajaxPropertyNamesJson;

  private String beforeunloadMessage;

  private static final String hiddenParameterName = "zformscriptHidden";

  private final String hiddenParameterValue;

  private final ZScriptDependency runtimeScripts;


  public ZFormScript(String formId,
      ZIFormElement form,
      String ajaxUrl,
      Set<String> ajaxPropertyNames) throws Exception
  {
    super();
    //    this.form = form;
    this.formId = formId;
    this.ajaxUrl = ajaxUrl;
    this.ajaxPropertyNames = ajaxPropertyNames;
    ajaxPropertyNamesJson = computeAjaxPropertyNamesJson();
    ZIFormService formService = ZTemplates.getFormService();
    formJson = computeFormJson(form).toString(1);
    runtimeScripts = formService.getJavaScriptDependency(form);
    HashMap<String, String> values = ZFormUtils.computeValues(form);
    hiddenParameterValue = ZDependencyUtils.encodeValues(values);
  }


  //  public ZFormScript(String formId,
  //      ZIFormElement form,
  //      String ajaxUrl,
  //      Set<ZProperty> ajaxPropertyNames) throws Exception
  //  {
  //    this(formId, form, ajaxUrl, getAjaxPropertyNames(dependencyManager));
  //  }

  public ZFormScript(String formId, ZIFormElement form) throws Exception
  {
    this(formId, form, (String) null, (Set<String>) null);
  }


  public static <T extends ZIFormElement> ZDependencyFormWorkflow<T> createDependencyFormWorkflow(
      T form) throws Exception
  {
    ZDependencyFormWorkflow<T> workflow = new ZDependencyFormWorkflow<T>(form)
    {
      @Override
      public Set<ZProperty> computeChangedProperties() throws Exception
      {
        return ZFormScript.getChangedProperties(this.form);
      }
    };
    return workflow;
  }


  private static JSONObject computeFormJson(ZIFormElement form) throws Exception
  {
    JSONObject json = ZJsonUtil.computeJSON(form);

    ZFormElementMirror mirr = new ZFormElementMirror(form);
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

    HashMap<String, String> valueMap = ZFormUtils.computeValues(form);
    String hiddenParameterValue = ZDependencyUtils.encodeValues(valueMap);
    ztemplatesJson.put(hiddenParameterName, hiddenParameterValue);

    json.put("ztemplates", ztemplatesJson);

    return json;
  }


  public static void sendAjaxResponse(ZIFormElement form) throws Exception
  {
    JSONObject json = computeFormJson(form);
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


  /**
   * returns the name of the request parameter that contains the name of the property changed in this ajax call
   * @param form
   * @return
   * @throws Exception
   */
  //  private static String getOnChangeParameterName()
  //  {
  //    return onChangeParameterName;
  //  }
  /**
   * convenience method, returns the name of the property that was changed in this ajax call
   * @param form
   * @return
   * @throws Exception 
   * @throws Exception
   */
  private static Set<String> getChangedPropertyNames(ZIFormElement form) throws Exception
  {
    Map<String, String> oldValues = getPreviousFormState();
    Map<String, String> newValues = ZFormUtils.computeValues(form);
    return ZDependencyUtils.computeChangedValues(oldValues, newValues);
  }


  private static Map<String, String> getPreviousFormState()
  {
    ZIServletService ss = ZTemplates.getServletService();
    String hiddenParam = ss.getRequest().getParameter(hiddenParameterName);
    if (hiddenParam == null)
    {
      return new HashMap<String, String>();
    }
    Map<String, String> oldValues = ZDependencyUtils.decodeValues(hiddenParam);
    return oldValues;
  }


  /**
   * convenience method, returns the property that was changed in this ajax call
   * @param form
   * @return
   * @throws Exception
   */
  public static Set<ZProperty> getChangedProperties(ZIFormElement form) throws Exception
  {
    Set<String> changedPropertyNames = getChangedPropertyNames(form);
    Set<ZProperty> ret = getPropertiesByName(form, changedPropertyNames);
    return ret;
  }


  private static Set<ZProperty> getPropertiesByName(ZIFormElement form, Set<String> propNames)
      throws Exception
  {
    Set<ZProperty> ret = new HashSet<ZProperty>();
    for (String propName : propNames)
    {
      try
      {
        Object prop = ZReflectionUtil.getObjectByBeanPath(form, propName);
        ret.add((ZProperty) prop);
      }
      catch (Exception e)
      {
        log.error("error while reading property " + propName + " from " + form, e);
      }
    }
    return ret;
  }


  public static Set<String> getAjaxPropertyNames(ZIDependencyManager<ZProperty> dependencyManager)
  {
    return getPropertyNames(dependencyManager.getTriggerNodes());
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
  public String getHiddenParameterName()
  {
    return hiddenParameterName;
  }


  @ZExpose
  public String getHiddenParameterValue()
  {
    return hiddenParameterValue;
  }
}
