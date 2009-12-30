/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * @author www.gerdziegler.de
 */
package org.ztemplates.web;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import javax.servlet.http.HttpSession;

import org.ztemplates.property.ZProperty;

/**
 * special Property that can be used to store and retrieve a value in the servlet-session.
 * Use it to pass values in redirects, but be aware the value lives in the HttpSession so if the session expires, 
 * the value is gone. Also keep in mind that the values accumulate in the session, so call removeFromSession
 * when you no longer need the value.
 * @author www.gerdziegler.de
 */
public class ZSessionProperty<T extends Serializable> extends ZProperty<T>
{
  private static AtomicLong cnt = new AtomicLong();

  private static String prefix = "zt" + System.currentTimeMillis() + "-";

  private String sessionId = prefix + cnt.incrementAndGet();


  @Override
  public boolean setStringValue(String val)
  {
    boolean ret = super.setStringValue(val);
    if (val == null)
    {
      HttpSession session = ZTemplates.getServletService().getRequest().getSession();
      session.removeAttribute(sessionId);
    }
    return ret;
  }


  @Override
  public String format(T val)
  {
    HttpSession session = ZTemplates.getServletService().getRequest().getSession();
    session.setAttribute(sessionId, val);
    return sessionId;
  }


  @Override
  public T parse(String stringValue) throws Exception
  {
    HttpSession session = ZTemplates.getServletService().getRequest().getSession();
    if (stringValue == null)
    {
      session.removeAttribute(sessionId);
      return null;
    }
    else
    {
      this.sessionId = stringValue;
      T ret = (T) session.getAttribute(sessionId);
      return ret;
    }
  }


  /**
   * removes the value from the session.
   */
  public void removeFromSession()
  {
    HttpSession session = ZTemplates.getServletService().getRequest().getSession();
    session.removeAttribute(sessionId);
  }
}
