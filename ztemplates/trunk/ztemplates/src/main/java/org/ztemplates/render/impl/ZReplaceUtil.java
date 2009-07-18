/*
 * Copyright 2008 Gerd Ziegler (www.gerdziegler.de) Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. 09.03.2008 @author
 * www.gerdziegler.de
 */
package org.ztemplates.render.impl;

import java.util.Map;

import org.apache.log4j.Logger;

public class ZReplaceUtil
{
  private static final Logger log = Logger.getLogger(ZReplaceUtil.class);


  public static String replace(String in, Map<String, Object> val)
  {
    StringBuffer out = new StringBuffer();
    for (int i = 0; i < in.length(); i++)
    {
      int idx1 = in.indexOf("${", i);
      if (idx1 < 0)
      {
        out.append(in, i, in.length());
        return out.toString();
      }
      else
      {
        out.append(in, i, idx1);
        i = idx1;
        int idx2 = in.indexOf("}", idx1);
        if (idx2 < 0)
        {
          out.append(in, idx1, in.length());
          return out.toString();
        }
        else
        {
          String key = in.substring(idx1 + 2, idx2);
          Object value = val.get(key);
          if (value == null)
          {
            out.append(in, i, idx2 + 1);
          }
          else
          {
            out.append(value);
          }
          i = idx2;
        }
      }

    }
    return out.toString();
  }


  public static String replace(StringBuffer sb, Map<String, Object> exposed)
  {
    return replace(sb.toString(), exposed);
  }


  public static void merge(StringBuffer sb, Map<String, Object> values)
  {
    for (int i = 0; i < sb.length() - 3; i++)
    {
      if (sb.charAt(i) == '$' && sb.charAt(i + 1) == '{')
      {
        i += 2;
        int end = sb.indexOf("}", i);
        if (end < 0)
        {
          break;
        }
        String key = sb.substring(i, end);
        Object val = values.get(key);
        if (val != null)
        {
          String sv = val.toString();
          end++;
          sb.replace(i - 2, end, sv);
          i = i + sv.length();
        }
        else
        {
          i = end + 1;
        }
      }
    }
  }
}
