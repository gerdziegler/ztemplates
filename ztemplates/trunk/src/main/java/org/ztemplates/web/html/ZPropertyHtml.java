package org.ztemplates.web.html;

import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZExpose;

public abstract class ZPropertyHtml
{
  private final String id;

  private final ZProperty property;

  private String cssClass;

  private String htmlAttributes;


  public ZPropertyHtml(String id, ZProperty property)
  {
    this.id = id;
    this.property = property;
  }


  @ZExpose
  public final String getId()
  {
    return id;
  }


  @ZExpose
  public final ZProperty getProperty()
  {
    return property;
  }


  @ZExpose
  public final String getName()
  {
    return property.getName();
  }


  @ZExpose
  public final String getCssClass()
  {
    return cssClass;
  }


  public final void setCssClass(String cssId)
  {
    this.cssClass = cssId;
  }


  /**
   * pass-through html attributes to be added to the generated html tag
   * 
   * @return
   */
  @ZExpose
  public final String getHtmlAttributes()
  {
    return htmlAttributes;
  }


  /**
   * pass-through html attributes to be added to the generated html tag
   * 
   * @return
   */
  public final void setHtmlAttributes(String attributes)
  {
    this.htmlAttributes = attributes;
  }
}
