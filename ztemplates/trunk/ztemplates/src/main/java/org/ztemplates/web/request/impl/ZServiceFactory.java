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
package org.ztemplates.web.request.impl;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZISecureUrlDecorator;
import org.ztemplates.actions.ZISecurityProvider;
import org.ztemplates.web.ZIEncryptionService;
import org.ztemplates.web.ZIExceptionService;
import org.ztemplates.web.ZIMessageService;
import org.ztemplates.web.ZISecurityService;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.application.ZIServiceFactory;

/**
 * configures the default services
 * 
 * @author www.gerdziegler.de
 */
public class ZServiceFactory implements ZIServiceFactory
{
  protected static final Logger log = Logger.getLogger(ZServiceFactory.class);


  public ZIExceptionService createExceptionService(ZApplication application)
  {
    return new ZExceptionServiceImpl();
  }


  public ZISecurityService createSecurityService(ZApplication application)
  {
    ZISecurityProvider securityProvider = new ZSecurityProviderImpl();
    ZISecureUrlDecorator secureUrlDecorator = new ZSecureUrlDecoratorImpl();
    return new ZSecurityServiceImpl(securityProvider, secureUrlDecorator);
  }


  public ZIEncryptionService createEncryptionService(ZApplication application)
  {
    String password = application.getActionApplication().getApplicationContext().getInitParameter("encryptPassword");
    String saltHex = application.getActionApplication().getApplicationContext().getInitParameter("encryptSalt");
    return new ZEncryptionServiceImpl(password, saltHex);
  }


  public ZIMessageService createMessageService(ZApplication application)
  {
    return new ZMessageServiceWebImpl();
  }
}
