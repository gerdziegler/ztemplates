package org.ztemplates.test.actions.urlhandler.secure.nested;

import org.ztemplates.actions.ZMatch;

/**
 */
@ZMatch(value = "/test/#{nested}")
public class Handler
{
  private INested nested;


  public INested getNested()
  {
    return nested;
  }


  public void setNested(INested nested)
  {
    this.nested = nested;
  }
}
