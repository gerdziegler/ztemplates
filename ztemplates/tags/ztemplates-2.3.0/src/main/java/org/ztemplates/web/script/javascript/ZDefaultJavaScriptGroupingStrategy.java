package org.ztemplates.web.script.javascript;

import java.util.ArrayList;
import java.util.List;

public class ZDefaultJavaScriptGroupingStrategy implements ZIJavaScriptGroupingStrategy
{
  public List<List<String>> group(List<String> javaScriptExposed)
  {
    List<List<String>> ret = new ArrayList<List<String>>();

    List<String> merge = null;
    for (String s : javaScriptExposed)
    {
      // String url = s.getUrl();
      // if (url.charAt(0) == '/' && s.isMerge())
      // {
      // if (merge == null)
      // {
      // merge = new ArrayList<ZJavaScriptExposed>();
      // ret.add(merge);
      // }
      // merge.add(s);
      // }
      // else
      // {
      merge = new ArrayList<String>();
      ret.add(merge);
      merge.add(s);
      merge = null;
      // }
    }

    return ret;
  }
}
