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

package org.ztemplates.web.application;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

public class ZHttpUtil
{
  protected static Logger log = Logger.getLogger(ZHttpUtil.class);


  public static void printParameters(HttpServletRequest req)
  {
    if (log.isDebugEnabled())
    {
      log.debug("*** " + req.getRequestURL() + " *****************************************");
      log.debug("getContextPath " + req.getContextPath());
      log.debug("getParameterMap " + req.getParameterMap());
      log.debug("getPathInfo " + req.getPathInfo());
      // log.debug("getPathTranslated " + req.getPathTranslated());
      log.debug("getQueryString " + req.getQueryString());
      log.debug("getRequestURI " + req.getRequestURI());
      log.debug("getRequestURL " + req.getRequestURL());
      log.debug("getServerName " + req.getServerName());
      log.debug("getLocalPort " + req.getLocalPort());
      log.debug("getProtocol " + req.getProtocol());
      log.debug("getScheme " + req.getScheme());

      log.debug("param begin");
      Enumeration<String> en = req.getParameterNames();
      while (en.hasMoreElements())
      {
        String key = en.nextElement();
        String[] val = req.getParameterValues(key);
        for (int i = 0; i < val.length; i++)
        {
          log.debug("    " + key + "=" + val[i]);
        }
      }
      log.debug("param end");

      log.debug("session begin");
      Enumeration<String> attr = req.getSession().getAttributeNames();
      while (attr.hasMoreElements())
      {
        String key = attr.nextElement();
        Object val = req.getSession().getAttribute(key);
        log.debug("    " + key + "=" + val);
      }
      log.debug("session end");
    }
  }


  public static void addCachingHeaders(HttpServletResponse resp)
  {
    addCachingHeaders(resp, 360000L);
  }


  public static void addCachingHeaders(HttpServletResponse resp, long limit)
  {
    long now = System.currentTimeMillis();
    resp.setDateHeader("Expires", now + limit * 1000L);
    resp.setDateHeader("Last-Modified", now);
    resp.setHeader("Cache-Control", "max-age=" + limit);// + ",
    // must-revalidate");
  }


  public static void addNoCachingHeaders(HttpServletResponse resp)
  {
    long now = System.currentTimeMillis();
    resp.setDateHeader("Expires", now - 10000L);
    resp.setDateHeader("Last-Modified", now- 10000L);
    resp.setHeader("Cache-Control", "no-store");    
  }
}
