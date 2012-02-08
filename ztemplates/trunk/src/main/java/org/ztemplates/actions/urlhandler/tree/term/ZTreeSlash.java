package org.ztemplates.actions.urlhandler.tree.term;

import org.ztemplates.actions.expression.ZParserException;
import org.ztemplates.actions.util.ZFormatUtil;

public class ZTreeSlash extends ZTreeTerm
{

  public ZTreeSlash(Class clazz) throws ZParserException
  {
    super(clazz);
  }


  public void toXml(StringBuilder sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);
    sb.append("<slash/>");
  }


  public String toDefinition()
  {
    return "/";
  }

}
