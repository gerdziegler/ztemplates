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

package org.ztemplates.spring.services;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.ztemplates.web.ZIExceptionService;
import org.ztemplates.web.ZTemplates;

/**
 * Spring proxy for ZIExceptionService
 * 
 * @author gerd
 * 
 */

@Component(ZIExceptionService.SPRING_NAME)
@Scope("request")
public class ZExceptionServiceSpring implements ZIExceptionService
{
  protected static Logger log = Logger.getLogger(ZIExceptionService.class);

  private final ZIExceptionService service;


  public ZExceptionServiceSpring()
  {
    this.service = ZTemplates.getExceptionService();
  }


  public void handle(HttpServletRequest req, HttpServletResponse resp, Throwable e)
  {
    service.handle(req, resp, e);
  }
}
