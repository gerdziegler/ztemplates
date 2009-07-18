/*
 * Copyright 2008 Gerd Ziegler (www.gerdziegler.de)
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
package org.ztemplates.web.application;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ztemplates.web.ZIActionService;
import org.ztemplates.web.ZIApplicationService;
import org.ztemplates.web.ZIEncryptionService;
import org.ztemplates.web.ZIExceptionService;
import org.ztemplates.web.ZIMessageService;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZISecurityService;
import org.ztemplates.web.ZIService;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.request.ZIServiceRepository;
import org.ztemplates.web.request.ZServiceRepository;

/**
 * use this to wrap the default service factory and override the services you
 * want to change.
 * 
 * @author www.gerdziegler.de
 */
public class ZServiceFactoryWrapper implements ZIServiceFactory
{
  private final ZIServiceFactory factory;


  public ZServiceFactoryWrapper(final ZIServiceFactory factory)
  {
    super();
    this.factory = factory;
  }


  public ZIActionService createActionService(ZIServiceRepository repo, HttpServletRequest request,
      HttpServletResponse response)
  {
    return factory.createActionService(repo, request, response);
  }


  public ZIApplicationService createApplicationService(ZIServiceRepository repo,
      HttpServletRequest request, HttpServletResponse response)
  {
    return factory.createApplicationService(repo, request, response);
  }


  public ZIEncryptionService createEncryptionService(ZIServiceRepository repo,
      HttpServletRequest request, HttpServletResponse response)
  {
    return factory.createEncryptionService(repo, request, response);
  }


  public ZIExceptionService createExceptionService(ZIServiceRepository repo,
      HttpServletRequest request, HttpServletResponse response)
  {
    return factory.createExceptionService(repo, request, response);
  }


  public ZIRenderService createRenderService(ZIServiceRepository repo, HttpServletRequest request,
      HttpServletResponse response)
  {
    return factory.createRenderService(repo, request, response);
  }


  public ZISecurityService createSecurityService(ZIServiceRepository repo,
      HttpServletRequest request, HttpServletResponse response)
  {
    return factory.createSecurityService(repo, request, response);
  }


  public ZIServletService createServletService(ZIServiceRepository repo,
      HttpServletRequest request, HttpServletResponse response)
  {
    return factory.createServletService(repo, request, response);
  }


  public ZIMessageService createMessageService(ZServiceRepository repository,
      HttpServletRequest request, HttpServletResponse response)
  {
    return factory.createMessageService(repository, request, response);
  }


  public <T extends ZIService> T createService(Class<T> type) throws Exception
  {
    return factory.createService(type);
  }
}
