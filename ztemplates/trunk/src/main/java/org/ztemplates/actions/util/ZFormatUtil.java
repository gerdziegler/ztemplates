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

package org.ztemplates.actions.util;

public class ZFormatUtil
{
  public static void indent(StringBuffer sb, int depth)
  {
    sb.append('\n');
    for (int i = 0; i < depth; i++)
    {
      sb.append("    ");
    }
  }


  public static void indentTree(StringBuffer sb, int depth)
  {
    sb.append('\n');
    for (int i = 0; i < depth - 1; i++)
    {
      sb.append("   |    ");
    }
    if (depth > 0)
    {
      sb.append("   |--  ");
    }
  }


  public static void fillRight(StringBuffer sb, int depth, char c)
  {
    for (int i = sb.length(); i < depth; i++)
    {
      sb.append(c);
    }
  }


  public static void fillLeft(StringBuffer sb, int depth, String s, char c)
  {
    for (int i = s.length(); i < depth; i++)
    {
      sb.append(c);
    }
    sb.append(s);
  }
}
