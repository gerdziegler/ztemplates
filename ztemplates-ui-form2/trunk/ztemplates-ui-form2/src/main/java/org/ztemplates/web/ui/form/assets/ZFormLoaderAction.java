package org.ztemplates.web.ui.form.assets;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.request.actions.ZResourceLoaderAction;

@ZMatch(ZFormLoaderAction.LOADER_URL_PREFIX + "*{resourcePath}")
public class ZFormLoaderAction extends ZResourceLoaderAction
{
  public static final String LOADER_URL_PREFIX = "/ztemplates-ui-form/assets";
}
