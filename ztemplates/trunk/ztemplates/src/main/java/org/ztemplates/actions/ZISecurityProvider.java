package org.ztemplates.actions;

import java.util.Set;

public interface ZISecurityProvider
{
  public String getUserName() throws Exception;


  public boolean isUserInRole(String role) throws Exception;


  public String removeSecurityFromUrl(String url) throws Exception;


  public String addSecurityToUrl(String url, Set<String> roles) throws Exception;
}
