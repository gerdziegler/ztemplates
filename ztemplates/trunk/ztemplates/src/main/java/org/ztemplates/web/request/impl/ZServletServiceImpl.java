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

package org.ztemplates.web.request.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.ztemplates.web.ZIActionService;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.http.ZHttpHeader;
import org.ztemplates.web.http.ZHttpHeaders;

public class ZServletServiceImpl implements ZIServletService
{
  static final Logger log = Logger.getLogger(ZServletServiceImpl.class);

  private final HttpServletRequest request;

  private final HttpServletResponse response;

  private final ZIActionService actionService;

  private final ZIRenderService renderService;


  // private final boolean allowRenderInPost;

  public ZServletServiceImpl(final HttpServletRequest request, final HttpServletResponse response, final ZIActionService actionService,
      final ZIRenderService renderService)
  {
    super();
    this.request = request;
    this.response = response;
    this.actionService = actionService;
    this.renderService = renderService;
    // this.allowRenderInPost =
    // !"false".equals(request.getSession().getServletContext().getInitParameter("allowRenderInPost"));
  }


  public HttpServletRequest getRequest()
  {
    return request;
  }


  public HttpServletResponse getResponse()
  {
    return response;
  }


  public void render(Object obj) throws Exception
  {
    render(obj, "text/html", null);
  }


  public void render(Object obj, String mimeType) throws Exception
  {
    render(obj, mimeType, null);
  }


  public void render(Object obj, String mimeType, String encoding) throws Exception
  {
    // if(!allowRenderInPost && request.getMethod().equals("POST"))
    // {
    // throw new
    // Exception("RESTful applications should not render in POST, send redirect to another page instead. To allow rendering in POST response set the servlet context init parameter 'allowRenderInPost=true' in WEB-INF/web.xml' like this:"
    // +
    // "\n<context-param>\n" +
    // "  <description>In RESTful applications make a redirect after POST</description>\n"
    // +
    // "  <param-name>allowRenderInPost</param-name>\n" +
    // "  <param-value>true</param-value>\n" +
    // "</context-param>\n");
    // }
    String value = obj == null ? null : renderService.render(obj);
    if (obj != null)
    {
      ZHttpHeaders headers = obj.getClass().getAnnotation(ZHttpHeaders.class);
      if (headers != null)
      {
        for (ZHttpHeader h : headers.value())
        {
          response.setHeader(h.name(), h.value());
        }
      }
    }
    if (encoding != null)
    {
      response.setCharacterEncoding(encoding);
    }
    if (mimeType != null)
    {
      response.setContentType(mimeType);
    }
    response.getWriter().print(value);
  }


  /**
   * use actionService.createUrl instead
   */
  @Deprecated
  public String createUrl(Object action)
  {
    try
    {
      return actionService.createUrl(action);
    }
    catch (Exception e)
    {
      log.error("error while creating url for " + action, e);
      return e.toString();
    }
  }


  public String createExternalUrl(Object action) throws Exception
  {
    String url = actionService.createUrl(action);
    String scheme = request.getScheme();
    String serverName = request.getServerName();
    int serverPort = request.getServerPort();
    // String contextPath = request.getContextPath();
    // String servletPath = request.getServletPath();
    StringBuffer ret = new StringBuffer();
    ret.append(scheme);
    ret.append("://");
    ret.append(serverName);
    ret.append(':');
    ret.append(serverPort);
    // ret.append(contextPath);
    // ret.append(servletPath);

    ret.append(url);
    return ret.toString();
  }


  public void sendRedirect(Object action) throws Exception
  {
    String url = actionService.createUrl(action);
    response.sendRedirect(url);
  }
}
