package org.ztemplates.yui;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.request.actions.ZResourceLoaderAction;

@ZMatch(ZYuiLoaderAction.LOADER_URL_PREFIX + "*{resourcePath}")
public class ZYuiLoaderAction extends ZResourceLoaderAction
{
  public static final String LOADER_URL_PREFIX = "/ztemplates/yui";

  public static final String ANIMATION_MIN_JS = LOADER_URL_PREFIX + "/animation/animation-min.js";

  public static final String AUTOCOMPLETE_MIN_JS = LOADER_URL_PREFIX + "/autocomplete/autocomplete-min.js";

  public static final String AUTOCOMPLETE_SAM_CSS = LOADER_URL_PREFIX + "/autocomplete/assets/skins/sam/autocomplete.css";

  public static final String CONNECTION_MIN_JS = LOADER_URL_PREFIX + "/connection/connection-min.js";

  public static final String CONTAINER_MIN_JS = LOADER_URL_PREFIX + "/container/container-min.js";

  public static final String CONTAINER_SAM_CSS = LOADER_URL_PREFIX + "/container/assets/skins/sam/container.css";

  public static final String DATASOURCE_MIN_JS = LOADER_URL_PREFIX + "/datasource/datasource-min.js";

  public static final String FONTS_MIN_CSS = LOADER_URL_PREFIX + "/fonts/fonts-min.css";

  public static final String GET_MIN_JS = LOADER_URL_PREFIX + "/get/get-min.js";

  public static final String JSON_MIN_JS = LOADER_URL_PREFIX + "/json/json-min.js";

  public static final String MENU_MIN_JS = LOADER_URL_PREFIX + "/menu/menu-min.js";

  public static final String MENU_SAM_CSS = LOADER_URL_PREFIX + "/menu/assets/skins/sam/menu.css";

  public static final String PAGINATOR_SAM_CSS = LOADER_URL_PREFIX + "/paginator/assets/skins/sam/paginator.css";

  public static final String PAGINATOR_MIN_JS = LOADER_URL_PREFIX + "/paginator/paginator-min.js";

  public static final String ELEMENT_MIN_JS = LOADER_URL_PREFIX + "/element/element-min.js";

  public static final String UTILITIES_JS = LOADER_URL_PREFIX + "/utilities/utilities.js";

  public static final String YAHOO_DOM_EVENT_JS = LOADER_URL_PREFIX + "/yahoo-dom-event/yahoo-dom-event.js";

  protected static Logger log = Logger.getLogger(ZYuiLoaderAction.class);


  public ZYuiLoaderAction()
  {
    super("/yui/build");
  }
}
