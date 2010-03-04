package org.ztemplates.test.actions.urlhandler.form;

import org.ztemplates.actions.ZMatch;
import org.ztemplates.property.ZIOperationListener;
import org.ztemplates.property.ZOperation;

/**
 */
@ZMatch(value = "/act-handler", parameters =
{
  "op"
})
public class FormActionCallback
{
  private boolean afterCalled = false;
  private boolean callbackCalled = false;

  private final ZOperation op = new ZOperation("called")
  {
    {
      setOperationListener(new ZIOperationListener()
      {
        public void exec() throws Exception
        {
          callbackCalled = true;
        }
      });
    }
  };

  public void after()
  {
    afterCalled = true;
  }

  public ZOperation getOp()
  {
    return op;
  }


  public boolean isCallbackCalled()
  {
    return callbackCalled;
  }


  public boolean isAfterCalled()
  {
    return afterCalled;
  }

}
