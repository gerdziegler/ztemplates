package org.ztemplates.web;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassPathFilter;

/**
 * Default classpath filter behavior accepts only ztemplates classes and skips
 * all other. Also scans only web-inf/lib and web-inf/classes. See
 * http://www.ztemplates.org/Edit.jsp?page=Install for how to override this
 * behavior.
 * 
 * @author gerdziegler.de
 * 
 */
public abstract class ZDefaultClassPathFilter implements ZIClassPathFilter
{
  protected static final Logger log = Logger.getLogger(ZDefaultClassPathFilter.class);


  public boolean acceptClass(String name) throws Exception
  {
    if (name.contains("ztemplates"))
    {
      if (log.isDebugEnabled())
      {
        log.debug("accept class " + name);
      }
      return true;
    }
    else
    {
      if (log.isDebugEnabled())
      {
        log.debug("skip class " + name);
      }
      return false;
    }
  }


  public boolean acceptClasspathPart(String name) throws Exception
  {
    if (name.startsWith("/WEB-INF/classes/") || name.startsWith("/WEB-INF/lib/"))
    {
      if (log.isInfoEnabled())
      {
        log.info("accept classpath part " + name);
      }
      return true;
    }
    else
    {
      if (log.isDebugEnabled())
      {
        log.debug("skip classpath part " + name);
      }
      return false;
    }
  }


  public boolean acceptResource(String resource) throws Exception
  {
    return true;
  }
}
