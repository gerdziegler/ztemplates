package org.ztemplates.web.script.experimental.repo;

import java.util.ArrayList;
import java.util.List;

/**
 * definition of a javascript resource
 * 
 * @author gerd
 * 
 */
public abstract class ZJavaScriptDependencyInfo
{
  /**
   * the id identifies the lib and version
   */
  private final ZJavaScriptId id;

  /**
   * if known, dependencies can be listed here, empty could mean unknown.
   */
  private final List<ZJavaScriptId> dependsOn = new ArrayList<ZJavaScriptId>();


  /**
   * @param id
   * @param version
   * @param url
   * @param standalone
   * @param merge
   */
  public ZJavaScriptDependencyInfo(ZJavaScriptId id)
  {
    super();
    this.id = id;
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
   * @see org.ztemplates.web.script.repo.ZIJavaScriptResource#isStandalone()
   */
  public List<ZJavaScriptId> getDependsOn()
  {
    return dependsOn;
  }
}
