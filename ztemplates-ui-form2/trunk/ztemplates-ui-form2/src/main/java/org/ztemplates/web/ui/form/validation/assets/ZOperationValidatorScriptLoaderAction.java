package org.ztemplates.web.ui.form.validation.assets;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.request.actions.ZResourceLoaderAction;

@ZMatch(ZOperationValidatorScriptLoaderAction.PREFIX + "*{resourcePath}")
public class ZOperationValidatorScriptLoaderAction extends ZResourceLoaderAction
{
  public static final String PREFIX = "/ztemplates-ui-form/property/validator/assets";

  public static final String OPERATION_VALIDATOR_JS = "/ZOperationValidatorScript.js";
}
