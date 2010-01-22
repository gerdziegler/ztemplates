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
package org.ztemplates.test.mock;

import org.ztemplates.web.ZIActionService;
import org.ztemplates.web.ZIApplicationService;
import org.ztemplates.web.ZIEncryptionService;
import org.ztemplates.web.ZIExceptionService;
import org.ztemplates.web.ZIFormService;
import org.ztemplates.web.ZIMessageService;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZISecurityService;
import org.ztemplates.web.ZIService;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.request.ZIServiceRepository;
import org.ztemplates.web.request.impl.ZFormServiceImpl;

public class ZMockServiceRepository implements ZIServiceRepository
{
  private ZIActionService actionService;

  private ZIApplicationService applicationService;

  private ZIEncryptionService encryptionService;

  private ZIExceptionService exceptionService;

  private ZIMessageService messageService = new ZMockMessageService();

  private ZIRenderService renderService = new ZMockRenderService();

  private ZISecurityService securityService;

  private ZIServletService servletService;

  private ZIFormService formService;


  public ZIActionService getActionService()
  {
    return actionService;
  }


  public ZIApplicationService getApplicationService()
  {
    return applicationService;
  }


  public ZIEncryptionService getEncryptionService()
  {
    return encryptionService;
  }


  public ZIExceptionService getExceptionService()
  {
    return exceptionService;
  }


  public ZIMessageService getMessageService()
  {
    return messageService;
  }


  public ZIRenderService getRenderService()
  {
    return renderService;
  }


  public ZISecurityService getSecurityService()
  {
    return securityService;
  }


  public ZIServletService getServletService()
  {
    return servletService;
  }


  public <T extends ZIService> T getService(Class<T> type) throws Exception
  {
    return null;
  }


  public void setActionService(ZIActionService actionService)
  {
    this.actionService = actionService;
  }


  public void setApplicationService(ZIApplicationService applicationService)
  {
    this.applicationService = applicationService;
  }


  public void setEncryptionService(ZIEncryptionService encryptionService)
  {
    this.encryptionService = encryptionService;
  }


  public void setExceptionService(ZIExceptionService exceptionService)
  {
    this.exceptionService = exceptionService;
  }


  public void setMessageService(ZIMessageService messageService)
  {
    this.messageService = messageService;
  }


  public void setRenderService(ZIRenderService renderService)
  {
    this.renderService = renderService;
  }


  public void setSecurityService(ZISecurityService securityService)
  {
    this.securityService = securityService;
  }


  public void setServletService(ZIServletService servletService)
  {
    this.servletService = servletService;
  }


  public ZIFormService getFormService()
  {
    if (formService == null)
    {
      formService = new ZFormServiceImpl();
    }
    return formService;
  }


  public void setFormService(ZIFormService formService)
  {
    this.formService = formService;
  }
}
