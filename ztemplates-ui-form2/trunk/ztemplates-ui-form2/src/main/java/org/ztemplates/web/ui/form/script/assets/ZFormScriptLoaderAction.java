package org.ztemplates.web.ui.form.script.assets;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.request.actions.ZResourceLoaderAction;

@ZMatch(ZFormScriptLoaderAction.PREFIX + "*{resourcePath}")
public class ZFormScriptLoaderAction extends ZResourceLoaderAction
{
  public static final String PREFIX = "/ztemplates-ui-form/script/assets";

  public static final String FORM_SCRIPT = "/ZFormScript.js";

  public static final String FORM_TEXT_SCRIPT = "/ZFormText.js";

  public static final String FORM_SELECT_SCRIPT = "/ZFormSelect.js";

  public static final String FORM_CHECKBOX_SCRIPT = "/ZFormCheckbox.js";

  public static final String FORM_RADIO_SCRIPT = "/ZFormRadio.js";

  public static final String FORM_SUBMIT_SCRIPT = "/ZFormSubmit.js";
}
