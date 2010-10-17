package org.ztemplates.web.request.impl;

import java.util.Set;

import org.ztemplates.actions.ZISecureUrlDecorator;

public class ZSecureUrlDecoratorImpl implements ZISecureUrlDecorator
{
  private final String prefix;


  public ZSecureUrlDecoratorImpl(String prefix)
  {
    super();
    this.prefix = prefix;
  }


  public ZSecureUrlDecoratorImpl()
  {
    this("/secure");
  }


  public String removeSecurityFromUrl(String url)
  {
    if (url.startsWith(prefix))
    {
      return url.substring(prefix.length());
    }
    else
    {
      return url;
    }
  }


  public String addSecurityToUrl(String url, Set<String> roles)
  {
    return prefix + url;
  }
}
