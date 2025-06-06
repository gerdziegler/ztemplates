/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de) Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. 20.11.2007 @author
 * www.gerdziegler.de
 */
package org.ztemplates.web.request.impl;

import java.util.Map;

import org.ztemplates.actions.ZIUrlFactory;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.web.ZIActionService;

public class ZActionServiceImpl implements ZIActionService
{
  private final String prefix;

  private final String contextPath;

  private final ZIUrlHandler urlHandler;

  private final ZIUrlFactory urlFactory;


  public ZActionServiceImpl(ZIUrlHandler urlHandler, ZIUrlFactory urlFactory, String contextPath, String prefix)
  {
    this.prefix = prefix;
    this.contextPath = contextPath;
    this.urlHandler = urlHandler;
    this.urlFactory = urlFactory;
  }


  public Object process(String url, Map<String, String[]> paramMap) throws Exception
  {
    String shortUrl;
    if (contextPath != null)
    {
      if (!url.startsWith(contextPath))
      {
        throw new Exception("url does not start with contextPath: " + url + " --- " + contextPath);
      }
      shortUrl = url.substring(contextPath.length());
    }
    else
    {
      shortUrl = url;
    }
    if (prefix != null)
    {
      if (!shortUrl.startsWith(prefix))
      {
        throw new Exception("url does not start with prefix: [url with contextPath" + url + "] --- " + shortUrl + " --- " + prefix);
      }
      shortUrl = shortUrl.substring(prefix.length());
    }
    return urlHandler.process(shortUrl, paramMap);
  }


  public String createUrl(Object action) throws Exception
  {
    // this is to avoid reentrant calls which would prepend the prefix multiple
    // times
    if (action instanceof String)
    {
      return (String) action;
    }

    String ret = urlFactory.createUrl(action);

    String prepend;
    if (prefix != null)
    {
      prepend = contextPath + prefix;
    }
    else
    {
      prepend = contextPath;
    }
    ret = prepend + ret;
    return ret;
  }
}
