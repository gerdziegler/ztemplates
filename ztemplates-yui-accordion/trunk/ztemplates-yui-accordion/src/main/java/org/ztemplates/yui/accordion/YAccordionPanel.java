package org.ztemplates.yui.accordion;

public class YAccordionPanel
{
  private Object header;

  private Object content;

  private boolean expanded;

  public YAccordionPanel(Object header, Object content, boolean expanded)
  {
    super();
    this.header = header;
    this.content = content;
    this.expanded = expanded;
  }

  public YAccordionPanel(Object header, Object content)
  {
    this(header, content, false);
  }

  public Object getHeader()
  {
    return header;
  }


  public void setHeader(Object header)
  {
    this.header = header;
  }


  public Object getContent()
  {
    return content;
  }


  public void setContent(Object content)
  {
    this.content = content;
  }


  public boolean isExpanded()
  {
    return expanded;
  }


  public void setExpand(boolean expand)
  {
    this.expanded = expand;
  }
}
