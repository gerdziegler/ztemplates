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
package org.ztemplates.spring.services;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.ztemplates.web.ZIMessageService;
import org.ztemplates.web.ZTemplates;

/**
 * Spring proxy for ZIMessageService
 * 
 * @author gerd
 * 
 */
@Component(ZIMessageService.SPRING_NAME)
@Scope("request")
public class ZMessageServiceWebSpring implements ZIMessageService
{
  static final Logger log = Logger.getLogger(ZMessageServiceWebSpring.class);

  private final ZIMessageService service;


  public ZMessageServiceWebSpring()
  {
    this.service = ZTemplates.getMessageService();
  }


  public String getMessage(String bundleName, String messageId, Object... parameters)
  {
    return service.getMessage(bundleName, messageId, parameters);
  }
}
