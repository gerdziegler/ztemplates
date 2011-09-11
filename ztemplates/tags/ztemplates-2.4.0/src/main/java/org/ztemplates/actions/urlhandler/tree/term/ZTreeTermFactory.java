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

package org.ztemplates.actions.urlhandler.tree.term;

import java.util.ArrayList;
import java.util.List;

import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.expression.ZExpression;
import org.ztemplates.actions.expression.ZLiteral;
import org.ztemplates.actions.expression.ZNestedExpression;
import org.ztemplates.actions.expression.ZOptionalExpression;
import org.ztemplates.actions.expression.ZSlash;
import org.ztemplates.actions.expression.ZTail;
import org.ztemplates.actions.expression.ZTerm;
import org.ztemplates.actions.expression.ZVariable;
import org.ztemplates.actions.util.impl.ZReflectionUtil;

public class ZTreeTermFactory
{
  public static List<ZTreeTermList> expand(ZIClassRepository repo, Class clazz) throws Exception
  {
    ZMatch match = (ZMatch) clazz.getAnnotation(ZMatch.class);
    ZExpression expression = new ZExpression(match.value());
    List<ZTreeTermList> ret = expand(expression, repo, clazz);
    return ret;
  }


  private static List<ZTreeTermList> expand(ZExpression expression, ZIClassRepository repo,
      Class clazz) throws Exception
  {
    List<ZTreeTermList> ret = new ArrayList<ZTreeTermList>();

    for (ZTerm t : expression.getContent())
    {
      if (t instanceof ZLiteral)
      {
        ret = append(ret, expand((ZLiteral) t, clazz));
      }
      else if (t instanceof ZSlash)
      {
        ret = append(ret, expand((ZSlash) t, clazz));
      }
      else if (t instanceof ZVariable)
      {
        ret = append(ret, expand((ZVariable) t, clazz));
      }
      else if (t instanceof ZOptionalExpression)
      {
        ret = append(ret, expand((ZOptionalExpression) t, repo, clazz));
      }
      else if (t instanceof ZNestedExpression)
      {
        ret = append(ret, expand((ZNestedExpression) t, repo, clazz));
      }
      else if (t instanceof ZTail)
      {
        ret = append(ret, expand((ZTail) t, clazz));
      }
      else
      {
        throw new Exception("unknown term-type: " + t);
      }
    }
    return ret;
  }


  private static List<ZTreeTermList> expand(ZLiteral l, Class clazz) throws Exception
  {
    List<ZTreeTermList> ret = new ArrayList<ZTreeTermList>();
    ZTreeTermList tl = new ZTreeTermList();
    tl.getTerms().add(new ZTreeLiteral(clazz, l.getText()));
    ret.add(tl);
    return ret;
  }


  private static List<ZTreeTermList> expand(ZSlash l, Class clazz) throws Exception
  {
    List<ZTreeTermList> ret = new ArrayList<ZTreeTermList>();
    ZTreeTermList tl = new ZTreeTermList();
    tl.getTerms().add(new ZTreeSlash(clazz));
    ret.add(tl);
    return ret;
  }


  private static List<ZTreeTermList> expand(ZVariable l, Class clazz) throws Exception
  {
    List<ZTreeTermList> ret = new ArrayList<ZTreeTermList>();
    ZTreeTermList tl = new ZTreeTermList();
    tl.getTerms().add(new ZTreeVariable(clazz, l.getName()));
    ret.add(tl);
    return ret;
  }


  private static List<ZTreeTermList> expand(ZTail l, Class clazz) throws Exception
  {
    List<ZTreeTermList> ret = new ArrayList<ZTreeTermList>();
    ZTreeTermList tl = new ZTreeTermList();
    tl.getTerms().add(new ZTreeTail(clazz, l.getName()));
    ret.add(tl);
    return ret;
  }


  private static List<ZTreeTermList> expand(ZOptionalExpression e, ZIClassRepository repo,
      Class clazz) throws Exception
  {
    List<ZTreeTermList> ret = expand(e.getOptionalExpression(), repo, clazz);
    ret.add(new ZTreeTermList());
    return ret;
  }


  private static List<ZTreeTermList> expand(ZNestedExpression ne, ZIClassRepository repo,
      Class clazz) throws Exception
  {
    List<ZTreeTermList> ret = new ArrayList<ZTreeTermList>();
    Class nestedClass = ZReflectionUtil.getReferenceType(clazz, ne.getName());
    List<Class> nestedClasses = repo.getClassesAnnotatedWithAndAssignableFrom(ZMatch.class,
        nestedClass);
    String name = ne.getName();
    for (Class c : nestedClasses)
    {
      List<ZTreeTermList> nestedSamples = expand(repo, c);
      for (ZTreeTermList l : nestedSamples)
      {
        l.getTerms().add(0, new ZTreeNestedBegin(clazz, name, c));
        l.getTerms().add(new ZTreeNestedEnd(clazz, name, c));
      }
      ret.addAll(nestedSamples);
    }

    return ret;
  }


  private static List<ZTreeTermList> append(List<ZTreeTermList> l1, List<ZTreeTermList> l2)
  {
    List<ZTreeTermList> ret = new ArrayList<ZTreeTermList>();

    if (l1.isEmpty())
    {
      ret.addAll(l2);
    }
    else if (l2.isEmpty())
    {
      ret.addAll(l1);
    }
    else
    {
      for (ZTreeTermList s1 : l1)
      {
        for (ZTreeTermList s2 : l2)
        {
          ret.add(ZTreeTermList.append(s1, s2));
        }
      }
    }

    return ret;
  }
}
