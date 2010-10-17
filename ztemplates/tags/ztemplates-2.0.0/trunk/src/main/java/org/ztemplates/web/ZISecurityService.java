package org.ztemplates.web;

import org.ztemplates.actions.ZISecureUrlDecorator;
import org.ztemplates.actions.ZISecurityProvider;

public interface ZISecurityService extends ZIService
{
  public ZISecurityProvider getSecurityProvider();


  public ZISecureUrlDecorator getSecureUrlDecorator();
}
