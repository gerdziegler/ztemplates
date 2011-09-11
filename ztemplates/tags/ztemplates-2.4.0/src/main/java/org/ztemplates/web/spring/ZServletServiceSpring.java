/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de) Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. @author
 * www.gerdziegler.de
 */

package org.ztemplates.web.spring;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.ZTemplates;

/**
 * Spring proxy for ZISecurityService
 * 
 * @author gerd
 * 
 */
@Component(ZIServletService.SPRING_NAME)
@Scope("request")
public class ZServletServiceSpring implements ZIServletService
{
  static final Logger log = Logger.getLogger(ZServletServiceSpring.class);

  private final ZIServletService servletService;


  public ZServletServiceSpring()
  {
    super();
    this.servletService = ZTemplates.getServletService();
  }


  public HttpServletRequest getRequest()
  {
    return servletService.getRequest();
  }


  public HttpServletResponse getResponse()
  {
    return servletService.getResponse();
  }


  public void render(Object obj)
  {
    servletService.render(obj);
  }


  public void render(Object obj, String mimeType)
  {
    servletService.render(obj, mimeType);
  }


  public void render(Object obj, String mimeType, String encoding)
  {
    servletService.render(obj, mimeType, encoding);
  }


  public void render(JSONObject json)
  {
    servletService.render(json);
  }


  public void render(JSONArray json)
  {
    servletService.render(json);
  }


  // public void renderJson(String obj) throws Exception
  // {
  // servletService.renderJson(obj);
  // }

  // public void renderJson(JSONObject json) throws Exception
  // {
  // servletService.renderJson(json);
  // }

  /**
   * use actionService.createUrl instead
   */
  @Deprecated
  public String createUrl(Object action)
  {
    return servletService.createUrl(action);
  }


  public String createExternalUrl(Object action) throws Exception
  {
    return servletService.createExternalUrl(action);
  }


  public void sendRedirect(Object action) throws Exception
  {
    servletService.sendRedirect(action);
  }


  public String getEncoding()
  {
    return servletService.getEncoding();
  }

}
