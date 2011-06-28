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

public interface ZIServiceFactory
{
  // public ZIServletService createServletService(final ZIServiceRepository
  // repo, final HttpServletRequest request, final HttpServletResponse
  // response);

  // public ZIApplicationService createApplicationService(final
  // ZIServiceRepository repo);

  // public ZIActionService createActionService(final ZIServiceRepository repo,
  // String contextPath);

  // public ZIRenderService createRenderService(final ZIServiceRepository repo,
  // String contextPath);

  // public <T extends ZIService> T createService(Class<T> type) throws
  // Exception;

  public ZIExceptionService createExceptionService(ZApplication application);


  public ZISecurityService createSecurityService(ZApplication application);


  public ZIEncryptionService createEncryptionService(ZApplication application);


  public ZIMessageService createMessageService(ZApplication application);
}
