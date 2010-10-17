package org.ztemplates.test.actions.urlhandler.match.test2;

import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSetter;

/**
 */
@ZMatch("/basic[/#{nested}]")
public class Handler
{
  private NestedHandler nested;


  @ZGetter("nested")
  public NestedHandler getNested()
  {
    return nested;
  }


  @ZSetter("nested")
  public void setNested(NestedHandler nested)
  {
    this.nested = nested;
  }
}
