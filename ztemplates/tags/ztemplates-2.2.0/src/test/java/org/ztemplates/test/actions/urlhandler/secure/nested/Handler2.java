package org.ztemplates.test.actions.urlhandler.secure.nested;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSecure;

/**
 */
@ZMatch(value = "/test2/#{nested}")
@ZSecure("maus")
public class Handler2
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
