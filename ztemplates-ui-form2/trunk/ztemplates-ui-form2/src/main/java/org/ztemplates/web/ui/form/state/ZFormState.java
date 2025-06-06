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
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ui.form.script.assets.ZFormScriptLoaderAction;

@ZRenderer(ZVelocityRenderer.class)
@ZScript(javaScript =
{
    @ZJavaScript(value = JQueryLoaderAction.JQUERY_MIN_JS, standalone = JQueryLoaderAction.STANDALONE, merge = JQueryLoaderAction.MERGE),
    @ZJavaScript(ZFormScriptLoaderAction.FORM_SCRIPT)
})
public final class ZFormState
{
  private final String formId;

  private final String displayId;


  public ZFormState(String formId, String displayId)
  {
    this.formId = formId;
    this.displayId = displayId;
  }


  @ZExpose
  public String getFormId()
  {
    return formId;
  }


  @ZExpose
  public String getDisplayId()
  {
    return displayId;
  }
}
