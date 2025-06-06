/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * @author www.gerdziegler.de
 */
package org.ztemplates.test.render.impl.opt;

import java.io.PrintStream;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;

class TypeRunner
{
  private final static PrintStream out = System.out;


  public static void printSuperclass(Type sup)
  {
    if (sup != null && !sup.equals(Object.class))
    {
      out.print("extends ");
      printType(sup);
      out.println();
    }
  }


  public static void printInterfaces(Type[] impls)
  {
    if (impls != null && impls.length > 0)
    {
      out.print("implements ");
      int i = 0;
      for (Type impl : impls)
      {
        if (i++ > 0)
          out.print(",");
        printType(impl);
      }
      out.println();
    }
  }


  public static void printMethods(Class clazz)
  {
    for (Method m : clazz.getMethods())
    {
      out.print(m.getName() + " ");
      printType(m.getReturnType());
      printType(m.getGenericReturnType());
      out.println("");
    }
  }


  public static void printTypeParameters(TypeVariable<?>[] vars)
  {
    if (vars != null && vars.length > 0)
    {
      out.print("<");
      int i = 0;
      for (TypeVariable<?> var : vars)
      {
        if (i++ > 0)
          out.print(",");
        out.print(var.getName());
        printBounds(var.getBounds());
      }
      out.print(">");
    }
  }


  public static void printBounds(Type[] bounds)
  {
    if (bounds != null && bounds.length > 0 && !(bounds.length == 1 && bounds[0] == Object.class))
    {
      out.print(" extends ");
      int i = 0;
      for (Type bound : bounds)
      {
        if (i++ > 0)
          out.print("&");
        printType(bound);
      }
    }
  }


  public static void printParams(Type[] types)
  {
    if (types != null && types.length > 0)
    {
      out.print("<");
      int i = 0;
      for (Type type : types)
      {
        if (i++ > 0)
          out.print(",");
        printType(type);
      }
      out.print(">");
    }
  }


  public static void printType(Type type)
  {
    if (type instanceof Class)
    {
      Class<?> c = (Class) type;
      out.print(c.getName());
    }
    else if (type instanceof ParameterizedType)
    {
      ParameterizedType p = (ParameterizedType) type;
      Class c = (Class) p.getRawType();
      Type o = p.getOwnerType();
      if (o != null)
      {
        printType(o);
        out.print(".");
      }
      out.print(c.getName());
      printParams(p.getActualTypeArguments());
    }
    else if (type instanceof TypeVariable<?>)
    {
      TypeVariable<?> v = (TypeVariable<?>) type;
      out.print(v.getName());
    }
    else if (type instanceof GenericArrayType)
    {
      GenericArrayType a = (GenericArrayType) type;
      printType(a.getGenericComponentType());
      out.print("[]");
    }
    else if (type instanceof WildcardType)
    {
      WildcardType w = (WildcardType) type;
      Type[] upper = w.getUpperBounds();
      Type[] lower = w.getLowerBounds();
      if (upper.length == 1 && lower.length == 0)
      {
        out.print("? extends ");
        printType(upper[0]);
      }
      else if (upper.length == 0 && lower.length == 1)
      {
        out.print("? super ");
        printType(lower[0]);
      }
      else
        throw new AssertionError();
    }
  }


  public static void printClass(Class c)
  {
    out.print("class ");
    out.print(c.getName());
    printTypeParameters(c.getTypeParameters());
    out.println();
    printSuperclass(c.getGenericSuperclass());
    printInterfaces(c.getGenericInterfaces());
    printMethods(c);
  }


  public static void main(String[] args) throws ClassNotFoundException
  {
    Class<?> c = Root.class;
    printClass(c);
  }
}
