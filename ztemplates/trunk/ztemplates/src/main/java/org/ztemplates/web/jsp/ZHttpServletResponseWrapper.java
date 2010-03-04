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

package org.ztemplates.web.jsp;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

public class ZHttpServletResponseWrapper implements HttpServletResponse
{
  private HttpServletResponse response;

  private ZServletOutputStreamWrapper out;

  private PrintWriter pw;


  public ZHttpServletResponseWrapper(HttpServletResponse response)
  {
    this.response = response;
    this.out = new ZServletOutputStreamWrapper();
    this.pw = new PrintWriter(out);
    try
    {
      pw = new PrintWriter(new BufferedWriter(new OutputStreamWriter(out, "utf-8")), false);
    }
    catch (UnsupportedEncodingException e)
    {
      throw new Error(e);
    }
  }


  public String getString() throws UnsupportedEncodingException
  {
    pw.flush();
    byte[] bytes = getBytes();
    String s = getCharacterEncoding() == null ? new String(bytes) : new String(bytes, getCharacterEncoding());
    return s;
  }


  public byte[] getBytes()
  {
    pw.flush();
    byte[] ret = out.getBuffer().toByteArray();
    return ret;
  }


  public int getLength()
  {
    pw.flush();
    return out.getBuffer().size();
  }


  public void resetBuffer()
  {
  }


  public void flushBuffer() throws IOException
  {
    pw.flush();
  }


  public boolean isCommitted()
  {
    return false;
  }


  public void reset()
  {
  }


  // ****************************************************
  // ****************************************************
  // ****************************************************

  public PrintWriter getWriter() throws IOException
  {
    return pw;
  }


  public ServletOutputStream getOutputStream() throws IOException
  {
    return out;
  }


  public void addCookie(Cookie c)
  {
    response.addCookie(c);
  }


  public boolean containsHeader(String s)
  {
    return response.containsHeader(s);
  }


  public String encodeURL(String s)
  {
    return response.encodeURL(s);
  }


  public String encodeRedirectURL(String s)
  {
    return response.encodeRedirectURL(s);
  }


  public String encodeUrl(String s)
  {
    return response.encodeURL(s);
  }


  public String encodeRedirectUrl(String s)
  {
    return response.encodeRedirectURL(s);
  }


  public void sendError(int s, String arg1) throws IOException
  {
    response.sendError(s, arg1);
  }


  public void sendError(int s) throws IOException
  {
    response.sendError(s);
  }


  public void sendRedirect(String s) throws IOException
  {
    response.sendRedirect(s);
  }


  public void setDateHeader(String s, long l)
  {
    response.setDateHeader(s, l);
  }


  public void addDateHeader(String s, long l)
  {
    response.addDateHeader(s, l);
  }


  public void setHeader(String s, String s1)
  {
    response.setHeader(s, s1);
  }


  public void addHeader(String s, String arg1)
  {
    response.addHeader(s, arg1);
  }


  public void setIntHeader(String s, int arg1)
  {
    response.setIntHeader(s, arg1);
  }


  public void addIntHeader(String s, int arg1)
  {
    response.addIntHeader(s, arg1);
  }


  public void setStatus(int s)
  {
    response.setStatus(s);
  }


  public void setStatus(int s, String arg1)
  {
    response.setStatus(s, arg1);
  }


  public String getCharacterEncoding()
  {
    return response.getCharacterEncoding();
  }


  public String getContentType()
  {
    return response.getContentType();
  }


  public void setCharacterEncoding(String s)
  {
    response.setCharacterEncoding(s);
  }


  public void setContentLength(int s)
  {
    response.setContentLength(s);
  }


  public void setContentType(String s)
  {
    response.setContentType(s);
  }


  public void setBufferSize(int s)
  {
    response.setBufferSize(s);
  }


  public int getBufferSize()
  {
    return response.getBufferSize();
  }


  public void setLocale(Locale s)
  {
    response.setLocale(s);
  }


  public Locale getLocale()
  {
    return response.getLocale();
  }


  public HttpServletResponse getResponse()
  {
    return response;
  }
}
