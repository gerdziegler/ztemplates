package org.ztemplates.test.actions.urlhandler.repository.simple;

import org.ztemplates.actions.ZMatch;

/**
 */
@ZMatch("audiobooks/category/${title}/${category2Id}")
public class Handler2a
{
  private String title;

  private String category2Id;


  public String getCategory2Id()
  {
    return category2Id;
  }


  public void setCategory2Id(String categoryId)
  {
    this.category2Id = categoryId;
  }


  public String getTitle()
  {
    return title;
  }


  public void setTitle(String title)
  {
    this.title = title;
  }
}
