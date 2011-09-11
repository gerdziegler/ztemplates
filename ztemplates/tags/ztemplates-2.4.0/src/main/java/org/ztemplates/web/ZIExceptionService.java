package org.ztemplates.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ZIExceptionService extends ZIService
{
  public static final String SPRING_NAME = "ZIExceptionService";


  public void handle(HttpServletRequest req, HttpServletResponse resp, Throwable e);
}
