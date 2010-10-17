package org.ztemplates.test.actions.urlhandler.repository.nested;

import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSetter;

/**
 */
@ZMatch("tree/${treeId}[/select/${nodeId}]")
public class TreeHandler
{
  private String treeId;

  private String nodeId;


  @ZGetter("treeId")
  public String getTreeId()
  {
    return treeId;
  }


  @ZSetter("treeId")
  public void setTreeId(String value)
  {
    this.treeId = value;
  }


  public String getNodeId()
  {
    return nodeId;
  }


  public void setNodeId(String nodeId)
  {
    this.nodeId = nodeId;
  }
}
