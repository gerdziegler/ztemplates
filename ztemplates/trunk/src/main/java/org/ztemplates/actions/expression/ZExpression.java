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

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.ztemplates.actions.util.ZFormatUtil;

public class ZExpression implements ZTerm
{
  private static Logger log = Logger.getLogger(ZExpression.class);

  private List<ZTerm> content = new ArrayList<ZTerm>();


  public ZExpression(String definition) throws ZParserException
  {
    if (log.isDebugEnabled())
    {
      log.debug("parsing '" + definition + "'");
    }
    String rest = parse(definition);

    // paranoia
    if (rest.length() > 0)
    {
      throw new ZParserException(rest, "input too long");
    }

    // selftest
    // String definitionTest = toDefinition();
    // if (!definition.equals(definitionTest))
    // {
    // throw new ParserException(definition, "internal error: not parsed
    // correctly '" + definitionTest + "'");
    // }
  }


  public ZExpression()
  {
  }


  public List<ZTerm> getContent()
  {
    return content;
  }


  public String parse(String s) throws ZParserException
  {
    if (s.length() == 0)
    {
      return "";
    }

    char c = s.charAt(0);
    switch (c)
    {
      case '/':
      {
        ZSlash v = new ZSlash();
        content.add(v);
        String rest = v.parse(s);
        return parse(rest);
      }
      case '$':
      {
        ZVariable v = new ZVariable();
        checkLiteral(s, v);
        content.add(v);
        String rest = v.parse(s);
        return parse(rest);
      }
      case '*':
      {
        ZTail v = new ZTail();
        checkLiteral(s, v);
        content.add(v);
        String rest = v.parse(s);
        if (rest.length() > 0)
        {
          throw new ZParserException(s, "tail *{} must be last in expression");
        }
        return "";
      }
      case '#':
      {
        ZNestedExpression v = new ZNestedExpression();
        checkLiteral(s, v);
        content.add(v);
        String rest = v.parse(s);
        return parse(rest);
      }
      case '[':
      {
        ZOptionalExpression v = new ZOptionalExpression();
        content.add(v);
        String rest = v.parse(s);
        return parse(rest);
      }
      case ']':
      {
        return s;
      }
      default:
      {
        ZLiteral v = new ZLiteral();
        content.add(v);
        String rest = v.parse(s);
        return parse(rest);
      }
    }
  }


  private void checkLiteral(String s, ZTerm crt) throws ZParserException
  {
    if (content.isEmpty())
    {
      return;
    }
    ZTerm last = content.get(content.size() - 1);
    if (last != null && !(last instanceof ZLiteral) && !(last instanceof ZSlash))
    {
      throw new ZParserException(s, crt + " must be preceded by a literal or slash: " + last);
    }
  }


  public String toString()
  {
    return toXml();
  }


  public String toXml()
  {
    StringBuilder sb = new StringBuilder();
    toXml(sb, 0);
    return sb.toString();
  }


  public void toXml(StringBuilder sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);
    sb.append("<expression>");
    for (ZTerm t : content)
    {
      t.toXml(sb, depth + 1);
    }
    ZFormatUtil.indent(sb, depth);
    sb.append("</expression>");
  }


  public String toDefinition()
  {
    StringBuilder sb = new StringBuilder();
    for (ZTerm t : content)
    {
      sb.append(t.toDefinition());
    }
    return sb.toString();
  }

}
