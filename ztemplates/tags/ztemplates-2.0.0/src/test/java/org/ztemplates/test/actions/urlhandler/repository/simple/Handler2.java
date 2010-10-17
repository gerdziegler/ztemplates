package org.ztemplates.test.actions.urlhandler.repository.simple;

import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSetter;

/**
 */
@ZMatch("/audiobooks2/category/${title}/${categoryId}[/page/${pageNum}[/sortby-${sortBy}]]")
public class Handler2
{
  private String title;

  private String categoryId;

  private String pageNum;

  private String sortBy;

  private String param1;


  @ZGetter("categoryId")
  public String getCategoryId()
  {
    return categoryId;
  }


  @ZSetter("categoryId")
  public void setCategoryId(String categoryId)
  {
    this.categoryId = categoryId;
  }


  @ZGetter("pageNum")
  public String getPageNum()
  {
    return pageNum;
  }


  @ZSetter("pageNum")
  public void setPageNum(String pageNum)
  {
    this.pageNum = pageNum;
  }


  @ZGetter("sortBy")
  public String getSortBy()
  {
    return sortBy;
  }


  @ZSetter("sortBy")
  public void setSortBy(String sortBy)
  {
    this.sortBy = sortBy;
  }


  @ZGetter("title")
  public String getTitle()
  {
    return title;
  }


  @ZSetter("title")
  public void setTitle(String title)
  {
    this.title = title;
  }


  @ZGetter("param1")
  public String getParam1()
  {
    return param1;
  }


  @ZSetter("param1")
  public void setParam1(String param1)
  {
    this.param1 = param1;
  }
}
