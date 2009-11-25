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
package org.ztemplates.yui.menu;

import java.util.ArrayList;
import java.util.List;

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
    @ZCss(ZYuiLoaderAction.FONTS_MIN_CSS),
    @ZCss(ZYuiLoaderAction.MENU_SAM_CSS)
}, javaScript =
{
    @ZJavaScript(ZYuiLoaderAction.YAHOO_DOM_EVENT_JS),
    @ZJavaScript(ZYuiLoaderAction.CONTAINER_MIN_JS),
    @ZJavaScript(ZYuiLoaderAction.MENU_MIN_JS)
})
public class YMenuBar
{
  public static final String PROP_AUTOSUBMENUDISPLAY_boolean = "autosubmenudisplay";

  public static final String PROP_HIDEDELAY_long = "hidedelay";

  public static final String PROP_LAZYLOAD_boolean = "lazyload";

  public static final String PROP_POSITION_String = "position";

  public static final String POSITION_STATIC = "static";

  public static final String visible_boolean = "visible";

  public static final String zIndex_int = "zIndex";
  
  public static final String maxheight_int = "maxheight";
  
  public static final String minscrollheight_int = "minscrollheight";
    
    

  private final String id;

  private final List<YMenuItem> menuItems;

  private final JSONObject properties = new JSONObject();


  public YMenuBar(String id, List<YMenuItem> menuItems) throws Exception
  {
    super();
    this.id = id;
    this.menuItems = menuItems;

    properties.put(PROP_AUTOSUBMENUDISPLAY_boolean, true);
    properties.put(PROP_HIDEDELAY_long, 750);
    properties.put(PROP_LAZYLOAD_boolean, true);
    properties.put(PROP_POSITION_String, POSITION_STATIC);
  }


  public YMenuBar() throws Exception
  {
    this(ZTemplates.getRenderService().createJavaScriptId(), new ArrayList<YMenuItem>());
  }


  public YMenuBar(List<YMenuItem> menuItems) throws Exception
  {
    this(ZTemplates.getRenderService().createJavaScriptId(), menuItems);
  }


  @ZExpose
  public final String getId()
  {
    return id;
  }


  @ZExpose
  public JSONObject getProperties()
  {
    return properties;
  }


  @ZExpose
  public List<YMenuItem> getMenuItems()
  {
    return menuItems;
  }
}
