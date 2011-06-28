package org.ztemplates.web.script.css;

import java.util.List;

/**
 * 
 * @author gerd
 * 
 */
public interface ZICssPreprocessor
{
  public List<String> preprocessCss(List<String> css);
}
