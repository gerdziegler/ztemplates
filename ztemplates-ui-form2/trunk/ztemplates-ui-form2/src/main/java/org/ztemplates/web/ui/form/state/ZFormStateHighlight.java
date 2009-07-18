/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
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
import org.ztemplates.web.ui.form.state.assets.ZInputStateLoaderAction;

@ZRenderer(ZVelocityRenderer.class)
@ZScript(javaScript =
{
    @ZJavaScript(prefix = JQueryLoaderAction.LOADER_URL_PREFIX, value = JQueryLoaderAction.JQUERY_MIN_JS, standalone = JQueryLoaderAction.STANDALONE, merge = JQueryLoaderAction.MERGE),
    @ZJavaScript(prefix = ZFormScriptLoaderAction.PREFIX, value = ZFormScriptLoaderAction.FORM_SCRIPT),
    @ZJavaScript(prefix = ZInputStateLoaderAction.LOADER_URL_PREFIX, value = ZInputStateLoaderAction.FORM_STATE_HIGHLIGHT_JS)
})
public class ZFormStateHighlight
{
  private final String displayId;

  private final String backgroundColor;


  public ZFormStateHighlight(String displayId, String backgroundColor)
  {
    this.displayId = displayId;
    this.backgroundColor = backgroundColor;
  }


  public ZFormStateHighlight(String displayId)
  {
    this(displayId, "#c33");
  }


  public ZFormStateHighlight(ZFormState formState, String backgroundColor)
  {
    this(formState.getDisplayId(), backgroundColor);
  }


  public ZFormStateHighlight(ZFormState formState)
  {
    this(formState, "#c33");
  }


  @ZExpose
  public String getBackgroundColor()
  {
    return backgroundColor;
  }


  @ZExpose
  public String getDisplayId()
  {
    return displayId;
  }
}
