package org.ztemplates.web;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.zclasspath.ZClassPathItemFile;
import org.zclasspath.ZClassPathItemWebapp;
import org.zclasspath.ZIClassPathItem;

public class ZClassPathItemResource implements ZIClassPathItem
{
  private static final Logger log = Logger.getLogger(ZClassPathItemResource.class);

  private final String name;

  private final ClassLoader classLoader;


  public static List<ZIClassPathItem> getItems() throws Exception
  {
    ClassLoader classLoader = Thread.currentThread().getContextClassLoader();

    List<ZIClassPathItem> ret = new ArrayList<ZIClassPathItem>();

    Enumeration<URL> urls = classLoader.getResources("");
    while (urls.hasMoreElements())
    {
      URL url = urls.nextElement();
      String path = url.getPath();
      File file = new File(path);
      System.out.println(file.getAbsolutePath());
      if (!file.exists())
      {
        log.error("file does not exist: " + file);
      }
      ret.add(new ZClassPathItemFile(file));
    }

    return ret;
  }


  public ZClassPathItemResource(String name, ClassLoader classLoader)
  {
    super();
    this.name = name;
    this.classLoader = classLoader;
  }


  public ZClassPathItemResource()
  {
    this("/", Thread.currentThread().getContextClassLoader());
  }


  public long getHash() throws Exception
  {
    return System.currentTimeMillis();
  }


  public InputStream getInputStream() throws Exception
  {
    return classLoader.getResourceAsStream(name);
  }


  public String getName() throws Exception
  {
    return name;
  }


  public String getRelativePath(ZIClassPathItem root) throws Exception
  {
    String p = getName();
    String rp = root.getName();
    String name = p.substring(rp.length() + 1);
    return name;
  }


  /**
   * isDirectory
   * 
   * @return boolean
   */
  public boolean isDirectory()
  {
    return name.endsWith("/");
  }


  /**
   * isFile
   * 
   * @return boolean
   */
  public boolean isFile()
  {
    return !name.endsWith("/");
  }


  public ZIClassPathItem[] list() throws Exception
  {
    String path;
    if (name.equals("/"))
    {
      path = "";
    }
    else
    {
      path = name;
    }
    Set<String> set = new HashSet<String>();

    Enumeration<URL> urls = classLoader.getResources(path);
    while (urls.hasMoreElements())
    {
      URL url = urls.nextElement();
      set.add(url.getPath());
    }
    ZIClassPathItem[] ret = new ZIClassPathItem[set.size()];
    Iterator<String> iter = set.iterator();
    for (int i = 0; iter.hasNext(); i++)
    {
      String crt = iter.next();
      File file = new File(crt);
      if (!file.exists())
      {
        throw new Exception("file does not exist: " + file);
      }
      ret[i] = new ZClassPathItemFile(file);
    }
    return ret;
  }

}
