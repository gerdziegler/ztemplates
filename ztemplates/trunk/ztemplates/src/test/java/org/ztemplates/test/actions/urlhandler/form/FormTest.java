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
 * 22.09.2007
 * @author www.gerdziegler.de
 */
package org.ztemplates.test.actions.urlhandler.form;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletInputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.form.ZDefaultFormWorkflow;
import org.ztemplates.form.ZFormWorkflow;
import org.ztemplates.form.ZIFormElement;
import org.ztemplates.test.ZTestUrlHandlerFactory;
import org.ztemplates.test.mock.ZMock;
import org.ztemplates.test.mock.ZMockServiceRepository;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.ZTemplates;

public class FormTest extends TestCase
{
  static Logger log = Logger.getLogger(FormTest.class);

  ZIUrlHandler up;


  protected void setUp() throws Exception
  {
    super.setUp();
    ZMock.setUp();
    up = ZTestUrlHandlerFactory.create(FormTest.class.getPackage().getName(),
        ZTestUrlHandlerFactory.defaultSecurityService);
  }


  protected void tearDown() throws Exception
  {
    up = null;
    super.tearDown();
  }


  public void testNames() throws Exception
  {
    Form form = new Form();

    ZMockServiceRepository repo = ZMock.getMock();
    repo.setServletService(new ZIServletService()
    {

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
            // TODO Auto-generated method stub
            return new HashMap();
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


      public ServletContext getServletContext()
      {
        // TODO Auto-generated method stub
        return null;
      }


      public void logout()
      {
        // TODO Auto-generated method stub

      }


      public void render(Object obj) throws Exception
      {
        // TODO Auto-generated method stub

      }


      public void render(Object obj, String mimeType) throws Exception
      {
        // TODO Auto-generated method stub

      }


      public void renderFormJson(ZIFormElement form) throws Exception
      {
        // TODO Auto-generated method stub

      }


      public void sendRedirect(Object action) throws Exception
      {
        // TODO Auto-generated method stub

      }

    });

    ZTemplates.getFormService().process(form);

    assertEquals("op1", form.getOp1().getName());
    assertEquals("prop1", form.getProp1().getName());
    assertEquals("topSection.field1", form.getTopSection().getField1().getName());
    assertEquals("topSection.field2", form.getTopSection().getField2().getName());
    assertEquals("topSection.op1", form.getTopSection().getOp1().getName());
  }


  public void testValidationParameterWithDot() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    try
    {
      FormAction obj = (FormAction) up.process("/act", param);
      fail();
    }
    catch (Exception e)
    {

    }
    // assertNotNull(obj.getForm().getTopSection().getOp1().getState());
    // assertNotNull(obj.getForm().getTopSection().getError());
    // assertNotNull(obj.getOp1().getState());
    // assertNotNull(obj.getProp1().getState());
    // assertEquals("katze",
    // obj.getForm().getTopSection().getOp1().getState().getText());
    // assertEquals("maus", obj.getForm().getTopSection().getError().getText());
  }


  public void testParamPropParameterWithDot() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("form.topSection.field1", new String[]
    {
      "val1",
    });
    param.put("katzeko", new String[]
    {
      "val4",
    });

    try
    {
      FormAction obj = (FormAction) up.process("/act", param);
      // assertEquals("val1",
      // obj.getForm().getTopSection().getField1().getStringValue());
      fail();
    }
    catch (Exception e)
    {

    }
  }


  public void testParamPropForm() throws Exception
  {
    Map<String, String[]> param = new HashMap<String, String[]>();
    param.put("topSection.field1", new String[]
    {
      "val1",
    });
    param.put("topSection.field2", new String[]
    {
      "val2",
    });

    FormAction2 obj = (FormAction2) up.process("/act2", param);
    ZDefaultFormWorkflow<Form> wf = new ZDefaultFormWorkflow<Form>(obj.getForm());
    wf.assign();
    assertEquals("val1", obj.getForm().getTopSection().getField1().getStringValue());
    assertEquals("val2", obj.getForm().getTopSection().getField2().getStringValue());
    assertEquals("getUpdateCalled", 1, obj.getForm().getUpdateCalled());
    assertEquals("getUpdateCalled", 1, obj.getForm().getTopSection().getUpdateCalled());
    assertEquals("getRevalidateCalledProp1", 1, obj.getForm().getRevalidateCalledProp1());
    assertEquals("getRevalidateCalledOp1", 1, obj.getForm().getRevalidateCalledOp1());
  }
}
