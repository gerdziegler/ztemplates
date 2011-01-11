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

package org.ztemplates.web.request.impl;

import java.io.PrintWriter;
import java.io.StringWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.ztemplates.actions.security.ZSecurityException;
import org.ztemplates.web.ZIExceptionService;

public class ZExceptionServiceImpl implements ZIExceptionService
{
  protected static Logger log = Logger.getLogger(ZIExceptionService.class);


  public ZExceptionServiceImpl()
  {
  }


  public void handle(HttpServletRequest request, HttpServletResponse response, Throwable t)
  {
    log.error("[" + request.getRequestURI() + "]", t);
    if (t instanceof ZSecurityException)
    {
      try
      {
        PrintWriter w = response.getWriter();
        String txt = t.getMessage();
        w.write("<html><body><pre>");
        w.write(escapeHtml(txt));
        w.write("</pre></body></html>");
      }
      catch (Exception e)
      {
        log.error("", t);
        log.error("error while handling exception: ", e);
      }
    }
    else
    {
      try
      {
        StringWriter sw = new StringWriter();
        PrintWriter sb = new PrintWriter(sw);
        for (Throwable e = t; e != null; e = e.getCause())
        {
          e.printStackTrace(sb);
          sb.write("\n<hr>\n");
        }
        sb.flush();
        PrintWriter w = response.getWriter();
        w.write("<html><body><pre>");
        w.write(escapeHtml(sw.getBuffer().toString()));
        w.write("</pre></body></html>");
      }
      catch (Exception e)
      {
        log.error("", t);
        log.error("error while handling exception: ", e);
      }
    }
  }
  
  private static String escapeHtml(String s)
  {
    if(s==null)
    {
      return s;
    }
    return s.replaceAll("&", "&amp;").replaceAll("<", "&lt;").replaceAll(">", "&gt;");
  }
}
