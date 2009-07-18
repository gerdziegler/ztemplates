package org.ztemplates.web.ui.form.state.assets;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.request.actions.ZResourceLoaderAction;

@ZMatch(ZInputStateLoaderAction.LOADER_URL_PREFIX + "*{resourcePath}")
public class ZInputStateLoaderAction extends ZResourceLoaderAction
{
  public static final String LOADER_URL_PREFIX = "/ztemplates-ui-form/state/assets";

  public static final String FORM_INPUT_STATE_JS = "/ZFormInputState.js";

  public static final String FORM_STATE_HIGHLIGHT_JS = "/ZFormStateHighlight.js";
}
