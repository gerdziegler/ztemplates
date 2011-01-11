package org.ztemplates.actions.urlhandler.tree.term;

import org.ztemplates.actions.expression.ZParserException;
import org.ztemplates.actions.util.ZFormatUtil;

public class ZTreeNestedBegin extends ZTreeTerm
{
  private final String name;

  private final Class nestedClass;


  public ZTreeNestedBegin(Class clazz, String name, Class nestedClass) throws ZParserException
  {
    super(clazz);
    this.name = name;
    this.nestedClass = nestedClass;
  }


  public String getName()
  {
    return name;
  }


  @Override
  public void toXml(StringBuffer sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);
    sb.append("<nested-begin name=\"" + name + "\" nestedClass=\"" + nestedClass.getSimpleName()
        + "\"/>");
  }


  @Override
  public String toDefinition()
  {
    return "#{ ";
  }


  public Class getNestedClass()
  {
    return nestedClass;
  }
}
