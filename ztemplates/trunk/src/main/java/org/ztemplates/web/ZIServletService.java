/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * @author www.gerdziegler.de
 */

package org.ztemplates.web;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * interface to servlet. Not available in standalone mode!
 * 
 * @author www.gerdziegler.de
 */
public interface ZIServletService extends ZIService
{
  public static final String SPRING_NAME = "ZIServletService";


  /**
   * @return the HttpServletRequest object associated with this servlet request
   */
  public HttpServletRequest getRequest();


  /**
   * @return the HttpServletResponse
   */
  public HttpServletResponse getResponse();


  /**
   * renders the object to the response, with default mime type "text/html"
   * 
   * @param obj
   */
  public void render(Object obj);


  /**
   * renders the object to the response, with specified mime type
   * 
   * @param obj
   *          object to render
   * @param mimeType
   *          mime type to use
   */
  public void render(Object obj, String mimeType);


  /**
   * renders the object to the response, with specified mime type and encoding
   * 
   * @param obj
   *          object to render
   * @param mimeType
   *          mime type to use
   * @param mimeType
   *          encoding to use
   */
  public void render(Object obj, String mimeType, String encoding);


  /**
   * utility as this is often needed for ajax applications, renders as mime-type
   * "application/json"
   */
  public void render(JSONObject json);


  /**
   * utility as this is often needed for ajax applications, renders as mime-type
   * "application/json"
   */
  public void render(JSONArray json);


  /**
   * utility as this is often needed for ajax applications, renders as mime-type
   * "application/json"
   */
  // public void renderJson(JSONObject json) throws Exception;
  // public void renderJson(String json) throws Exception;

  /**
   * creates a url that calls the referenced action, Use
   * ZIActionService.createUrl instead.
   * 
   * @param action
   * @return
   */
  @Deprecated
  // Use ZIActionService.createUrl instead.
  public String createUrl(Object action);


  //  /**
  //   * Creates a url including http://servername:port as provided by HttpRequest
  //   * 
  //   * @param action
  //   * @return
  //   * @throws Exception
  //   */
  //  public String createExternalUrl(Object action) throws Exception;

  /**
   * sends a redirect to the action
   * 
   * @param action
   * @throws IOException
   */
  public void sendRedirect(Object action) throws Exception;


  /**
   * The encoding used for the response and for parsing request parameters,
   * defaults to "UTF-8". *
   * <p>
   * To change encoding to ISO-8859-1 add the following lines to your web.xml:
   * 
   * <pre>
   * <context-param> 
   *    <param-name>encoding</param-name>
   *    <param-value>ISO-8859-1</param-value> 
   * </context-param>
   * </pre>
   * 
   * Don't forget to adjust your IDE/compiler/editor settings to use the
   * selected encoding.
   * 
   * @return
   */
  public String getEncoding();

}
