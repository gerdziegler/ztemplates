package org.ztemplates.actions.urlhandler.tree.match;

import org.ztemplates.actions.util.ZFormatUtil;

public class ZSegmentLiteral extends ZSegment
{
  private final String text;


  public ZSegmentLiteral(String text)
  {
    super();
    this.text = text;
  }


  public String getText()
  {
    return text;
  }


  @Override
  public void toXml(StringBuffer sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);
    sb.append("<literal>");
    sb.append(text);
    sb.append("</literal>");
  }


  @Override
  public boolean isMatchingTheSame(ZSegment other)
  {
    if (other instanceof ZSegmentLiteral)
    {
      ZSegmentLiteral sl = (ZSegmentLiteral) other;
      return text.equals(sl.text);
    }
    return false;
  }


  @Override
  public String toDefinition()
  {
    return text;
  }
}
