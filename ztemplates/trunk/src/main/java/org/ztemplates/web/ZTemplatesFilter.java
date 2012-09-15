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

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.log4j.Logger;

@Deprecated
public class ZTemplatesFilter extends ZTemplatesWebFilter
{
  private static final Logger log = Logger.getLogger(ZTemplatesFilter.class);


  public void init(FilterConfig filterConfig) throws ServletException
  {
    log.warn("deprecated: do not use " + getClass() + " anymore.");
    log.warn("please use " + getClass().getSuperclass().getName() + " instead.");
    super.init(filterConfig);
  }


  public void destroy()
  {
    super.destroy();
    log.warn("deprecated: do not use " + getClass() + " anymore.");
    log.warn("please use " + getClass().getSuperclass().getName() + " instead.");
  }


  public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException
  {
    log.warn("deprecated: do not use " + getClass() + " anymore.");
    log.warn("please use " + getClass().getSuperclass().getName() + " instead.");
    super.doFilter(request, response, chain);
  }

}
