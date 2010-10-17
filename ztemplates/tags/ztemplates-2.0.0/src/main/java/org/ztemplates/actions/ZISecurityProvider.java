package org.ztemplates.actions;


public interface ZISecurityProvider
{
  public String getUserName() throws Exception;


  public boolean isUserInRole(String role) throws Exception;
}
