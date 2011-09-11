package org.ztemplates.validation.assets;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.request.actions.ZResourceLoaderAction;

@ZMatch(ZRegexValidatorScriptLoaderAction.PREFIX + "*{resourcePath}")
public class ZRegexValidatorScriptLoaderAction extends ZResourceLoaderAction
{
  protected static final String PREFIX = "/ztemplates/property/validator/assets";

  public static final String REGEX_VALIDATOR_JS = PREFIX + "/ZRegexValidatorScript.js";

  public static final String OPERATION_VALIDATOR_JS = PREFIX + "/ZOperationValidatorScript.js";
}
