package org.ztemplates.web.script.javascript;

import java.util.List;

/**
 * by default nothing is replaced
 */
public class ZDefaultJavaScriptPreprocessor implements ZIJavaScriptPreprocessor
{

  public List<String> preprocessJavaScript(List<String> javaScript)
  {
    return javaScript;
  }

}
