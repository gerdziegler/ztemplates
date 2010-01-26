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

import org.ztemplates.web.ZIEncryptionService;
import org.ztemplates.web.ZIExceptionService;
import org.ztemplates.web.ZIMessageService;
import org.ztemplates.web.ZISecurityService;

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


  public ZIEncryptionService createEncryptionService(ZApplication application)
  {
    return factory.createEncryptionService(application);
  }


  public ZIExceptionService createExceptionService(ZApplication application)
  {
    return factory.createExceptionService(application);
  }


  public ZISecurityService createSecurityService(ZApplication application)
  {
    return factory.createSecurityService(application);
  }


  public ZIMessageService createMessageService(ZApplication application)
  {
    return factory.createMessageService(application);
  }
}
