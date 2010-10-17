package org.ztemplates.web.script.javascript;

import java.util.ArrayList;
import java.util.List;

public class ZJavaScriptTag
{
  private final List<String> mergedFrom = new ArrayList<String>();

  private final String url;


  public ZJavaScriptTag(String url)
  {
    super();
    this.url = url;
  }


  public String getUrl()
  {
    return url;
  }


  public List<String> getMergedFrom()
  {
    return mergedFrom;
  }
}
