package org.ztemplates.yui.menu;

import java.util.LinkedList;
import java.util.List;

public class YMenuItem
{
  private Object content;

  private String href;

  private final List<YMenuItem> menuItems = new LinkedList<YMenuItem>();

  private String contentClass = "ztemplates-" + YMenuItem.class.getSimpleName() + "-content";


  public YMenuItem()
  {
  }


  public YMenuItem(Object content)
  {
    this.content = content;
  }


  public YMenuItem(Object content, String href)
  {
    this.content = content;
    this.href = href;
  }


  public Object getContent()
  {
    return content;
  }


  public void setContent(Object content)
  {
    this.content = content;
  }


  public String getHref()
  {
    return href;
  }


  public void setHref(String href)
  {
    this.href = href;
  }


  public List<YMenuItem> getMenuItems()
  {
    return menuItems;
  }


  public String getContentClass()
  {
    return contentClass;
  }


  public void setContentClass(String contentClass)
  {
    this.contentClass = contentClass;
  }
}
