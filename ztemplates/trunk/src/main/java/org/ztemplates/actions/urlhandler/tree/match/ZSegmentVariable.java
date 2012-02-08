package org.ztemplates.actions.urlhandler.tree.match;

import org.ztemplates.actions.util.ZFormatUtil;

public class ZSegmentVariable extends ZSegment
{
  private final String name;


  public ZSegmentVariable(String name)
  {
    super();
    this.name = name;
  }


  public String getName()
  {
    return name;
  }


  public void toXml(StringBuilder sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);

    sb.append("<variable name=\"" + name + "\"/>");
  }


  @Override
  public boolean isMatchingTheSame(ZSegment other)
  {
    if (other instanceof ZSegmentVariable)
    {
      return true;
    }
    return false;
  }


  @Override
  public String toDefinition()
  {
    return "${" + name + "}";
  }
}
