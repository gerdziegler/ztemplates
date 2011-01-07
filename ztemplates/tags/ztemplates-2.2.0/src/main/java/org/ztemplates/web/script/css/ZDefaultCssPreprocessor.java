package org.ztemplates.web.script.css;

import java.util.List;

/**
 * by default nothing is replaced
 */
public class ZDefaultCssPreprocessor implements ZICssPreprocessor
{

  public List<String> preprocessCss(List<String> css)
  {
    return css;
  }

}
