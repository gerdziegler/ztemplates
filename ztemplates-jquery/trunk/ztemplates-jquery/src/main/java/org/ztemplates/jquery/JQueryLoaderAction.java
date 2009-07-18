package org.ztemplates.jquery;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.request.actions.ZResourceLoaderAction;

@ZMatch(JQueryLoaderAction.LOADER_URL_PREFIX + "*{resourcePath}")
public class JQueryLoaderAction extends ZResourceLoaderAction
{
  public static final String LOADER_URL_PREFIX = "/ztemplates/jquery";

  protected static Logger log = Logger.getLogger(JQueryLoaderAction.class);

  public static final boolean STANDALONE = true;

  public static final boolean MERGE = false;

  public static final String JQUERY_MIN_JS = "/jquery-1.3.2.min.js";

  public static final String JQUERY_MIN_JS_EXTERNAL = "http://ajax.googleapis.com/ajax/libs/jquery/1.3.2/jquery.min.js";


  public JQueryLoaderAction()
  {
    super("/jquery");
  }
}
