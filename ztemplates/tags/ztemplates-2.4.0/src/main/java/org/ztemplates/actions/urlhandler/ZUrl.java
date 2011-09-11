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

package org.ztemplates.actions.urlhandler;

import java.io.Serializable;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ztemplates.actions.security.ZRoles;

public class ZUrl implements Serializable
{
  protected static Logger log = Logger.getLogger(ZUrl.class);

  private final Map<String, String[]> parameterMap;

  private final String url;

  private final ZRoles roles;


  public ZUrl(String url,
      Map<String, String[]> parameterMap,
      ZRoles roles)
  {
    this.url = url;
    this.parameterMap = parameterMap;
    this.roles = roles;
  }


  public Map<String, String[]> getParameterMap()
  {
    return parameterMap;
  }


  public String getUrl()
  {
    return url;
  }


  public ZRoles getRoles()
  {
    return roles;
  }
}
