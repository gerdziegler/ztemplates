/*
 * Copyright 2008 Gerd Ziegler (www.gerdziegler.de)
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
package org.ztemplates.form.impl;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ztemplates.actions.util.impl.ZReflectionUtil;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.ZIForm;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.ZScriptDependency;
import org.ztemplates.validation.ZIValidator;

/**
 * form wrapper that can be used to manipulate a form in a generic way
 * 
 * @author gerdziegler.de
 * 
 */
public final class ZFormWrapper implements ZIFormVisitable
{
  static final Logger log = Logger.getLogger(ZFormWrapper.class);

  private String name;

  private final List<ZFormWrapper> forms = new ArrayList<ZFormWrapper>();

  private final List<ZPropertyWrapper> properties = new ArrayList<ZPropertyWrapper>();

  private final List<ZOperationWrapper> operations = new ArrayList<ZOperationWrapper>();


  /**
   * creates form model with name "" (empty String)
   * 
   * @param obj
   * @throws Exception
   */
  public ZFormWrapper(ZIForm obj) throws Exception
  {
    this(obj, "");
  }


  /**
   * @throws Exception
   * 
   */
  public void initPropertyNames() throws Exception
  {
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop) throws Exception
      {
        prop.getProperty().setName(prop.getName());
      }


      public void visit(ZOperationWrapper op) throws Exception
      {
        op.getOperation().setName(op.getName());
      }
    };

    visitDepthFirst(visitor);
  }


  /**
   * creates form Model with specified name
   * 
   * @param obj
   * @param name
   * @throws Exception
   */
  protected ZFormWrapper(ZIForm obj,
      String name) throws Exception
  {
    this.name = name;
    String prefix;
    if (name.length() > 0)
    {
      prefix = name + "_";
    }
    else
    {
      prefix = "";
    }

    Set<String> names = new HashSet<String>();

    for (Field f : obj.getClass().getFields())
    {
      Class type = f.getType();
      // ORDER given by extends relation
      // first
      if (ZOperation.class.isAssignableFrom(type))
      {
        ZOperation op = (ZOperation) f.get(obj);
        if (op == null)
        {
          throw new Exception("null value in property " + f);
        }
        String opName = op.getName() == null ? prefix + f.getName() : op.getName();
        if (names.contains(opName))
        {
          log.warn("duplicate name: " + opName);
        }
        else
        {
          names.add(opName);
          operations.add(new ZOperationWrapper(opName, op));
        }
      }
      // second
      else if (ZProperty.class.isAssignableFrom(type))
      {
        ZProperty prop = (ZProperty) f.get(obj);
        if (prop == null)
        {
          throw new Exception("null value in property " + f);
        }
        String propName = prop.getName() == null ? prefix + f.getName() : prop.getName();
        if (names.contains(propName))
        {
          log.warn("duplicate name: " + propName);
        }
        else
        {
          names.add(propName);
          properties.add(new ZPropertyWrapper(propName, prop));
        }
      }
      // third
      else if (ZFormWrapper.class.isAssignableFrom(type))
      {
        ZFormWrapper fe = (ZFormWrapper) f.get(obj);
        if (fe == null)
        {
          throw new Exception("null value in property " + f);
        }
        if (fe.getName() == null)
        {
          String feName = prefix + f.getName();
          fe.setName(feName);
        }
        forms.add(fe);
      }
      // fourth
      else if (ZIForm.class.isAssignableFrom(type))
      {
        ZIForm fe = (ZIForm) f.get(obj);
        if (fe == null)
        {
          throw new Exception("null value in property " + f);
        }
        String feName = f.getName();
        forms.add(new ZFormWrapper(fe, prefix + feName));
      }
    }

    // Methods
    for (Method m : obj.getClass().getMethods())
    {
      if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())
          && !m.getName().equals("getClass"))
      {
        Class type = m.getReturnType();
        // ORDER given by extends relation
        // first
        if (ZOperation.class.isAssignableFrom(type))
        {
          ZOperation op = (ZOperation) m.invoke(obj);
          if (op == null)
          {
            throw new Exception("null op returned from " + m.getName());
          }
          String opName = op.getName() == null ? prefix + ZReflectionUtil.removePrefixName("get", m.getName()) : op.getName();
          if (names.contains(opName))
          {
            log.warn("duplicate name: " + opName);
          }
          else
          {
            names.add(opName);
            operations.add(new ZOperationWrapper(opName, op));
          }
        }
        // second
        else if (ZProperty.class.isAssignableFrom(type))
        {
          ZProperty prop = (ZProperty) m.invoke(obj);
          if (prop == null)
          {
            throw new Exception("null prop returned from " + m.getName());
          }
          String propName = prop.getName() == null ? prefix + ZReflectionUtil.removePrefixName("get", m.getName()) : prop.getName();
          if (names.contains(propName))
          {
            log.warn("duplicate name: " + propName);
          }
          else
          {
            names.add(propName);
            properties.add(new ZPropertyWrapper(propName, prop));
          }
        }
        // third
        else if (ZFormWrapper.class.isAssignableFrom(type))
        {
          ZFormWrapper fe = (ZFormWrapper) m.invoke(obj);
          if (fe == null)
          {
            throw new Exception("null dynamic form model returned from " + m.getName());
          }
          if (fe.getName() == null)
          {
            String feName = prefix + ZReflectionUtil.removePrefixName("get", m.getName());
            fe.setName(feName);
          }
          forms.add(fe);
        }
        // fourth
        else if (ZIForm.class.isAssignableFrom(type))
        {
          ZIForm fe = (ZIForm) m.invoke(obj);
          if (fe == null)
          {
            throw new Exception("null form model returned from " + m.getName());
          }
          String feName = ZReflectionUtil.removePrefixName("get", m.getName());
          forms.add(new ZFormWrapper(fe, prefix + feName));
        }
      }
    }
  }


  public String getName()
  {
    return name;
  }


  public Set<ZProperty> getPropertiesByName(final Set<String> propNames) throws Exception
  {
    final Set<ZProperty> ret = new HashSet<ZProperty>();
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop) throws Exception
      {
        String name = prop.getProperty().getName();
        if (propNames.contains(name))
        {
          ret.add(prop.getProperty());
        }
      }


      public void visit(ZOperationWrapper op) throws Exception
      {
      }
    };

    visitDepthFirst(visitor);

    return ret;
  }


  public void setName(String name)
  {
    this.name = name;
  }


  /**
   * returns the modified form members
   * 
   * @param formValues
   * @return
   * @throws Exception
   */
  public ZFormMembers readFromValues(ZFormValues formValues) throws Exception
  {
    final List<ZOperation> operations = new ArrayList<ZOperation>();
    final List<ZProperty> properties = new ArrayList<ZProperty>();
    final Map<String, String[]> values = formValues.getValues();
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop) throws Exception
      {
        String name = prop.getProperty().getName();
        String[] param = values.get(name);
        if (param != null)
        {
          prop.getProperty().setStringValues(param);
          properties.add(prop.getProperty());
        }
      }


      public void visit(ZOperationWrapper op) throws Exception
      {
        String name = op.getOperation().getName();
        String[] param = values.get(name);
        if (param != null)
        {
          op.getOperation().setStringValues(param);
          operations.add(op.getOperation());
        }
      }
    };

    visitDepthFirst(visitor);

    return new ZFormMembers(properties, operations);
  }


  public void writeToValues(ZFormValues formValues) throws Exception
  {
    final HashMap<String, String[]> values = formValues.getValues();
    values.clear();

    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop) throws Exception
      {
        if (!prop.getProperty().isEmpty())
        {
          values.put(prop.getProperty().getName(), new String[]
          {
              prop.getProperty().getStringValue()
          });
        }
      }


      public void visit(ZOperationWrapper op) throws Exception
      {
        // operations are not written
      }
    };

    visitDepthFirst(visitor);
  }


  // public void validate() throws Exception
  // {
  // ZIFormVisitor visitor = new ZIFormVisitor()
  // {
  // public void visit(ZProperty prop) throws Exception
  // {
  // prop.revalidate();
  // }
  //
  //
  // public void visit(ZOperation op) throws Exception
  // {
  // op.revalidate();
  // }
  // };
  // visitDepthFirst(visitor);
  // }

  public List<ZFormWrapper> getForms()
  {
    return forms;
  }


  public List<ZOperationWrapper> getOperations()
  {
    return operations;
  }


  public List<ZPropertyWrapper> getProperties()
  {
    return properties;
  }


  public void visitDepthFirst(ZIFormVisitor vis) throws Exception
  {
    for (ZFormWrapper form : forms)
    {
      form.visitDepthFirst(vis);
    }
    for (ZPropertyWrapper prop : properties)
    {
      vis.visit(prop);
    }
    for (ZOperationWrapper op : operations)
    {
      vis.visit(op);
    }
  }


  public void visitBreadthFirst(ZIFormVisitor vis) throws Exception
  {
    for (ZPropertyWrapper prop : properties)
    {
      vis.visit(prop);
    }
    for (ZOperationWrapper op : operations)
    {
      vis.visit(op);
    }
    for (ZFormWrapper form : forms)
    {
      form.visitBreadthFirst(vis);
    }
  }


  /**
   * recursively finds all properties for the given object
   * 
   * @param obj
   * @return
   * @throws Exception
   */
  public ZFormMembers getFormMembers() throws Exception
  {
    final List<ZProperty> properties = new ArrayList<ZProperty>();
    final List<ZOperation> operations = new ArrayList<ZOperation>();
    ZIFormVisitor vis = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop) throws Exception
      {
        properties.add(prop.getProperty());
      }


      public void visit(ZOperationWrapper op) throws Exception
      {
        operations.add(op.getOperation());
      }
    };
    this.visitDepthFirst(vis);
    ZFormMembers ret = new ZFormMembers(properties, operations);
    return ret;
  }


  public ZScriptDependency getJavaScriptDependency() throws Exception
  {
    final ZScriptDependency ret = new ZScriptDependency();

    ZIFormVisitor vis = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop) throws Exception
      {
        List<ZIValidator> validators = prop.getProperty().getValidators();
        for (ZIValidator val : validators)
        {
          ZScript script = ZFormWrapper.getAnnotation(val.getClass(), ZScript.class);
          if (script != null)
          {
            ret.add(script);
          }
        }
      }


      public void visit(ZOperationWrapper op) throws Exception
      {
        List<ZIValidator> validators = op.getOperation().getValidators();
        for (ZIValidator val : validators)
        {
          ZScript script = ZFormWrapper.getAnnotation(val.getClass(), ZScript.class);
          if (script != null)
          {
            ret.add(script);
          }
        }
      }
    };

    this.visitDepthFirst(vis);
    return ret;
  }


  private static <T extends Annotation> T getAnnotation(Class c, Class<T> annClass) throws Exception
  {
    T ret = (T) c.getAnnotation(annClass);
    for (Class crtClass = c; ret == null && !Object.class.equals(crtClass); crtClass = crtClass.getSuperclass())
    {
      ret = (T) crtClass.getAnnotation(annClass);
    }
    return ret;
  }


  public void setWriteable(final boolean b) throws Exception
  {
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop) throws Exception
      {
        prop.getProperty().setWriteable(b);
      }


      public void visit(ZOperationWrapper op) throws Exception
      {
        op.getOperation().setWriteable(b);
      }
    };
    this.visitDepthFirst(visitor);
  }


  public void setReadable(final boolean b) throws Exception
  {
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop) throws Exception
      {
        prop.getProperty().setReadable(b);
      }


      public void visit(ZOperationWrapper op) throws Exception
      {
        op.getOperation().setReadable(b);
      }
    };
    this.visitDepthFirst(visitor);
  }


  public void setRequired(final boolean b) throws Exception
  {
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop) throws Exception
      {
        prop.getProperty().setRequired(b);
      }


      public void visit(ZOperationWrapper op) throws Exception
      {
      }
    };
    this.visitDepthFirst(visitor);
  }
}
