package org.ztemplates.test.actions.urlhandler.secure.nested;

import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSetter;

/**
 */
@ZMatch(value = "/test/#{nested}")
public class Handler
{
  private INested nested;


  @ZGetter("nested")
  public INested getNested()
  {
    return nested;
  }


  @ZSetter("nested")
  public void setNested(INested nested)
  {
    this.nested = nested;
  }
}
