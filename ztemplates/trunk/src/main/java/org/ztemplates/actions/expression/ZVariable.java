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

public class ZVariable implements ZTerm
{
  private static Logger log = Logger.getLogger(ZVariable.class);

  private String name;


  public String parse(String s) throws ZParserException
  {
    if (!s.startsWith("${"))
    {
      throw new ZParserException(s,
          "variable definition must start with '${', single '$' is not allowed");
    }

    int idx = s.indexOf('}');
    if (idx < 0)
    {
      throw new ZParserException(s, "variable definition not closed by '}'");
    }
    if (idx == 2)
    {
      throw new ZParserException(s, "variable name must not be empty");
    }

    name = s.substring(2, idx);

    for (int i = 0; i < name.length(); i++)
    {
      char c = name.charAt(i);
      if (!Character.isJavaIdentifierPart(c))
      {
        throw new ZParserException(s, "variables names can contain only java identifier characters");
      }

      if (c == '$')
      {
        throw new ZParserException(s, "variables names can not contain '$'");
      }

    }
    return s.substring(idx + 1);
  }


  public String getName()
  {
    return name;
  }


  public void setName(String name)
  {
    this.name = name;
  }


  public String toString()
  {
    StringBuilder sb = new StringBuilder();
    sb.append("<variable>");
    sb.append(getName());
    sb.append("</variable>");
    return sb.toString();
  }


  public void toXml(StringBuilder sb, int depth)
  {
    ZFormatUtil.indent(sb, depth);
    sb.append("<variable>");
    sb.append(getName());
    sb.append("</variable>");
  }


  public String toDefinition()
  {
    StringBuilder sb = new StringBuilder("${");
    sb.append(getName());
    sb.append("}");
    return sb.toString();
  }
}
