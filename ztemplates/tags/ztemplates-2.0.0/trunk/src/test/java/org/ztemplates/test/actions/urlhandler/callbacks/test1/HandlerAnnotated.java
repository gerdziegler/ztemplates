package org.ztemplates.test.actions.urlhandler.callbacks.test1;

import org.ztemplates.actions.ZAfter;
import org.ztemplates.actions.ZBefore;
import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.ZInit;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSetter;

/**
 */
@ZMatch("/annotated/${var1}[/#{nested}]")
public class HandlerAnnotated
{
  private String var1;

  private boolean beforeCalled = false;

  private boolean afterCalled = false;

  private boolean beforeVar1Called = false;

  private boolean afterVar1Called = false;

  private boolean initNestedCalled = false;

  private Nested nested;


  @ZBefore
  public void callThisBefore()
  {
    beforeCalled = true;
  }


  @ZBefore("var1")
  public void callThisBeforeVar1()
  {
    beforeVar1Called = true;
  }


  @ZGetter("var1")
  public String getVar1()
  {
    return var1;
  }


  @ZSetter("var1")
  public void setVar1(String var1)
  {
    this.var1 = var1;
  }


  @ZAfter("var1")
  public void callThisAfterVar1()
  {
    afterVar1Called = true;
  }


  @ZAfter
  public void callThisAfter()
  {
    afterCalled = true;
  }


  public boolean isAfterCalled()
  {
    return afterCalled;
  }


  public boolean isBeforeCalled()
  {
    return beforeCalled;
  }


  public boolean isAfterVar1Called()
  {
    return afterVar1Called;
  }


  public boolean isBeforeVar1Called()
  {
    return beforeVar1Called;
  }


  @ZInit("nested")
  public void callInitNested(Nested nested)
  {
    initNestedCalled = true;
  }


  public Nested getNested()
  {
    return nested;
  }


  public void setNested(Nested nested)
  {
    this.nested = nested;
  }


  public boolean isInitNestedCalled()
  {
    return initNestedCalled;
  }


  public void setInitNestedCalled(boolean initNestedCalled)
  {
    this.initNestedCalled = initNestedCalled;
  }
}
