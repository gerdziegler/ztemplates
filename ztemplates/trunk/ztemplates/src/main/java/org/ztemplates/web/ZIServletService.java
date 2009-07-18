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

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * interface to servlet
 * 
 * @author www.gerdziegler.de
 */
public interface ZIServletService extends ZIService
{
  /**
   * @return the HttpServletRequest object associated with this servlet request
   */
  public HttpServletRequest getRequest();


  /**
   * @return the HttpServletResponse
   */
  public HttpServletResponse getResponse();


  /**
   * @return the ServletContext
   */
  public ServletContext getServletContext();


  /**
   * renders the object to the response, with default mime type "text/html"
   * 
   * @param obj
   */
  public void render(Object obj) throws Exception;


  /**
   * renders the object to the response, with specified mime type
   * 
   * @param obj
   *          object to render
   * @param mimeType
   *          mime type to use
   */
  public void render(Object obj, String mimeType) throws Exception;



  /**
   * creates a url that calls the referenced action
   * 
   * @param action
   * @return
   */
  public String createUrl(Object action);


  /**
   * 
   * @param action
   * @return
   */
  public String createExternalUrl(Object action);


  /**
   * sends a redirect to the action
   * 
   * @param action
   * @throws IOException
   */
  public void sendRedirect(Object action) throws Exception;


  /**
   * invalidates the session
   */
  public void logout();
}
