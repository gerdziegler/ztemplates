package org.ztemplates.test.actions.urlhandler.callbacks.test1;

import org.ztemplates.actions.ZMatch;

/**
 */
@ZMatch("/notannotated/${var1}[/#{nested}]")
public class HandlerNotAnnotated
{
  private String var1;

  private boolean beforeCalled = false;

  private boolean afterCalled = false;

  private boolean beforeVar1Called = false;

  private boolean afterVar1Called = false;

  private boolean initNestedCalled = false;

  private Nested nested;


  public void before()
  {
    beforeCalled = true;
  }


  public void beforeVar1()
  {
    beforeVar1Called = true;
  }


  public String getVar1()
  {
    return var1;
  }


  public void setVar1(String var1)
  {
    this.var1 = var1;
  }


  public void afterVar1()
  {
    afterVar1Called = true;
  }


  public void after()
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


  public boolean isInitNestedCalled()
  {
    return initNestedCalled;
  }


  public void setInitNestedCalled(boolean initNestedCalled)
  {
    this.initNestedCalled = initNestedCalled;
  }


  public void initNested(Nested nested)
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
}
