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

public class ZLiteral implements ZTerm
{
  private static Logger log = Logger.getLogger(ZLiteral.class);

  private StringBuffer text = new StringBuffer();


  public String getText()
  {
    return text.toString();
  }


  public String parse(String s) throws ZParserException
  {
    for (int i = 0; i < s.length(); i++)
    {
      char c = s.charAt(i);
      switch (c)
      {
        case '/':
          return s.substring(i);
        case '$':
          return s.substring(i);
        case '#':
          return s.substring(i);
        case '*':
          return s.substring(i);
        case '[':
          return s.substring(i);
        case ']':
          return s.substring(i);
        default:
          text.append(c);
          break;
      }
    }

    return "";
  }


  public String toString()
  {
    StringBuffer sb = new StringBuffer();
    sb.append("<literal>");
    sb.append(getText());
    sb.append("</literal>");
    return sb.toString();
  }


  public void toXml(StringBuffer sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);
    sb.append("<literal>");
    sb.append(getText());
    sb.append("</literal>");
  }


  public String toDefinition()
  {
    return text.toString();
  }

}
