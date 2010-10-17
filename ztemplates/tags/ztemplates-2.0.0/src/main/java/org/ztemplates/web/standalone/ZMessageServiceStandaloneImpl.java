/*
 * Copyright 2008 Gerd Ziegler (www.gerdziegler.de)
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
package org.ztemplates.web.standalone;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

import org.apache.log4j.Logger;
import org.ztemplates.web.ZIMessageService;

public class ZMessageServiceStandaloneImpl implements ZIMessageService
{
  static final Logger log = Logger.getLogger(ZMessageServiceStandaloneImpl.class);

  private final Locale locale;


  public ZMessageServiceStandaloneImpl(Locale locale)
  {
    this.locale = locale;
  }


  public Locale getLocale()
  {
    return locale;
  }


  public String getMessage(String bundleName, String messageId, Object... parameters)
  {
    try
    {
      ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale);
      String ret = bundle.getString(messageId);
      ret = MessageFormat.format(ret, parameters);
      return ret;
    }
    catch (Exception e)
    {
      String msg = "Error while reading resource bundle " + bundleName + " " + messageId + " for locale " + locale;
      log.error(msg, e);
      return msg + " --- " + e.getMessage();
    }
  }
}
