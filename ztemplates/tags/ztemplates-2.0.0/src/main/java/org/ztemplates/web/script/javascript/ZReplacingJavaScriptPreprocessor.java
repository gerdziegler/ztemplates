package org.ztemplates.web.script.javascript;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * replaces Strings
 */
public abstract class ZReplacingJavaScriptPreprocessor implements ZIJavaScriptPreprocessor
{
  private final Map<String, String> replaceMap = new HashMap<String, String>();


  public List<String> preprocessJavaScript(List<String> javaScript)
  {
    List<String> ret = new ArrayList<String>();
    for (int i = 0; i < javaScript.size(); i++)
    {
      String oldVal = javaScript.get(i);
      String newVal = replaceMap.get(javaScript);
      if (newVal == null)
      {
        newVal = oldVal;
      }
      ret.add(newVal);
    }
    return ret;
  }


  protected void addReplace(String oldVal, String newVal)
  {
    replaceMap.put(oldVal, newVal);
  }

}
