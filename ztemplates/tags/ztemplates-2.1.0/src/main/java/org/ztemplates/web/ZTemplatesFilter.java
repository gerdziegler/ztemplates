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
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.application.ZApplicationRepositoryWeb;
import org.ztemplates.web.application.ZHttpUtil;
import org.ztemplates.web.application.ZIServiceFactory;
import org.ztemplates.web.request.ZServiceRepositoryWebapp;

public class ZTemplatesFilter implements Filter
{
  private static final Logger log = Logger.getLogger(ZTemplatesFilter.class);

  private FilterConfig filterConfig = null;

  private Set<String> passThroughRead = new HashSet<String>();


  // private final Set<String> passThroughWrite = new HashSet<String>();

  public void init(FilterConfig filterConfig) throws ServletException
  {
    this.filterConfig = filterConfig;
  }


  public void destroy()
  {
    this.filterConfig = null;
  }


  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    HttpServletRequest req = (HttpServletRequest) request;
    HttpServletResponse resp = (HttpServletResponse) response;
    try
    {
      String uri = req.getRequestURI();
      if (passThroughRead.contains(uri))
      {
        chain.doFilter(request, response);
      }
      else
      {
        ZHttpUtil.printParameters(req);
        ZApplication application = ZApplicationRepositoryWeb.getApplication(req.getSession().getServletContext());

        boolean processed = processRequest(application, req, resp);
        if (!processed)
        {
          Set<String> passThroughWrite = new HashSet<String>(passThroughRead);
          // synchronized (passThroughWrite)
          {
            passThroughWrite.add(uri);
            passThroughRead = passThroughWrite;// new
            // HashSet<String>(passThroughWrite);
          }
          chain.doFilter(request, response);
        }
      }
    }
    catch (Exception e)
    {
      filterConfig.getServletContext().log("error in filter " + req.getRequestURI(), e);
    }
  }


  private boolean processRequest(ZApplication application, HttpServletRequest req, HttpServletResponse resp) throws Exception
  {
    String url = req.getRequestURI();
    if (log.isDebugEnabled())
    {
      log.debug("processing " + url);
    }
    long begin = System.currentTimeMillis();
    boolean ret = false;

    ZIServiceFactory serviceFactory = application.getServiceFactory();

    ZServiceRepositoryWebapp serviceRepository = new ZServiceRepositoryWebapp(application, serviceFactory, req, resp);
    ZTemplates.setServiceRepository(serviceRepository);
    try
    {
      Map httpParamMap = req.getParameterMap();
      Map<String, String[]> paramMap = new HashMap<String, String[]>(httpParamMap);
      String requestEncoding = req.getCharacterEncoding();
      if (requestEncoding == null)
      {
        requestEncoding = "ISO-8859-1";
      }
      String myEncoding = serviceRepository.getServletService().getEncoding();
      decodeRequestParam(paramMap, requestEncoding, myEncoding);
      Object handler = serviceRepository.getActionService().process(url, paramMap);
      ret = handler != null;
      return ret;
    }
    catch (Throwable e)
    {
      log.error("", e);
      serviceRepository.getExceptionService().handle(req, resp, e);
      ret = true;
    }
    finally
    {
      ZTemplates.setServiceRepository(null);
      if (log.isDebugEnabled())
      {
        long time = System.currentTimeMillis() - begin;
        if (ret)
        {
          // if (time > 0)
          {
            String msg = url + " [" + time + " ms]";
            log.info(msg);
            System.out.println(msg);
          }
        }
      }
    }
    return ret;
  }


  /**
   * decode parameters from ISO-8859-1 to encoding for this app
   * 
   * @param paramMap
   * @param myEncoding
   * @throws UnsupportedEncodingException
   */
  private void decodeRequestParam(Map<String, String[]> paramMap, String requestEncoding, String myEncoding) throws UnsupportedEncodingException
  {
    if (myEncoding == null || requestEncoding.equalsIgnoreCase(myEncoding))
    {
      return;
    }
    for (String[] param : paramMap.values())
    {
      for (int i = 0; i < param.length; i++)
      {
        String text = param[i];
        text = new String(text.getBytes(requestEncoding), myEncoding);
        param[i] = text;
      }
    }
  }
}
