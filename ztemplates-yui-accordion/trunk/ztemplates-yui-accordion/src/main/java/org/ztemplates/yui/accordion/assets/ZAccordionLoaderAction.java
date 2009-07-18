package org.ztemplates.yui.accordion.assets;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.request.actions.ZResourceLoaderAction;

@ZMatch(ZAccordionLoaderAction.LOADER_URL_PREFIX + "*{resourcePath}")
public class ZAccordionLoaderAction extends ZResourceLoaderAction
{
  public static final String LOADER_URL_PREFIX = "/accordionloader";
}
