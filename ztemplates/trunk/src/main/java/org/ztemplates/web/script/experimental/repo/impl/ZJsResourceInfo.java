package org.ztemplates.web.script.experimental.repo.impl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.ztemplates.web.script.experimental.repo.ZJavaScriptId;
import org.ztemplates.web.script.experimental.repo.ZJavaScriptInfo;

/**
 * definition of a javascript resource
 * 
 * @author gerd
 * 
 */
public class ZJsResourceInfo
{
  private final ZJavaScriptId id;

  /**
   * comparator to use when comparing versions (of resources with the same id)
   */
  private Comparator<String> versionComparator;

  /**
   * If set to true will be considerered as having no dependency to other
   * javascript resources, so order is not important
   * 
   * @return
   */
  private final List<ZJavaScriptId> dependsOn = new ArrayList<ZJavaScriptId>();

  /**
   * 
   */
  private final List<ZJavaScriptInfo> locations = new ArrayList<ZJavaScriptInfo>();

  /**
   * If set to true can be merged with other scripts if possible without
   * changing the order. reasons for not merging could be resource paths.
   * 
   * @return
   */
  private final boolean mergeAllowed;


  /**
   * @param id
   * @param version
   * @param url
   * @param standalone
   * @param merge
   */
  public ZJsResourceInfo(ZJavaScriptId id, Comparator<String> versionComparator, boolean mergeAllowed)
  {
    super();
    this.id = id;
    this.versionComparator = versionComparator;
    this.mergeAllowed = mergeAllowed;
  }


  /**
   * @return the id
   */
  public ZJavaScriptId getId()
  {
    return id;
  }


  /*
   * (non-Javadoc)
   * 
   * @see
   * org.ztemplates.web.script.repo.ZIJavaScriptResource#getVersionComparator()
   */
  public Comparator<String> getVersionComparator()
  {
    return versionComparator;
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.script.repo.ZIJavaScriptResource#isStandalone()
   */
  public List<ZJavaScriptId> getDependsOn()
  {
    return dependsOn;
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.script.repo.ZIJavaScriptResource#isMerge()
   */
  public boolean isMergeAllowed()
  {
    return mergeAllowed;
  }


  /**
   * @return the locations
   */
  public List<ZJavaScriptInfo> getLocations()
  {
    return locations;
  }

}
