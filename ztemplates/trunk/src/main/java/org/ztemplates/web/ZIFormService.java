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
package org.ztemplates.web;

import java.io.Serializable;

import org.json.JSONObject;
import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.ZIForm;
import org.ztemplates.render.ZScriptDependency;

/**
 * Methods useful when doing form processing.
 * 
 * @author www.gerdziegler.de
 */
public interface ZIFormService
{
  public static final String SPRING_NAME = "ZIFormService";


  // /**
  // * returns all properties from the form with the provided names
  // *
  // * @param form
  // * @param propNames
  // * @return
  // * @throws Exception
  // */
  // public Set<ZProperty> getPropertiesByName(ZIForm form, Set<String>
  // propNames) throws Exception;

  /**
   * sets all Properties in a form to writeable b
   * 
   * @param form
   * @param b
   * @throws Exception
   */
  public void setWriteable(ZIForm form, boolean b) throws Exception;


  /**
   * sets all Properties in a form to required b
   * 
   * @param form
   * @param b
   * @throws Exception
   */
  public void setRequired(ZIForm form, boolean b) throws Exception;


  /**
   * sets all Properties in the form to readable b
   * 
   * @param form
   * @param b
   * @throws Exception
   */
  public void setReadable(ZIForm form, boolean b) throws Exception;


  // /**
  // * lets you iterate over a form to do your own processing
  // *
  // * @param form
  // * @return
  // * @throws Exception
  // */
  // public ZFormMembers getFormMembers(ZIForm form) throws Exception;

  /**
   * computes a JSON representation of a form, used typically in AJAX - Call -
   * Handlers
   * 
   * @param form
   * @return
   * @throws Exception
   */
  public JSONObject computeJson(ZIForm form) throws Exception;


  /**
   * serializes a object to a string that can be put into a hidden form
   * parameter
   * 
   * @param obj
   * @return
   * @throws Exception
   */
  public String serialize(Serializable obj) throws Exception;


  /**
   * deserializes a object serialized to a string with serialize()
   * 
   * @param s
   * @return
   * @throws Exception
   */
  public Serializable deserialize(String s) throws Exception;


  public String serializeForm(ZIForm form) throws Exception;


  public void deserializeForm(String s, ZIForm form) throws Exception;


  /**
   * writes the form values back into the form.
   * 
   * @return
   * @throws Exception
   */
  public void copyValuesToForm(ZFormValues values, ZIForm form) throws Exception;


  /**
   * writes the form's values into a ZFormValues object that can be kept in a
   * servlet session. Do not keep the ZIform itself in the session, only the
   * values. This is to avoid keeping any unwanted objects around. Use this to
   * get the values form a form, the values are intended to be stored in the
   * servlet session, Use this to keep form state between requests, do not keep
   * the form object itself in the servlet session.
   * 
   * @return
   * @throws Exception
   */
  public void copyFormToValues(ZIForm form, ZFormValues values) throws Exception;


  /**
   * stores the form values into the servlet session under the given name
   * 
   * @param form
   */
  public void copyFormToSession(String name, ZIForm form) throws Exception;


  /**
   * same as copyFormToSession(form.getClass().getName(), form); stores the form
   * values into the servlet session under the form class name
   * 
   * @param form
   */
  public void copyFormToSession(ZIForm form) throws Exception;


  /**
   * restores the form values from the servlet session, the values must be
   * stored before under the given name with copyFormToSession. If no form
   * values can be found under that name, nothing is done.
   * 
   * @param form
   */
  public void copySessionToForm(String name, ZIForm form) throws Exception;


  /**
   * same as copySessionToForm(form.getClass().getName(), form); restores the
   * form values from the servlet session, the values must be stored before with
   * copyFormToSession. If no form values can be found under that name, nothing
   * is done.
   * 
   * @param form
   */
  public void copySessionToForm(ZIForm form) throws Exception;


  /**
   * returns the JavaScript needed by the form, typically by StringValidators in
   * Properties.
   * 
   * @param form
   * @return
   * @throws Exception
   */
  public ZScriptDependency getJavaScriptDependency(ZIForm form) throws Exception;


  /**
   * initialized the property names if the form has not been processed by a
   * action, but created manually.
   * 
   * @param form
   * @throws Exception
   */
  public void initPropertyNames(ZIForm form) throws Exception;


  /**
   * initialized the property names if the form has not been processed by a
   * action, but created manually and you have multiple forms on your page, to
   * ensure the ids are unique
   * 
   * @param form
   * @param formId
   *          the formId, is prepended to each propertyName
   * @throws Exception
   */
  public void initPropertyNames(ZIForm form, String formId) throws Exception;
}