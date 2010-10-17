package org.ztemplates.web.standalone;

import org.ztemplates.actions.ZISecurityProvider;

public class ZSecurityProviderStandalone implements ZISecurityProvider
{
  private final String userName;


  public ZSecurityProviderStandalone(String userName)
  {
    this.userName = userName;
  }


  public String getUserName() throws Exception
  {
    return userName;
  }


  public boolean isUserInRole(String role) throws Exception
  {
    return true;
  }
}
