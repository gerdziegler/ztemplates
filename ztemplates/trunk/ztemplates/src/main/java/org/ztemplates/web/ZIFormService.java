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
import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.ZIForm;
import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZScriptDependency;

/**
 * Methods useful when doing form processing.
 * 
 * @author www.gerdziegler.de
 */
public interface ZIFormService
{
  /**
   * returns all properties from the form with the provided names
   * 
   * @param form
   * @param propNames
   * @return
   * @throws Exception
   */
  public Set<ZProperty> getPropertiesByName(ZIForm form, Set<String> propNames) throws Exception;


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


  /**
   * returns a list with all properties that have errors
   * 
   * @param form
   * @return
   * @throws Exception
   */
  public List<ZProperty> getPropertiesWithError(ZIForm form) throws Exception;


  /**
   * lets you iterate over a form to do your own processing
   * 
   * @param form
   * @return
   * @throws Exception
   */
  public ZFormMembers getFormMembers(ZIForm form) throws Exception;


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
  public Object deserialize(String s) throws Exception;


  /**
   * writes the form values back into the form.
   * 
   * @return
   * @throws Exception
   */
  public void copyValuesToForm(ZFormValues values, ZIForm form) throws Exception;


  /**
   * writes the form's values into a ZFormValues object that can be kept in a servlet session. 
   * Do not keep the ZIform itself in the session, only the values. This is to avoid keeping 
   * any unwanted objects around.  
   * Use this to get the values form a form, the values are intended to be stored in the servlet session, 
   * Use this to keep form state between requests, do not keep the form object itself in the servlet session. 
   * 
   * @return
   * @throws Exception
   */
  public void copyFormToValues(ZIForm form, ZFormValues values) throws Exception;


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
}
// /**
// * convenience method, reads the value of the specified request parameter
// and returns the property object that corresponds to
// * this name, or null if parameter not set
// * @see getPropertyByName
// * @param form
// * @param parameterName the name of the request parameter that contains the
// property name
// * @throws Exception if the parameter name provided is invalid
// */
// public <T> ZProperty<T> getPropertyByParameterName(ZIFormElement form,
// String parameterName)
// throws Exception;
// /**
// * Calls the update methods of the form depth first (subsidiarity). A update()
// * method should therefore only access properties from the same object or a
// * nested object, never from a parent. You normally call update() after the
// * init() method.
// *
// * @see init()
// * @param form
// * @throws Exception
// */
// public void adjustValues(ZIForm form) throws Exception;

// /**
// * convenience method, computes a JSON representation of a form and renders
// it to the response
// * @see computeJson
// * @param form
// * @throws Exception
// */
// public void sendJsonResponse(ZIFormElement form) throws Exception;

// /**
// * use this to get the form string values for storage in the session
// *
// * @return
// * @throws Exception
// */
// public void copyValuesToForm(ZISessionform form) throws Exception;
//
//
// public void copyFormToValues(ZISessionform form) throws Exception;

// /**
// * Assigns the form values from the request parameters, sets the property
// * names and validates each property, Use this to initialize a form by hand,
// * Has exactly the same effect as declaring the form in the @ZMatch
// * annotation.
// *
// * @see @ZMatch
// * @param form
// * the form object to be initialized
// * @throws Exception
// */
// public void process(ZIForm form) throws Exception;

// /**
// * reads the request parameters and assigns matching values to this form
// *
// * @param form
// * @throws Exception
// */
// public void assign(ZIForm form) throws Exception;
// /**
// * returns the property object that corresponds to this name
// * @param form
// * @param parameterName
// * @throws Exception if the parameter name provided is invalid
// */
// public ZProperty getPropertyByName(ZIFormElement form, String propertyName)
// throws Exception;

