package org.ztemplates.test.actions.urlhandler.callbacks.test2;

import org.ztemplates.actions.ZMatch;

/**
 */
@ZMatch("tree/${treeId}[/select/${nodeId}]")
public class TreeHandler
{
  private String treeId;

  private String nodeId;


  public String getTreeId()
  {
    return treeId;
  }


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
