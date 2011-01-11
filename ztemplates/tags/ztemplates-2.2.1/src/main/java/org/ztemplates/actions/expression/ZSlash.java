package org.ztemplates.actions.expression;

import org.ztemplates.actions.util.ZFormatUtil;

public class ZSlash implements ZTerm
{

  public String parse(String s) throws ZParserException
  {
    if (s.charAt(0) != '/')
    {
      throw new ZParserException(s, "slash must start with '/'");
    }

    return s.substring(1);
  }


  public String toDefinition()
  {
    return "/";
  }


  public void toXml(StringBuffer sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);
    sb.append("<slash/>");
  }


  public String getText()
  {
    return "/";
  }
}
