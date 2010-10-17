/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * @author www.gerdziegler.de
 */

package org.ztemplates.web.jsp;

import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.ztemplates.render.ZIRenderApplicationContext;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.ZITemplateNameRepository;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.ZTemplates;

public class ZJspRenderer implements ZIRenderer
{
  private ZITemplateNameRepository templateNameRepository;


  public void init(ZIRenderApplicationContext applicationContext, ZITemplateNameRepository templateNameRepository)
  {
    this.templateNameRepository = templateNameRepository;
  }


  public String render(Class clazz, Map<String, Object> values) throws Exception
  {
    ZIServletService servletService = ZTemplates.getServletService();

    HttpServletRequest request = servletService.getRequest();
    HttpServletResponse response = servletService.getResponse();

    String template = templateNameRepository.getTemplateName(clazz) + ".jsp";

    if (!template.startsWith("/"))
    {
      template = "/WEB-INF/classes/" + template;
    }

    for (Map.Entry<String, Object> entry : values.entrySet())
    {
      request.setAttribute(entry.getKey(), entry.getValue());
    }

    RequestDispatcher dispatcher = request.getRequestDispatcher(template);
    if (dispatcher == null)
    {
      throw new Exception("no RequestDispatcher for [" + template + "]");
    }

    ZHttpServletResponseWrapper wrapper = new ZHttpServletResponseWrapper(response);
    dispatcher.include(request, wrapper);

    return wrapper.getString();
  }
}
