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
package org.ztemplates.test.mock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.ztemplates.web.ZIServletService;

public class ZMockServletService implements ZIServletService
{
  public Map parameterMap = new HashMap();


  public String createExternalUrl(Object action)
  {
    // TODO Auto-generated method stub
    return null;
  }


  public String createUrl(Object action)
  {
    // TODO Auto-generated method stub
    return null;
  }


  public HttpServletRequest getRequest()
  {
    // TODO Auto-generated method stub
    return new HttpServletRequest()
    {

      public String getAuthType()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getContextPath()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public Cookie[] getCookies()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public long getDateHeader(String arg0)
      {
        // TODO Auto-generated method stub
        return 0;
      }


      public String getHeader(String arg0)
      {
        // TODO Auto-generated method stub
        return null;
      }


      public Enumeration getHeaderNames()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public Enumeration getHeaders(String arg0)
      {
        // TODO Auto-generated method stub
        return null;
      }


      public int getIntHeader(String arg0)
      {
        // TODO Auto-generated method stub
        return 0;
      }


      public String getMethod()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getPathInfo()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getPathTranslated()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getQueryString()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getRemoteUser()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getRequestURI()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public StringBuffer getRequestURL()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getRequestedSessionId()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getServletPath()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public HttpSession getSession()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public HttpSession getSession(boolean arg0)
      {
        // TODO Auto-generated method stub
        return null;
      }


      public Principal getUserPrincipal()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public boolean isRequestedSessionIdFromCookie()
      {
        // TODO Auto-generated method stub
        return false;
      }


      public boolean isRequestedSessionIdFromURL()
      {
        // TODO Auto-generated method stub
        return false;
      }


      public boolean isRequestedSessionIdFromUrl()
      {
        // TODO Auto-generated method stub
        return false;
      }


      public boolean isRequestedSessionIdValid()
      {
        // TODO Auto-generated method stub
        return false;
      }


      public boolean isUserInRole(String arg0)
      {
        // TODO Auto-generated method stub
        return false;
      }


      public Object getAttribute(String arg0)
      {
        // TODO Auto-generated method stub
        return null;
      }


      public Enumeration getAttributeNames()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getCharacterEncoding()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public int getContentLength()
      {
        // TODO Auto-generated method stub
        return 0;
      }


      public String getContentType()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public ServletInputStream getInputStream() throws IOException
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getLocalAddr()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getLocalName()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public int getLocalPort()
      {
        // TODO Auto-generated method stub
        return 0;
      }


      public Locale getLocale()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public Enumeration getLocales()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getParameter(String arg0)
      {
        // TODO Auto-generated method stub
        return null;
      }


      public Map getParameterMap()
      {
        return parameterMap;
      }


      public Enumeration getParameterNames()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String[] getParameterValues(String arg0)
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getProtocol()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public BufferedReader getReader() throws IOException
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getRealPath(String arg0)
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getRemoteAddr()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getRemoteHost()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public int getRemotePort()
      {
        // TODO Auto-generated method stub
        return 0;
      }


      public RequestDispatcher getRequestDispatcher(String arg0)
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getScheme()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public String getServerName()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public int getServerPort()
      {
        // TODO Auto-generated method stub
        return 0;
      }


      public boolean isSecure()
      {
        // TODO Auto-generated method stub
        return false;
      }


      public void removeAttribute(String arg0)
      {
        // TODO Auto-generated method stub

      }


      public void setAttribute(String arg0, Object arg1)
      {
        // TODO Auto-generated method stub

      }


      public void setCharacterEncoding(String arg0) throws UnsupportedEncodingException
      {
        // TODO Auto-generated method stub

      }
    };
  }


  public HttpServletResponse getResponse()
  {
    // TODO Auto-generated method stub
    return null;
  }


  public void render(Object obj) throws Exception
  {
    // TODO Auto-generated method stub

  }


  public void render(Object obj, String mimeType) throws Exception
  {
    // TODO Auto-generated method stub

  }


  public void sendRedirect(Object action) throws Exception
  {
    // TODO Auto-generated method stub

  }


  public void render(Object obj, String mimeType, String encoding) throws Exception
  {
    // TODO Auto-generated method stub
  }


  public String getEncoding()
  {
    return "ISO-8559-1";
  }
}
