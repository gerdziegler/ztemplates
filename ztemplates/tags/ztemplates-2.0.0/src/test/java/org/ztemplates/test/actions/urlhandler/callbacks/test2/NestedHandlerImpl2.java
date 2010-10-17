package org.ztemplates.test.actions.urlhandler.callbacks.test2;

import org.ztemplates.actions.ZAfter;
import org.ztemplates.actions.ZBefore;
import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSetter;

/**
 */
@ZMatch("nested2/${value}[/#{tree}]-")
public class NestedHandlerImpl2 implements NestedHandlerInterface
{
  private String value;

  private int beforeCalled = 0;

  private int afterCalled = 0;

  private int afterValueCalled = 0;

  private int beforeValueCalled = 0;

  private TreeHandler tree;


  @ZBefore
  public void before()
  {
    beforeCalled++;
  }


  @ZAfter
  public void after()
  {
    afterCalled++;
  }


  public int getAfterCalled()
  {
    return afterCalled;
  }


  public int getBeforeCalled()
  {
    return beforeCalled;
  }


  @ZBefore("value")
  public void beforeValue()
  {
    beforeValueCalled++;
  }


  @ZAfter("value")
  public void afterValue()
  {
    afterValueCalled++;
  }


  @ZGetter("value")
  public String getValue()
  {
    return value;
  }


  @ZSetter("value")
  public void setValue(String value)
  {
    this.value = value;
  }


  public int getAfterValueCalled()
  {
    return afterValueCalled;
  }


  public int getBeforeValueCalled()
  {
    return beforeValueCalled;
  }


  @ZGetter("tree")
  public TreeHandler getTree()
  {
    return tree;
  }


  @ZSetter("tree")
  public void setTree(TreeHandler tree)
  {
    this.tree = tree;
  }
}
