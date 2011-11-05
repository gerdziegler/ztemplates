package org.ztemplates.html;

import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZExpose;

public abstract class ZPropertyHtml
{
  @ZExpose
  private final ZProperty property;

  @ZExpose
  private final String id;

  @ZExpose
  private String cssClass;

  @ZExpose
  private String style;

  @ZExpose
  private String htmlAttributes;


  public ZPropertyHtml(String id,
      ZProperty property)
  {
    this.id = id;
    this.property = property;
  }


  protected static String computeId(ZProperty property)
  {
    return computeId(property, 0);
  }


  protected static String computeId(ZProperty property, int idx)
  {
    String ret = property.getName().replace('.', '_');
    if (idx > 0)
    {
      ret += idx;
    }
    return ret;
  }


  public String getId()
  {
    return id;
  }


  public ZProperty getProperty()
  {
    return property;
  }


  @ZExpose
  public String getName()
  {
    return property.getName();
  }


  public String getCssClass()
  {
    return cssClass;
  }


  public void setCssClass(String cssId)
  {
    this.cssClass = cssId;
  }


  /**
   * pass-through html attributes to be added to the generated html tag
   * 
   * @return
   */
  public String getHtmlAttributes()
  {
    return htmlAttributes;
  }


  /**
   * pass-through html attributes to be added to the generated html tag
   * 
   * @return
   */
  public void setHtmlAttributes(String attributes)
  {
    this.htmlAttributes = attributes;
  }


  public String getStyle()
  {
    return style;
  }


  public void setStyle(String style)
  {
    this.style = style;
  }
}
