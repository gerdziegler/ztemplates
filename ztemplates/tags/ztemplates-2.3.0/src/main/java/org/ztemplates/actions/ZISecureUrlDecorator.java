package org.ztemplates.actions;

import java.util.Set;

public interface ZISecureUrlDecorator
{
  public String removeSecurityFromUrl(String url) throws Exception;


  public String addSecurityToUrl(String url, Set<String> roles) throws Exception;
}
