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

import java.util.Locale;

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
import org.ztemplates.web.request.ZServiceRepositoryWebapp;

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


  public ZIActionService createActionService(ZIServiceRepository repo, String contextPath)
  {
    return factory.createActionService(repo, contextPath);
  }


  public ZIApplicationService createApplicationService(ZIServiceRepository repo)
  {
    return factory.createApplicationService(repo);
  }


  public ZIEncryptionService createEncryptionService(ZIServiceRepository repo)
  {
    return factory.createEncryptionService(repo);
  }


  public ZIExceptionService createExceptionService(ZIServiceRepository repo)
  {
    return factory.createExceptionService(repo);
  }


  public ZIRenderService createRenderService(ZIServiceRepository repo, String contextPath)
  {
    return factory.createRenderService(repo, contextPath);
  }


  public ZISecurityService createSecurityService(ZIServiceRepository repo, HttpServletRequest request)
  {
    return factory.createSecurityService(repo, request);
  }


  public ZIServletService createServletService(ZIServiceRepository repo, HttpServletRequest request, HttpServletResponse response)
  {
    return factory.createServletService(repo, request, response);
  }


  public ZIMessageService createMessageService(ZIServiceRepository repository, Locale locale)
  {
    return factory.createMessageService(repository, locale);
  }


  public <T extends ZIService> T createService(Class<T> type) throws Exception
  {
    return factory.createService(type);
  }
}
