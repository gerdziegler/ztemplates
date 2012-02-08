/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * @author www.gerdziegler.de
 */
package org.ztemplates.actions.expression;

import org.apache.log4j.Logger;
import org.ztemplates.actions.util.ZFormatUtil;

public class ZOptionalExpression implements ZTerm
{
  private static Logger log = Logger.getLogger(ZOptionalExpression.class);

  private ZExpression optionalExpression;


  public String parse(String s) throws ZParserException
  {
    if (s.charAt(0) != '[')
    {
      throw new ZParserException(s, "optional expression must start with '['");
    }

    optionalExpression = new ZExpression();
    String rest = optionalExpression.parse(s.substring(1));
    if (rest.length() == 0)
    {
      throw new ZParserException(s, "optional expression must end with with ']'");
    }
    if (rest.charAt(0) != ']')
    {
      throw new ZParserException(s, "optional expression must end with with ']', not '"
          + rest.charAt(0));
    }
    return rest.substring(1);
  }


  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("<optional>\n");
    sb.append(optionalExpression);
    sb.append("\n</optional>");
    return sb.toString();
  }


  public void toXml(StringBuilder sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);
    sb.append("<optional>");
    optionalExpression.toXml(sb, depth + 1);
    ZFormatUtil.indent(sb, depth);
    sb.append("</optional>");
  }


  public String toDefinition()
  {
    return "[" + optionalExpression.toDefinition() + "]";
  }


  public ZExpression getOptionalExpression()
  {
    return optionalExpression;
  }
}
