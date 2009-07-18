package org.ztemplates.yui;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.request.actions.ZResourceLoaderAction;

@ZMatch(ZYuiLoaderAction.LOADER_URL_PREFIX + "*{resourcePath}")
public class ZYuiLoaderAction extends ZResourceLoaderAction
{
  public static final String LOADER_URL_PREFIX = "/ztemplates/yui";

  public static final String ANIMATION_MIN_JS = "/animation/animation-min.js";

  public static final String AUTOCOMPLETE_MIN_JS = "/autocomplete/autocomplete-min.js";

  public static final String AUTOCOMPLETE_SAM_CSS = "/autocomplete/assets/skins/sam/autocomplete.css";

  public static final String CONNECTION_MIN_JS = "/connection/connection-min.js";

  public static final String CONTAINER_MIN_JS = "/container/container-min.js";

  public static final String CONTAINER_SAM_CSS = "/container/assets/skins/sam/container.css";

  public static final String DATASOURCE_MIN_JS = "/datasource/datasource-min.js";

  public static final String FONTS_MIN_CSS = "/fonts/fonts-min.css";

  public static final String GET_MIN_JS = "/get/get-min.js";

  public static final String JSON_MIN_JS = "/json/json-min.js";

  public static final String MENU_MIN_JS = "/menu/menu-min.js";

  public static final String MENU_SAM_CSS = "/menu/assets/skins/sam/menu.css";

  public static final String PAGINATOR_SAM_CSS = "/paginator/assets/skins/sam/paginator.css";

  public static final String PAGINATOR_MIN_JS = "/paginator/paginator-min.js";

  public static final String ELEMENT_MIN_JS = "/element/element-min.js";

  public static final String UTILITIES_JS = "/utilities/utilities.js";

  public static final String YAHOO_DOM_EVENT_JS = "/yahoo-dom-event/yahoo-dom-event.js";

  protected static Logger log = Logger.getLogger(ZYuiLoaderAction.class);


  public ZYuiLoaderAction()
  {
    super("/yui/build");
  }
}
