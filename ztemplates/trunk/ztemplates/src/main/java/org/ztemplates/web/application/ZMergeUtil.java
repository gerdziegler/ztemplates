/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de) Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. @author
 * www.gerdziegler.de
 */

package org.ztemplates.web.application;

import java.io.ByteArrayOutputStream;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.jsp.ZHttpServletResponseWrapper;

public class ZMergeUtil
{
  protected static Logger log = Logger.getLogger(ZMergeUtil.class);


  public static byte[] mergeWebResources(List<String> merge) throws Exception
  {

    // HttpServletRequest req = ZTemplates.getServletService().getRequest();
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    for (String path : merge)
    {

      byte[] buff = getFromDispatcher(path);
      out.write(buff);
      // log.info(new String(buff));

      // String contextPath = req.getContextPath();
      // if(path.startsWith(contextPath)) {
      // path = path.substring(contextPath.length());
      // }
      // log.info("loading " + path);
      //
      // InputStream in =
      // ZTemplates.getServletService().getServletContext().getResourceAsStream(path);
      // if(in==null)
      // {
      // throw new Exception("resource not found " + path);
      // }

      // URL url = new URL("http", req.getServerName(), req.getServerPort(),
      // path);
      // log.info("loading " + url);
      // InputStream in = url.openStream();

      // try {
      // ZCopyUtil.copy(in, out);
      // }
      // finally
      // {
      // if(in!=null)
      // {
      // in.close();
      // }
      // }
    }
    byte[] buff = out.toByteArray();
    return buff;
  }


  public static byte[] getFromDispatcher(String path) throws Exception
  {
    ZIServletService servletService = ZTemplates.getServletService();
    HttpServletRequest request = servletService.getRequest();
    HttpServletResponse response = servletService.getResponse();
    String contextPath = request.getContextPath();
    if (path.startsWith(contextPath))
    {
      path = path.substring(contextPath.length());
    }

    if (log.isDebugEnabled())
    {
      log.debug("dispatching to " + path);
    }

    RequestDispatcher dispatcher = request.getRequestDispatcher(path);
    if (dispatcher == null)
    {
      throw new Exception("no RequestDispatcher for [" + path + "]");
    }

    ZHttpServletResponseWrapper wrapper = new ZHttpServletResponseWrapper(response);
    dispatcher.forward(request, wrapper);
    return wrapper.getBytes();
  }

}
