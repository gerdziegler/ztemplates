package org.ztemplates.render.script;

import java.io.Serializable;

public class ZCssExposed implements Comparable<ZCssExposed>, Serializable
{
  private final int index;

  private final String url;

  private final String prefix;

  private final boolean merge;


  public ZCssExposed(int index, String javaScript, String prefix, boolean merge)
  {
    super();
    this.index = index;
    this.url = javaScript;
    this.prefix = prefix;
    this.merge = merge;
  }


  public int compareTo(ZCssExposed o)
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
    ZCssExposed o = (ZCssExposed) obj;
    return url.equals(o.url);
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


  public boolean isMerge()
  {
    return merge;
  }
}
