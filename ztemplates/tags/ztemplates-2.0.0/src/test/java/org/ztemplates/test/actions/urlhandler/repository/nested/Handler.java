package org.ztemplates.test.actions.urlhandler.repository.nested;

import org.ztemplates.actions.ZAfter;
import org.ztemplates.actions.ZBefore;
import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSetter;

/**
 */
@ZMatch("/audiobooks/#{nested}[/#{tree}]")
public class Handler
{
  private NestedHandler nested;

  private TreeHandler tree;

  private int beforeCalled = 0;

  private int afterCalled = 0;

  private int beforeNestedCalled = 0;

  private int afterNestedCalled = 0;


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


  @ZBefore("nested")
  public void beforeNested()
  {
    beforeNestedCalled++;
  }


  @ZAfter("nested")
  public void afterNested()
  {
    afterNestedCalled++;
  }


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


  public int getAfterNestedCalled()
  {
    return afterNestedCalled;
  }


  public int getBeforeNestedCalled()
  {
    return beforeNestedCalled;
  }
}
