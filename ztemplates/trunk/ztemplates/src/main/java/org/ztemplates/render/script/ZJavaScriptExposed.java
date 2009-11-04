package org.ztemplates.render.script;

import java.io.Serializable;

public class ZJavaScriptExposed implements Comparable<ZJavaScriptExposed>, Serializable
{
  private final int index;

  private final String url;

  private final String prefix;

  private final boolean standalone;

  private final boolean merge;


  public ZJavaScriptExposed(int index, String prefix, String url, boolean standalone, boolean merge)
  {
    super();
    this.index = index;
    this.prefix = prefix;
    this.url = url;
    this.standalone = standalone;
    this.merge = merge;
  }


  public ZJavaScriptExposed(int index, String url)
  {
    this(index, "", url, false, true);
  }


  public int compareTo(ZJavaScriptExposed o)
  {
    if (index < o.index)
    {
      return -1;
    }
    if (index > o.index)
    {
      return 1;
    }
    return url.compareTo(o.url);
  }


  public int getIndex()
  {
    return index;
  }


  public String getUrl()
  {
    return url;
  }


  @Override
  public boolean equals(Object obj)
  {
    ZJavaScriptExposed o = (ZJavaScriptExposed) obj;
    return index == o.index && url.equals(o.url);
  }


  @Override
  public int hashCode()
  {
    return url.hashCode();
  }


  public String getPrefix()
  {
    return prefix;
  }


  public boolean isStandalone()
  {
    return standalone;
  }


  public boolean isMerge()
  {
    return merge;
  }
}
