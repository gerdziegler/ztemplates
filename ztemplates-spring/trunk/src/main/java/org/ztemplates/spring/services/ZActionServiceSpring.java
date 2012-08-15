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
package org.ztemplates.spring.services;

import java.util.Map;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZMatch.Protocol;
import org.ztemplates.web.ZIActionService;
import org.ztemplates.web.ZTemplates;

/**
 * Spring proxy for ZIActionService
 * 
 * @author gerd
 * 
 */

@Component(ZIActionService.SPRING_NAME)
@Scope("request")
public class ZActionServiceSpring implements ZIActionService
{
  private final ZIActionService service;


  public ZActionServiceSpring()
  {
    this.service = ZTemplates.getActionService();
  }


  public Object process(ZMatch.Protocol protocol, String url, Map<String, String[]> paramMap) throws Exception
  {
    return service.process(protocol, url, paramMap);
  }


  public String createUrl(Object action) throws Exception
  {
    return service.createUrl(action);
  }


  public String createUrl(ZMatch.Protocol requiresProtocol, Object action) throws Exception
  {
    return service.createUrl(requiresProtocol, action);
  }


  public String createNestedUrl(Object nestedAction) throws Exception
  {
    return service.createNestedUrl(nestedAction);
  }


  public String createUrl(Protocol requiresProtocol, String path) throws Exception
  {
    return service.createUrl(requiresProtocol, path);
  }
}
