package org.ztemplates.actions.urlhandler.tree.term;

import java.io.Serializable;

import org.ztemplates.actions.expression.ZParserException;

public abstract class ZTreeTerm implements Serializable
{
  private final Class clazz;


  public ZTreeTerm(Class clazz) throws ZParserException
  {
    super();
    this.clazz = clazz;
  }


  public Class getClazz()
  {
    return clazz;
  }


  public abstract void toXml(StringBuffer sb, int i);


  public abstract String toDefinition();

  // @Override
  // public String toString() {
  // StringBuffer sb = new StringBuffer();
  // toXml(sb, 0);
  // return sb.toString();
  // }
  //
  // public void toXml(StringBuffer sb, int depth) {
  // ZFormatUtil.indent(sb, depth);
  // sb.append("<parsed-term>");
  // term.toXml(sb, depth + 1);
  // ZFormatUtil.indent(sb, depth);
  // sb.append("</parsed-term>");
  // }
}
