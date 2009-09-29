package org.ztemplates.examples.formprocessing.layers.passive.ui.ui.wait.assets;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.request.actions.ZResourceLoaderAction;

@ZMatch(ZFormWaitLoaderAction.LOADER_URL_PREFIX + "*{resourcePath}")
public class ZFormWaitLoaderAction extends ZResourceLoaderAction
{
  public static final String LOADER_URL_PREFIX = "/ztemplates-ui-form/wait/assets";
  public static final String LOADER_SCRIPT = "/ZFormWait.js";
}
