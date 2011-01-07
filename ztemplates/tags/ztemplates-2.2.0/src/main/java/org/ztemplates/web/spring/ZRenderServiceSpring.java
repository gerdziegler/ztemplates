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

package org.ztemplates.web.spring;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.ztemplates.render.ZIRenderedObject;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZTemplates;

/**
 * Spring proxy for ZIRenderService
 * 
 * @author gerd
 * 
 */
@Component("ZIRenderService")
@Scope("request")
public class ZRenderServiceSpring implements ZIRenderService
{
  static final Logger log = Logger.getLogger(ZRenderServiceSpring.class);

  private final ZIRenderService service;


  public ZRenderServiceSpring()
  {
    this.service = ZTemplates.getRenderService();
  }


  public String render(Object obj) throws Exception
  {
    return service.render(obj);
  }


  public ZIRenderedObject prerender(Object obj) throws Exception
  {
    return service.prerender(obj);
  }


  public String renderZtemplatesCss() throws Exception
  {
    return service.renderZtemplatesCss();
  }


  public String createJavaScriptId()
  {
    return service.createJavaScriptId();
  }


  public void setJavaScriptIdPrefix(String javaScriptIdPrefix)
  {
    service.setJavaScriptIdPrefix(javaScriptIdPrefix);
  }


  public String getJavaScriptIdPrefix()
  {
    return service.getJavaScriptIdPrefix();
  }


  public String getCssId(Class clazz)
  {
    return service.getCssId(clazz);
  }
}
