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
package org.ztemplates.test.mock;

import org.ztemplates.render.ZIRenderedObject;
import org.ztemplates.web.ZIActiveView;
import org.ztemplates.web.ZIRenderService;

public class ZMockRenderService implements ZIRenderService
{
  private int cnt = 0;

  private String javaScriptIdPrefix = "zid";


  public String createJavaScriptId()
  {
    return javaScriptIdPrefix + cnt++;
  }


  public String getCssId(Class clazz)
  {
    return clazz.getSimpleName();
  }


  public String render(Object obj) throws Exception
  {
    return null;
  }


  public String renderZtemplatesCss() throws Exception
  {
    return null;
  }


  public ZIRenderedObject prerender(Object obj) throws Exception
  {
    return null;
  }


  public String getJavaScriptIdPrefix()
  {
    return javaScriptIdPrefix;
  }


  public void setJavaScriptIdPrefix(String javaScriptIdPrefix)
  {
    this.javaScriptIdPrefix = javaScriptIdPrefix;

  }


  public String createJavaScriptId(String prefix)
  {
    // TODO Auto-generated method stub
    return null;
  }


  public <T extends ZIActiveView> T createActiveView(Class<T> clazz) throws Exception
  {
    // TODO Auto-generated method stub
    return null;
  }
}
