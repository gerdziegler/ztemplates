package org.ztemplates.test.actions.urlhandler.match.collision2;

import org.ztemplates.actions.ZMatch;

/**
 */
@ZMatch("nested1/id-${value}-now")
public class NestedHandler
{
  private String value;


  public String getValue()
  {
    return value;
  }


  public void setValue(String value)
  {
    this.value = value;
  }
}
