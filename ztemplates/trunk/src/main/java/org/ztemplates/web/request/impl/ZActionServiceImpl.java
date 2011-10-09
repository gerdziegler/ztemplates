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
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSecure;
import org.ztemplates.actions.urlhandler.ZIUrlHandler;
import org.ztemplates.actions.util.impl.ZReflectionUtil;
import org.ztemplates.web.ZIActionService;

public class ZActionServiceImpl implements ZIActionService
{
  private final String scheme;

  private final String httpPrefix;

  private final String httpsPrefix;

  private final String contextPath;

  private final ZIUrlHandler urlHandler;

  private final ZIUrlFactory urlFactory;


  public ZActionServiceImpl(ZIUrlHandler urlHandler,
      ZIUrlFactory urlFactory,
      String contextPath,
      String scheme,
      String httpPrefix,
      String httpsPrefix)
  {
    this.scheme = scheme;
    this.httpPrefix = httpPrefix;
    this.httpsPrefix = httpsPrefix;
    this.contextPath = contextPath;
    this.urlHandler = urlHandler;
    this.urlFactory = urlFactory;
  }


  public Object process(ZMatch.Protocol protocol, String url, Map<String, String[]> paramMap) throws Exception
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
    return urlHandler.process(protocol, shortUrl, paramMap);
  }


  public String createNestedUrl(Object nestedAction) throws Exception
  {
    Object action = urlHandler.getNestedActionParent();
    String nestedName = urlHandler.getNestedActionName();
    Object oldVal = ZReflectionUtil.callReferenceGetter(action, nestedName);
    ZReflectionUtil.callReferenceSetter(action, nestedName, nestedAction);
    String ret = createUrl(action);
    ZReflectionUtil.callReferenceSetter(action, nestedName, oldVal);
    return ret;
  }


  public String createUrl(ZMatch.Protocol requiresProtocol, String path) throws Exception
  {
    if (requiresProtocol == ZMatch.Protocol.HTTPS)
    {
      if (httpsPrefix == null)
      {
        throw new Exception("servletContext.getInitParameter('" + ZMatch.Protocol.HTTPS.getContextParameterName()
            + "') is empty but you use class that requires Protocol " + ZMatch.Protocol.HTTPS + " --- " + path);
      }
      return httpsPrefix + contextPath + path;
    }

    if (requiresProtocol == ZMatch.Protocol.HTTP)
    {
      if (httpPrefix == null)
      {
        throw new Exception("servletContext.getInitParameter('" + ZMatch.Protocol.HTTP.getContextParameterName()
            + "') is empty but you use class that requires Protocol " + ZMatch.Protocol.HTTP + " --- " + path);
      }
      return httpPrefix + contextPath + path;
    }

    if ("http".equals(scheme) && httpPrefix != null)
    {
      return httpPrefix + contextPath + path;
    }
    if ("https".equals(scheme) && httpsPrefix != null)
    {
      return httpsPrefix + contextPath + path;
    }

    return contextPath + path;
  }


  public String createUrl(ZMatch.Protocol requiresProtocol, Object action) throws Exception
  {
    // this is to avoid reentrant calls which would prepend the prefix multiple
    // times
    if (action instanceof String)
    {
      return (String) action;
    }

    String path = urlFactory.createUrl(action);
    return createUrl(requiresProtocol, path);
  }


  public String createUrl(Object action) throws Exception
  {
    // this is to avoid reentrant calls which would prepend the prefix multiple
    // times
    if (action instanceof String)
    {
      return (String) action;
    }

    String path = urlFactory.createUrl(action);

    ZMatch.Protocol requiresProtocol = ZMatch.Protocol.DEFAULT;

    Class actionClass = action.getClass();
    ZMatch match = (ZMatch) actionClass.getAnnotation(ZMatch.class);
    if (match != null)
    {
      requiresProtocol = match.requiresProtocol();
    }

    boolean isSecure = actionClass.isAnnotationPresent(ZSecure.class);
    if (isSecure && httpsPrefix != null)
    {
      requiresProtocol = ZMatch.Protocol.HTTPS;
    }

    return createUrl(requiresProtocol, path);
  }

  //  public String createUrl(Object action) throws Exception
  //  {
  //    // this is to avoid reentrant calls which would prepend the prefix multiple
  //    // times
  //    if (action instanceof String)
  //    {
  //      return (String) action;
  //    }
  //
  //    String path = urlFactory.createUrl(action);
  //
  //    Class actionClass = action.getClass();
  //
  //    ZMatch match = (ZMatch) actionClass.getAnnotation(ZMatch.class);
  //    if (match != null)
  //    {
  //      ZMatch.Protocol requiresProtocol = match.requiresProtocol();
  //      if (requiresProtocol == ZMatch.Protocol.HTTPS)
  //      {
  //        if (httpsPrefix == null)
  //        {
  //          throw new Exception("servletContext.getInitParameter('" + ZMatch.Protocol.HTTPS.getContextParameterName()
  //              + "') is empty but you use class that requires Protocol " + ZMatch.Protocol.HTTPS + " --- " + actionClass.getName());
  //        }
  //        return httpsPrefix + contextPath + path;
  //      }
  //      if (requiresProtocol == ZMatch.Protocol.HTTP)
  //      {
  //        if (httpPrefix == null)
  //        {
  //          throw new Exception("servletContext.getInitParameter('" + ZMatch.Protocol.HTTP.getContextParameterName()
  //              + "') is empty but you use class that requires Protocol " + ZMatch.Protocol.HTTP + " --- " + actionClass.getName());
  //        }
  //        return httpPrefix + contextPath + path;
  //      }
  //    }
  //
  //    boolean isSecure = actionClass.isAnnotationPresent(ZSecure.class);
  //    if (isSecure && httpsPrefix != null)
  //    {
  //      return httpsPrefix + contextPath + path;
  //    }
  //
  //    if (httpPrefix != null)
  //    {
  //      return httpPrefix + contextPath + path;
  //    }
  //
  //    return contextPath + path;
  //  }
}
