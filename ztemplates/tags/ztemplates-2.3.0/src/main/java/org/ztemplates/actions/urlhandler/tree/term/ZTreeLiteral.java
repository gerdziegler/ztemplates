package org.ztemplates.actions.urlhandler.tree.term;

import org.ztemplates.actions.expression.ZParserException;
import org.ztemplates.actions.util.ZFormatUtil;

public class ZTreeLiteral extends ZTreeTerm
{
  private final String text;


  public ZTreeLiteral(Class clazz, String text) throws ZParserException
  {
    super(clazz);
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
  public String toDefinition()
  {
    return text;
  }
}
