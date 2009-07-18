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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.actions.urlhandler.tree.ZTreeUrlHandler;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.web.ZIActionService;
import org.ztemplates.web.ZIApplicationService;
import org.ztemplates.web.ZIEncryptionService;
import org.ztemplates.web.ZIExceptionService;
import org.ztemplates.web.ZIMessageService;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZISecurityService;
import org.ztemplates.web.ZIService;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.application.ZApplication;
import org.ztemplates.web.application.ZIServiceFactory;
import org.ztemplates.web.request.ZIServiceRepository;
import org.ztemplates.web.request.ZServiceRepository;

/**
 * configures the default services
 * 
 * @author www.gerdziegler.de
 */
public class ZServiceFactory implements ZIServiceFactory
{
  protected static final Logger log = Logger.getLogger(ZServiceFactory.class);

  private final ZApplication application;


  public ZServiceFactory(final ZApplication application) throws Exception
  {
    super();
    this.application = application;

  }


  public <T extends ZIService> T createService(Class<T> type) throws Exception
  {
    return null;
  }


  public ZIApplicationService createApplicationService(final ZIServiceRepository repo,
      final HttpServletRequest request, final HttpServletResponse response)
  {
    return new ZApplicationServiceImpl(application);
  }


  public ZIExceptionService createExceptionService(final ZIServiceRepository repo,
      final HttpServletRequest request, final HttpServletResponse response)
  {
    return new ZExceptionServiceImpl();
  }


  public ZIServletService createServletService(final ZIServiceRepository repo,
      final HttpServletRequest request, final HttpServletResponse response)
  {
    return new ZServletServiceImpl(request, response, repo.getActionService(), repo
        .getRenderService());
  }


  public ZIActionService createActionService(final ZIServiceRepository repo,
      final HttpServletRequest request, final HttpServletResponse response)
  {
    String prefix = application.getActionApplication().getApplicationContext()
        .getInitParameter("prefix");
    ZMatchTree matchTree = application.getActionApplication().getMatchTree();
    ZIUrlHandler urlHandler = new ZTreeUrlHandler(matchTree, repo.getSecurityService()
        .getSecurityProvider());
    return new ZActionServiceImpl(urlHandler, request.getContextPath(), prefix);
  }


  public ZISecurityService createSecurityService(final ZIServiceRepository repo,
      final HttpServletRequest request, final HttpServletResponse response)
  {
    return new ZSecurityServiceImpl(request);
  }


  public ZIRenderService createRenderService(final ZIServiceRepository repo,
      final HttpServletRequest request, final HttpServletResponse response)
  {
    return new ZRenderServiceImpl(application.getRenderApplication(), request.getContextPath());
  }


  public ZIEncryptionService createEncryptionService(final ZIServiceRepository repo,
      final HttpServletRequest request, final HttpServletResponse response)
  {
    String password = application.getActionApplication().getApplicationContext()
        .getInitParameter("encryptPassword");
    if (password != null)
    {
      String hex = application.getActionApplication().getApplicationContext()
          .getInitParameter("encryptSalt");
      byte[] salt = null;
      if (hex != null)
      {
        hex = hex.trim().toLowerCase();
        if (hex.length() != 16)
        {
          throw new RuntimeException("'encryptSalt' in web.xml must have length 16 and contain only the hex characters 0-1 and a-f");
        }

        salt = new byte[hex.length() / 2];
        for (int i = 0; i < salt.length; i++)
        {
          salt[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
        }
      }
      return new ZEncryptionServiceImpl(password, salt);
    }
    return null;
  }


  public ZIMessageService createMessageService(final ZServiceRepository repository,
      final HttpServletRequest request, final HttpServletResponse response)
  {
    ZIMessageService ret = new ZMessageService(request.getLocale());
    return ret;
  }
}
