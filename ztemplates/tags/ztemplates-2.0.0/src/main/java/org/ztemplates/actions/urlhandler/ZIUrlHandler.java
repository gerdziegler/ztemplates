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
package org.ztemplates.actions.urlhandler;

import java.util.Map;

public interface ZIUrlHandler
{
  public Object process(String url) throws Exception;


  public Object process(String url, Map<String, String[]> paramMap) throws Exception;


  public void printInfo(StringBuffer sb);
}
