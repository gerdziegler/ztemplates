package org.ztemplates.web;

import org.ztemplates.actions.ZISecurityProvider;

public interface ZISecurityService extends ZIService
{
  public static final String SPRING_NAME = "ZISecurityService";


  @Deprecated
  public ZISecurityProvider getSecurityProvider();


  public String getUserName() throws Exception;


  public boolean isUserInRole(String role) throws Exception;

}
