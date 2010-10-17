package org.ztemplates.actions.urlhandler.tree.match;

import org.ztemplates.actions.util.ZFormatUtil;

public class ZSegmentTail extends ZSegment
{
  private final String name;


  public ZSegmentTail(String name)
  {
    super();
    this.name = name;
  }


  public String getName()
  {
    return name;
  }


  public void toXml(StringBuffer sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);

    sb.append("<tail name=\"" + name + "\"/>");
  }


  @Override
  public boolean isMatchingTheSame(ZSegment other)
  {
    if (other instanceof ZSegmentTail)
    {
      return true;
    }
    return false;
  }


  @Override
  public String toDefinition()
  {
    return "*{" + name + "}";
  }
}
