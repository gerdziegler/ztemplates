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

package org.ztemplates.web;

import java.util.Stack;

import org.apache.log4j.Logger;
import org.ztemplates.web.request.ZIServiceRepository;

public abstract class ZTemplates
{
  private static final Logger log = Logger.getLogger(ZTemplates.class);

  // need a stack here as in the case of request.getRequestDispatcher the
  // request can use the same thread and so will
  // set the repository to null. stack only pops.
  protected static final ThreadLocal<Stack<ZIServiceRepository>> serviceRepositoryStack = new ThreadLocal<Stack<ZIServiceRepository>>();


  public static ZIServiceRepository getServiceRepository()
  {
    return ZTemplates.serviceRepositoryStack.get().peek();
  }


  public static void setServiceRepository(ZIServiceRepository repository)
  {
    Stack<ZIServiceRepository> stack = serviceRepositoryStack.get();
    if (stack == null)
    {
      stack = new Stack<ZIServiceRepository>();
      serviceRepositoryStack.set(stack);
    }

    if (repository == null)
    {
      stack.pop();
    }
    else
    {
      stack.push(repository);
    }
    if (log.isDebugEnabled())
    {
      log.debug("serviceRepository.stack.size = " + stack.size());
    }
  }


  public static ZIServletService getServletService()
  {
    return getServiceRepository().getServletService();
  }


  public static ZIRenderService getRenderService()
  {
    return getServiceRepository().getRenderService();
  }


  public static ZIEncryptionService getEncryptionService() throws Exception
  {
    return getServiceRepository().getEncryptionService();
  }


  public static ZISecurityService getSecurityService() throws Exception
  {
    return getServiceRepository().getSecurityService();
  }


  public static ZIExceptionService getExceptionService() throws Exception
  {
    return getServiceRepository().getExceptionService();
  }


  public static ZIApplicationService getApplicationService() throws Exception
  {
    return getServiceRepository().getApplicationService();
  }


  public static ZIMessageService getMessageService() throws Exception
  {
    return getServiceRepository().getMessageService();
  }


  public static ZIFormService getFormService() throws Exception
  {
    return getServiceRepository().getFormService();
  }


  /**
   * user defined services, create in ZIServiceFactory, add ContextListener to
   * your webapp
   * 
   * <code>
   ZIApplicationRepository applicationRepository = new ZApplicationRepository(req.getSession().getServletContext());
   ZApplication application = applicationRepository.getApplication();
   </code>
   * 
   * 
   * @param <T>
   * @param type
   * @return
   * @throws Exception
   */
  public <T extends ZIService> T getService(Class<T> type) throws Exception
  {
    return getServiceRepository().getService(type);
  }
}
