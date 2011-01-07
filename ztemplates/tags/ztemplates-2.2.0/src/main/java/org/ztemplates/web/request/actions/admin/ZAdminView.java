/*
 * Copyright 2008 Gerd Ziegler (www.gerdziegler.de) Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. 09.02.2008 @author
 * www.gerdziegler.de
 */
package org.ztemplates.web.request.actions.admin;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZTemplatesRenderer;

@ZRenderer(ZTemplatesRenderer.class)
public class ZAdminView
{
  private String info;


  @ZExpose
  public String getInfo()
  {
    return info;
  }


  public void setInfo(String info)
  {
    this.info = info;
  }
}
