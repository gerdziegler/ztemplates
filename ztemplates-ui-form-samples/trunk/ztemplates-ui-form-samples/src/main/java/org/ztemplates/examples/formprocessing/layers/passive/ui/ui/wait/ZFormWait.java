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
package org.ztemplates.examples.formprocessing.layers.passive.ui.ui.wait;

import org.ztemplates.examples.formprocessing.layers.passive.ui.ui.wait.assets.ZFormWaitLoaderAction;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ui.form.script.assets.ZFormScriptLoaderAction;
import org.ztemplates.yui.ZYuiLoaderAction;

@ZRenderer(ZVelocityRenderer.class)
@ZScript(javaScript =
{
    @ZJavaScript(prefix = ZYuiLoaderAction.LOADER_URL_PREFIX, value = ZYuiLoaderAction.YAHOO_DOM_EVENT_JS),
    @ZJavaScript(prefix = ZYuiLoaderAction.LOADER_URL_PREFIX, value = ZYuiLoaderAction.UTILITIES_JS),
    @ZJavaScript(prefix = ZYuiLoaderAction.LOADER_URL_PREFIX, value = ZYuiLoaderAction.CONTAINER_MIN_JS),
    @ZJavaScript(prefix = ZFormScriptLoaderAction.PREFIX, value = ZFormScriptLoaderAction.FORM_SCRIPT),
    @ZJavaScript(prefix = ZFormWaitLoaderAction.LOADER_URL_PREFIX, value = ZFormWaitLoaderAction.LOADER_SCRIPT)
})
public class ZFormWait
{
  private final String formId;

  private final String text;

  private final String imgUrl;


  public ZFormWait(String formId, String text, String imgUrl)
  {
    super();
    this.formId = formId;
    this.text = text;
    this.imgUrl = imgUrl;
  }


  public ZFormWait(String formId, String text)
  {
    this(formId,
        text,
        "http://us.i1.yimg.com/us.yimg.com/i/us/per/gr/gp/rel_interstitial_loading.gif");
  }


  @ZExpose
  public String getFormId()
  {
    return formId;
  }


  @ZExpose
  public String getText()
  {
    return text;
  }


  @ZExpose
  public String getImgUrl()
  {
    return imgUrl;
  }
}
