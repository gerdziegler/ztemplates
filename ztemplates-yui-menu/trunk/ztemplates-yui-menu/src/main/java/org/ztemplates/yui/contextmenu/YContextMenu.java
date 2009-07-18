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
 * 07.03.2008
 * @author www.gerdziegler.de
 */
package org.ztemplates.yui.contextmenu;

import org.json.JSONObject;
import org.ztemplates.render.ZCss;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.yui.ZYuiLoaderAction;

@ZRenderer(ZVelocityRenderer.class)
@ZScript(css =
{
    @ZCss(prefix = ZYuiLoaderAction.LOADER_URL_PREFIX, value = ZYuiLoaderAction.FONTS_MIN_CSS),
    @ZCss(prefix = ZYuiLoaderAction.LOADER_URL_PREFIX, value = ZYuiLoaderAction.MENU_SAM_CSS)
}, javaScript =
{
    @ZJavaScript(prefix = ZYuiLoaderAction.LOADER_URL_PREFIX, value = ZYuiLoaderAction.YAHOO_DOM_EVENT_JS),
    @ZJavaScript(prefix = ZYuiLoaderAction.LOADER_URL_PREFIX, value = ZYuiLoaderAction.CONTAINER_MIN_JS),
    @ZJavaScript(prefix = ZYuiLoaderAction.LOADER_URL_PREFIX, value = ZYuiLoaderAction.MENU_MIN_JS)
})
public class YContextMenu
{
  public static final String PROP_TRIGGER_String = "trigger";

  private final String id;

  private final JSONObject properties = new JSONObject();



  public YContextMenu(String eventSourceId) throws Exception
  {
    this(ZTemplates.getRenderService().createJavaScriptId(), eventSourceId);
  }


  public YContextMenu(final String id, String eventSourceId) throws Exception
  {
    this.id = id;
    properties.put("autosubmenudisplay", true);
    properties.put("hidedelay", 750);
    properties.put("lazyload", true);
    properties.put(PROP_TRIGGER_String, eventSourceId);
  }


  @ZExpose
  public JSONObject getProperties()
  {
    return properties;
  }


  @ZExpose
  public final String getId()
  {
    return id;
  }
}
