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
 * 09.01.2008
 * @author www.gerdziegler.de
 */
package org.ztemplates.web;

import java.util.Map;

import org.ztemplates.actions.ZMatch;

public interface ZIActionService extends ZIService
{
  public static final String SPRING_NAME = "ZIActionService";


  public Object process(ZMatch.Protocol protocol, String url, Map<String, String[]> paramMap) throws Exception;


  /**
   * Creates a url to the specified action object
   * 
   * @param action
   * @return
   * @throws Exception
   */
  public String createUrl(Object action) throws Exception;


  public String createUrl(ZMatch.Protocol requiresProtocol, String path) throws Exception;


  public String createUrl(ZMatch.Protocol requiresProtocol, Object action) throws Exception;


  /**
   * Creates a url to the specified nested action object, the url is relative to
   * the current nested action. Can only be while processing a nested action.
   * 
   * @param nestedAction
   * @return
   * @throws Exception
   */
  public String createNestedUrl(Object nestedAction) throws Exception;

}
