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
import org.ztemplates.form.ZIFormElement;
import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZScriptDependency;

/**
 * Methods useful when doing form processing.
 * @author www.gerdziegler.de
 */
public interface ZIFormService
{
  /**
   * Initializes the form, reads the parameters from the request, sets the property names and revalidates each property.
   * You normally call init() for each request before doing anything with the form.
   * Also see update() method.
   * @see update()
   * @param form
   * @throws Exception
   */
  public void process(ZIFormElement form) throws Exception;


  /**
   * reads the request parameters and assigns matching values to this form
   * @param form
   * @throws Exception
   */
  public void readParameters(ZIFormElement form) throws Exception;


//  /**
//   * returns the property object that corresponds to this name
//   * @param form
//   * @param parameterName
//   * @throws Exception if the parameter name provided is invalid 
//   */
//  public ZProperty getPropertyByName(ZIFormElement form, String propertyName)
//      throws Exception;


  /**
   * returns all properties from the form with the provided names
   * @param form
   * @param propNames
   * @return
   * @throws Exception
   */
  public Set<ZProperty> getPropertiesByName(ZIFormElement form, Set<String> propNames) throws Exception;

//  /**
//   * convenience method, reads the value of the specified request parameter and returns the property object that corresponds to 
//   * this name, or null if parameter not set
//   * @see getPropertyByName
//   * @param form
//   * @param parameterName the name of the request parameter that contains the property name
//   * @throws Exception if the parameter name provided is invalid 
//   */
//  public <T> ZProperty<T> getPropertyByParameterName(ZIFormElement form, String parameterName)
//      throws Exception;


  /**
   * Calls the update methods of the form depth first (subsidiarity). A update() method should therefore only access properties
   * from the same object or a nested object, never from a parent. 
   * You normally call update() after the init() method.
   * @see init()
   * @param form
   * @throws Exception
   */
  public void update(ZIFormElement form) throws Exception;


  /**
   * sets all Properties in a form to writeable b
   * @param form
   * @param b
   * @throws Exception
   */
  public void setWriteable(ZIFormElement form, boolean b) throws Exception;


  /**
   * sets all Properties in the form to readable b
   * @param form
   * @param b
   * @throws Exception
   */
  public void setReadable(ZIFormElement form, boolean b) throws Exception;


  /**
   * returns a list with all properties that have errors
   * @param form
   * @return
   * @throws Exception
   */
  public List<ZProperty> getPropertiesWithError(ZIFormElement form) throws Exception;


  /**
   * lets you iterate over a form to do your own processing
   * @param form
   * @return
   * @throws Exception
   */
  public ZFormMembers getFormMembers(ZIFormElement form) throws Exception;


  /**
   * computes a JSON representation of a form, used typically in AJAX - Call - Handlers
   * @param form
   * @return
   * @throws Exception
   */
  public JSONObject computeJson(ZIFormElement form) throws Exception;


//  /**
//   * convenience method, computes a JSON representation of a form and renders it to the response
//   * @see computeJson
//   * @param form
//   * @throws Exception
//   */
//  public void sendJsonResponse(ZIFormElement form) throws Exception;


  /**
   * serializes some object to a form that can be put into a hidden form parameter
   * @param obj
   * @return
   * @throws Exception
   */
  public String serialize(Serializable obj) throws Exception;


  /**
   * deserializes a object serialized with serialize() from a hidden parameter
   * @param s
   * @return
   * @throws Exception
   */
  public Object deserialize(String s) throws Exception;


  /**
   * returns the JavaScript needed by the form, typically by StringValidators in Properties.
   * @param form
   * @return
   * @throws Exception
   */
  public ZScriptDependency getJavaScriptDependency(ZIFormElement form) throws Exception;
}
