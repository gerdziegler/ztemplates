package org.ztemplates.test.actions.urlhandler.match.handler.tail;

import org.ztemplates.actions.ZMatch;

/**
 */
@ZMatch(value = "/path6*{tail}")
public class Handler6
{
  private String tail;


  public String getTail()
  {
    return tail;
  }


  public void setTail(String gobble)
  {
    this.tail = gobble;
  }
}
