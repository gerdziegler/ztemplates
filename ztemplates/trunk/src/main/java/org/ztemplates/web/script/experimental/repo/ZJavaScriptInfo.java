package org.ztemplates.web.script.experimental.repo;

import java.util.Comparator;

import org.ztemplates.web.script.experimental.repo.impl.ZDefaultVersionComparator;

public class ZJavaScriptInfo
{
  private final String url;

  private final ZJavaScriptId id;

  private boolean standalone = false;

  private final Comparator<String> versionComparator = new ZDefaultVersionComparator();


  /**
   * @param url
   * @param name
   * @param version
   */
  public ZJavaScriptInfo(String url, ZJavaScriptId id)
  {
    super();
    this.url = url;
    this.id = id;
  }


  @Override
  public String toString()
  {
    return "[" + url + " " + id + "]";
  }


  /**
   * @param url
   * @param id
   * @param standalone
   */
  public ZJavaScriptInfo(String url, ZJavaScriptId id, boolean standalone)
  {
    super();
    this.url = url;
    this.id = id;
    this.standalone = standalone;
  }


  /**
   * @return the id
   */
  public ZJavaScriptId getId()
  {
    return id;
  }


  /**
   * @return the url
   */
  public String getUrl()
  {
    return url;
  }


  /**
   * @return the standalone
   */
  public boolean isStandalone()
  {
    return standalone;
  }


  /**
   * @param standalone
   *          the standalone to set
   */
  public void setStandalone(boolean standalone)
  {
    this.standalone = standalone;
  }


  /**
   * @return the versionComparator
   */
  public Comparator<String> getVersionComparator()
  {
    return versionComparator;
  }
}
