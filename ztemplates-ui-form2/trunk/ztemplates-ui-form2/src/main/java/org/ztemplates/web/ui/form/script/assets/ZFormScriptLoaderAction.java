package org.ztemplates.web.ui.form.script.assets;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.request.actions.ZResourceLoaderAction;

@ZMatch(ZFormScriptLoaderAction.PREFIX + "*{resourcePath}")
public class ZFormScriptLoaderAction extends ZResourceLoaderAction
{
  protected static final String PREFIX = "/ztemplates-ui-form/script/assets";

  public static final String FORM_SCRIPT = PREFIX + "/ZFormScript.js";

  public static final String FORM_TEXT_SCRIPT = PREFIX + "/ZFormText.js";

  public static final String FORM_SELECT_SCRIPT = PREFIX + "/ZFormSelect.js";

  public static final String FORM_CHECKBOX_SCRIPT = PREFIX + "/ZFormCheckbox.js";

  public static final String FORM_RADIO_SCRIPT = PREFIX + "/ZFormRadio.js";

  public static final String FORM_SUBMIT_SCRIPT = PREFIX + "/ZFormSubmit.js";
}
