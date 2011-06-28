package org.ztemplates.test.actions.urlhandler.match.test2;

import org.ztemplates.actions.ZMatch;

/**
 */
@ZMatch("nested2/${value}")
public class NestedHandler2 extends NestedHandler
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
