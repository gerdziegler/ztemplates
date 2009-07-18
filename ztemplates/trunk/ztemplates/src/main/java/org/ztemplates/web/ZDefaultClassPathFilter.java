package org.ztemplates.web;

import org.zclasspath.ZIClassPathFilter;

public class ZDefaultClassPathFilter implements ZIClassPathFilter
{
  public boolean acceptClass(String name) throws Exception
  {
    if (name.startsWith("org.apache.") || name.startsWith("freemarker.")
        || name.startsWith("flex.") || name.startsWith("java.") || name.startsWith("javax.")
        || name.startsWith("org.junit") || name.startsWith("org.json")
        || name.startsWith("org.jgrapht") || name.startsWith("org.jfree"))
    {
      return false;
    }
    return true;
  }


  public boolean acceptClasspathPart(String name) throws Exception
  {
    if (name.endsWith(".jar")
        && (name.startsWith("jgrapht") || name.startsWith("junit") || name.startsWith("log4j")
            || name.startsWith("json") || name.startsWith("servlet") || name.startsWith("velocity")
            || name.startsWith("freemarker") || name.startsWith("yui") || name
            .startsWith("commons")))
    {
      return false;
    }
    return true;
  }


  public boolean acceptResource(String resource) throws Exception
  {
    return false;
  }
}
