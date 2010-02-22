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
 * 17.11.2007
 * @author www.gerdziegler.de
 */
package org.ztemplates.actions.urlhandler;

import org.ztemplates.actions.ZGetter;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.ZSetter;
import org.ztemplates.actions.expression.ZExpression;
import org.ztemplates.actions.expression.ZNestedExpression;
import org.ztemplates.actions.expression.ZOptionalExpression;
import org.ztemplates.actions.expression.ZTail;
import org.ztemplates.actions.expression.ZTerm;
import org.ztemplates.actions.expression.ZVariable;
import org.ztemplates.actions.util.impl.ZReflectionUtil;

public class ZClassValidator
{

  public static void validate(Class clazz) throws Exception
  {
    ZMatch match = (ZMatch) clazz.getAnnotation(ZMatch.class);

    StringBuffer buff = new StringBuffer();

    ZExpression expression = new ZExpression(match.value());

    validate(buff, expression, clazz);

    String[] parameters = match.parameters();
    for (String name : parameters)
    {
      // if (ReflectionUtil.getGetter(clazz, name) == null)
      // {
      // buff.append("\nmissing @" + ZGetter.class.getSimpleName() + "(\"" +
      // name + "\") or " + ReflectionUtil.computePrefixName("get", name) +
      // "()");
      // }
      if (ZReflectionUtil.getSetter(clazz, name) == null)
      {
        buff.append("\nmissing @" + ZSetter.class.getSimpleName() + "(\"" + name + "\") or "
            + ZReflectionUtil.computePrefixName("set", name) + "()");
      }
    }

    if (buff.length() > 0)
    {
      throw new Exception("validation errors:\n" + clazz.getName() + buff.toString());
    }
  }


  private static void validate(StringBuffer buff, ZExpression expression, Class clazz)
      throws Exception
  {
    for (ZTerm t : expression.getContent())
    {
      if (t instanceof ZVariable)
      {
        ZVariable v = (ZVariable) t;
        String name = v.getName();
        if (ZReflectionUtil.getGetter(clazz, name) == null)
        {
          buff.append("\nmissing @" + ZGetter.class.getSimpleName() + "(\"" + name + "\") or "
              + ZReflectionUtil.computePrefixName("get", name) + "()");
        }
        if (ZReflectionUtil.getSetter(clazz, name) == null)
        {
          buff.append("\nmissing @" + ZSetter.class.getSimpleName() + "(\"" + name + "\") or "
              + ZReflectionUtil.computePrefixName("set", name) + "()");
        }
      }
      else if (t instanceof ZOptionalExpression)
      {
        ZOptionalExpression oe = (ZOptionalExpression) t;
        validate(buff, oe.getOptionalExpression(), clazz);
      }
      else if (t instanceof ZNestedExpression)
      {
        ZNestedExpression ne = (ZNestedExpression) t;
        String name = ne.getName();
        if (ZReflectionUtil.getGetter(clazz, name) == null)
        {
          buff.append("\nmissing @" + ZGetter.class.getSimpleName() + "(\"" + name + "\") or "
              + ZReflectionUtil.computePrefixName("get", name) + "()");
        }
        if (ZReflectionUtil.getSetter(clazz, name) == null)
        {
          buff.append("\nmissing @" + ZSetter.class.getSimpleName() + "(\"" + name + "\") or "
              + ZReflectionUtil.computePrefixName("set", name) + "()");
        }
      }
      else if (t instanceof ZTail)
      {
        ZTail v = (ZTail) t;
        String name = v.getName();
        if (ZReflectionUtil.getGetter(clazz, name) == null)
        {
          buff.append("\nmissing @" + ZGetter.class.getSimpleName() + "(\"" + name + "\") or "
              + ZReflectionUtil.computePrefixName("get", name) + "()");
        }
        if (ZReflectionUtil.getSetter(clazz, name) == null)
        {
          buff.append("\nmissing @" + ZSetter.class.getSimpleName() + "(\"" + name + "\") or "
              + ZReflectionUtil.computePrefixName("set", name) + "()");
        }
      }
    }
  }
}
