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
package org.ztemplates.web.ui.form.state;

import org.ztemplates.jquery.JQueryLoaderAction;
import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ZIRenderService;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.ui.form.script.assets.ZFormScriptLoaderAction;
import org.ztemplates.web.ui.form.state.assets.ZInputStateLoaderAction;

@ZRenderer(ZVelocityRenderer.class)
@ZScript(javaScript =
{
    @ZJavaScript(prefix = JQueryLoaderAction.LOADER_URL_PREFIX, value = JQueryLoaderAction.JQUERY_MIN_JS, standalone = JQueryLoaderAction.STANDALONE, merge = JQueryLoaderAction.MERGE),
    @ZJavaScript(prefix = ZFormScriptLoaderAction.PREFIX, value = ZFormScriptLoaderAction.FORM_SCRIPT),
    @ZJavaScript(prefix = ZInputStateLoaderAction.LOADER_URL_PREFIX, value = ZInputStateLoaderAction.FORM_INPUT_STATE_JS)
})
public class ZFormInputState
{
  private final String inputId;

  private final String formId;

  private final String propertyNames;


  public ZFormInputState(String formId, final ZProperty... prop)
  {
    ZIRenderService rs = ZTemplates.getRenderService();
    inputId = rs.createJavaScriptId();
    this.formId = formId;
    StringBuffer sb = new StringBuffer();
    boolean first = true;
    for (ZProperty p : prop)
    {
      if (!first)
      {
        sb.append(',');
        first = false;
      }
      sb.append(p.getName());
    }
    propertyNames = sb.toString();
  }


  @ZExpose
  public String getInputId()
  {
    return inputId;
  }


  @ZExpose
  public String getFormId()
  {
    return formId;
  }


  @ZExpose
  public String getPropertyNames()
  {
    return propertyNames;
  }
}
