package org.ztemplates.web.request.impl;

import java.security.Principal;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.ztemplates.actions.ZISecureUrlDecorator;
import org.ztemplates.actions.ZISecurityProvider;
import org.ztemplates.web.ZISecurityService;

public class ZSecurityServiceImpl implements ZISecurityService
{
  private ZISecurityProvider securityProvider;

  private ZISecureUrlDecorator secureUrlDecorator;


  public ZSecurityServiceImpl(final HttpServletRequest request)
  {
    securityProvider = new ZISecurityProvider()
    {
      public boolean isUserInRole(String role)
      {
        return request.isUserInRole(role);
      }


      public String getUserName()
      {
        Principal p = request.getUserPrincipal();
        return p == null ? null : p.getName();
      }
    };

    secureUrlDecorator = new ZISecureUrlDecorator()
    {

      public String removeSecurityFromUrl(String url)
      {
        if (url.startsWith("/secure"))
        {
          return url.substring("/secure".length());
        }
        else
        {
          return url;
        }
      }


      public String addSecurityToUrl(String url, Set<String> roles)
      {
        return "/secure" + url;
      }
    };
  }


  public ZISecurityProvider getSecurityProvider()
  {
    return securityProvider;
  }


  public ZISecureUrlDecorator getSecureUrlDecorator()
  {
    return secureUrlDecorator;
  }
}
