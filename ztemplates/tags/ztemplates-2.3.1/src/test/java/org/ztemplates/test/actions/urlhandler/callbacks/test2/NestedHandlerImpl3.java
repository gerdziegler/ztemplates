package org.ztemplates.test.actions.urlhandler.callbacks.test2;

import org.ztemplates.actions.ZMatch;

/**
 */
@ZMatch(value = "nested3", consume = true)
public class NestedHandlerImpl3 implements NestedHandlerInterface
{

  public int getAfterCalled()
  {
    // TODO Auto-generated method stub
    return 0;
  }


  public int getAfterValueCalled()
  {
    // TODO Auto-generated method stub
    return 0;
  }


  public int getBeforeCalled()
  {
    // TODO Auto-generated method stub
    return 0;
  }


  public int getBeforeValueCalled()
  {
    // TODO Auto-generated method stub
    return 0;
  }
}
