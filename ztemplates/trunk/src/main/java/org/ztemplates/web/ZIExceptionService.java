package org.ztemplates.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ZIExceptionService extends ZIService
{
  public void handle(HttpServletRequest req, HttpServletResponse resp, Throwable e);
}
