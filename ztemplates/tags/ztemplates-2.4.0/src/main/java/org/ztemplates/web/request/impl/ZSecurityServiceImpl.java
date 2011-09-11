package org.ztemplates.web.request.impl;

import org.ztemplates.actions.ZISecureUrlDecorator;
import org.ztemplates.actions.ZISecurityProvider;
import org.ztemplates.web.ZISecurityService;

public class ZSecurityServiceImpl implements ZISecurityService
{
  private final ZISecurityProvider securityProvider;

  private final ZISecureUrlDecorator secureUrlDecorator;


  public ZSecurityServiceImpl(ZISecurityProvider securityProvider,
      ZISecureUrlDecorator secureUrlDecorator)
  {
    this.securityProvider = securityProvider;
    this.secureUrlDecorator = secureUrlDecorator;
  }


  public ZISecurityProvider getSecurityProvider()
  {
    return securityProvider;
  }


  public ZISecureUrlDecorator getSecureUrlDecorator()
  {
    return secureUrlDecorator;
  }


  public String getUserName() throws Exception
  {
    return securityProvider.getUserName();
  }


  public boolean isUserInRole(String role) throws Exception
  {
    return securityProvider.isUserInRole(role);
  }
}
