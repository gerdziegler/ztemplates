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

  static final char LIST_SEPARATOR = '-';

  static final char PROP_SEPARATOR = '_';

  private final boolean enforcePrefix;

  private String name;

  private final List<ZFormWrapper> forms = new ArrayList<ZFormWrapper>();

  private final List<ZPropertyWrapper> properties = new ArrayList<ZPropertyWrapper>();

  private final List<ZOperationWrapper> operations = new ArrayList<ZOperationWrapper>();

  private final ZIForm obj;


  /**
   * creates form model with name "" (empty String)
   * 
   * @param obj
   * @throws Exception
   */
  public ZFormWrapper(ZIForm obj)
  {
    this(obj, "", false);
  }


  /**
   * creates form Model with specified name
   * 
   * @param obj
   * @param name
   * @throws Exception
   */
  public ZFormWrapper(ZIForm obj,
      String name)
  {
    this(obj, name, true);
  }


  private ZFormWrapper(ZIForm obj,
      String name,
      boolean enforcePrefix)
  {
    this.obj = obj;
    this.enforcePrefix = enforcePrefix;
    this.name = name;
    String prefix;
    if (name.length() > 0)
    {
      prefix = name + PROP_SEPARATOR;
    }
    else
    {
      prefix = "";
    }

    Set<String> names = new HashSet<String>();
    try
    {
      for (Class clazz = obj.getClass(); ZIForm.class.isAssignableFrom(clazz); clazz = clazz.getSuperclass())
      {
        addFields(prefix, clazz, obj, names);
        addMethods(prefix, clazz, obj, names);
      }
    }
    catch (Exception e)
    {
      String msg = obj + " " + name;
      log.error(msg, e);
      throw new RuntimeException(msg, e);
    }
  }


  private void addFields(String prefix, Class clazz, Object obj, Set<String> names) throws Exception
  {
    for (Field f : clazz.getDeclaredFields())
    {
      Class type = f.getType();
      if (!f.isAccessible())
      {
        f.setAccessible(true);
      }
      // ORDER given by extends relation
      // first
      if (ZOperation.class.isAssignableFrom(type))
      {
        ZOperation op = (ZOperation) f.get(obj);
        String inferredName = f.getName();
        addOperation(obj, prefix, inferredName, op, names);
      }
      // second
      else if (ZProperty.class.isAssignableFrom(type))
      {
        ZProperty prop = (ZProperty) f.get(obj);
        String inferredName = f.getName();
        addProperty(obj, prefix, inferredName, prop, names);
      }
      else if (ZIForm.class.isAssignableFrom(type))
      {
        ZIForm fe = (ZIForm) f.get(obj);
        String inferredName = f.getName();
        addForm(obj, prefix, inferredName, fe, names);
      }
      else if (List.class.isAssignableFrom(type))
      {
        List list = (List) f.get(obj);
        String inferredName = f.getName();
        addList(obj, prefix, inferredName, list, names);
      }
      else if (ZFormWrapper.class.isAssignableFrom(type))
      {
        ZFormWrapper fe = (ZFormWrapper) f.get(obj);
        String inferredName = f.getName();
        addFormWrapper(obj, prefix, inferredName, fe, names);
      }
      /*else
      {
        log.warn("unsupported form value type: " + obj.getClass() + "." + f.getName() + " " + type);
      }*/
    }
  }


  private void addMethods(String prefix, Class clazz, Object obj, Set<String> names) throws Exception
  {
    for (Method m : clazz.getDeclaredMethods())
    {
      if (!m.isAccessible())
      {
        m.setAccessible(true);
      }

      if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())
          && !m.getName().equals("getClass"))
      {
        Class type = m.getReturnType();
        // ORDER given by extends relation
        // first
        if (ZOperation.class.isAssignableFrom(type))
        {
          ZOperation op = (ZOperation) m.invoke(obj);
          String inferredName = ZReflectionUtil.removePrefixName("get", m.getName());
          addOperation(obj, prefix, inferredName, op, names);
        }
        // second
        else if (ZProperty.class.isAssignableFrom(type))
        {
          ZProperty prop = (ZProperty) m.invoke(obj);
          String inferredName = ZReflectionUtil.removePrefixName("get", m.getName());
          addProperty(obj, prefix, inferredName, prop, names);
        }
        else if (ZIForm.class.isAssignableFrom(type))
        {
          ZIForm fe = (ZIForm) m.invoke(obj);
          String inferredName = ZReflectionUtil.removePrefixName("get", m.getName());
          addForm(obj, prefix, inferredName, fe, names);
        }
        else if (List.class.isAssignableFrom(type))
        {
          List l = (List) m.invoke(obj);
          String inferredName = ZReflectionUtil.removePrefixName("get", m.getName());
          addList(obj, prefix, inferredName, l, names);
        }
        else if (ZFormWrapper.class.isAssignableFrom(type))
        {
          ZFormWrapper fe = (ZFormWrapper) m.invoke(obj);
          String inferredName = ZReflectionUtil.removePrefixName("get", m.getName());
          addFormWrapper(obj, prefix, inferredName, fe, names);
        }
        /*       else
               {
                 log.warn("unsupported form value type: " + obj.getClass() + "." + m.getName() + " " + type);
               }*/
      }
    }
  }


  private void addForm(Object obj, String prefix, String inferredName, ZIForm form, Set<String> names)
  {
    if (form == null)
    {
      throw new RuntimeException("null value in " + obj.getClass() + "." + inferredName);
    }

    String formName = prefix + inferredName;
    if (!duplicateName(obj, formName, names))
    {
      forms.add(new ZFormWrapper(form, formName, enforcePrefix));
    }
  }


  private void addFormWrapper(Object obj, String prefix, String inferredName, ZFormWrapper wrap, Set<String> names)
  {
    if (wrap == null)
    {
      throw new RuntimeException("null value in " + obj.getClass() + "." + inferredName);
    }

    String wrapName = computeName(wrap.getName(), prefix, inferredName);
    if (!duplicateName(obj, wrapName, names))
    {
      wrap.setName(wrapName);
      forms.add(wrap);
    }
  }


  private String computeName(String name, String prefix, String inferredName)
  {
    if (name == null)
    {
      return prefix + inferredName;
    }
    if (enforcePrefix && !name.startsWith(prefix))
    {
      return prefix + name;
    }
    return name;
  }


  private void addOperation(Object obj, String prefix, String inferredName, ZOperation op, Set<String> names)
  {
    if (op == null)
    {
      throw new RuntimeException("null value in " + obj.getClass() + "." + inferredName);
    }

    String opName = computeName(op.getName(), prefix, inferredName);
    if (!duplicateName(obj, opName, names))
    {
      op.setName(opName);
      if (op.getAllowedValue() == null)
      {
        op.setAllowedValue(opName);
      }
      operations.add(new ZOperationWrapper(opName, op));
    }
  }


  private void addProperty(Object obj, String prefix, String inferredName, ZProperty prop, Set<String> names)
  {
    if (prop == null)
    {
      throw new RuntimeException("null value in " + obj.getClass() + "." + inferredName);
    }

    String propName = computeName(prop.getName(), prefix, inferredName);
    if (!duplicateName(obj, propName, names))
    {
      prop.setName(propName);
      properties.add(new ZPropertyWrapper(propName, prop));
    }
  }


  private List<ZFormWrapper> addList(Object obj, String prefix, String inferredName, List list, Set<String> names)
  {
    if (list == null)
    {
      throw new RuntimeException("null value in List property " + obj.getClass() + "." + inferredName);
    }

    List<ZFormWrapper> ret = new ArrayList<ZFormWrapper>();
    int listSize = list.size();
    for (int i = 0; i < listSize; i++)
    {
      Object crt = list.get(i);
      if (crt instanceof ZIForm)
      {
        ZIForm fe = (ZIForm) crt;
        String feName = prefix + inferredName + LIST_SEPARATOR + i + LIST_SEPARATOR + listSize;
        if (!duplicateName(obj, feName, names))
        {
          ZFormWrapper fw = new ZFormWrapper(fe, feName, enforcePrefix);
          forms.add(fw);
          ret.add(fw);
        }
      }
      else
      {
        break;
      }
    }
    return ret;
  }


  private boolean duplicateName(Object obj, String name, Set<String> names)
  {
    if (names.contains(name))
    {
      return true;
    }
    else
    {
      names.add(name);
      return false;
    }
  }


  public String getName()
  {
    return name;
  }


  public Set<ZProperty> getPropertiesByName(final Set<String> propNames)
  {
    final Set<ZProperty> ret = new HashSet<ZProperty>();
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop)
      {
        String name = prop.getProperty().getName();
        if (propNames.contains(name))
        {
          ret.add(prop.getProperty());
        }
      }


      public void visit(ZOperationWrapper op)
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
  public ZFormMembers readFromValues(ZFormValues formValues)
  {
    updateListElements(formValues);
    final List<ZOperation> operations = new ArrayList<ZOperation>();
    final List<ZProperty> properties = new ArrayList<ZProperty>();
    final Map<String, String[]> values = formValues.getValues();
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop)
      {
        String name = prop.getProperty().getName();
        String[] param = values.get(name);
        if (param != null)
        {
          prop.getProperty().setStringValues(param);
          properties.add(prop.getProperty());
        }
      }


      public void visit(ZOperationWrapper op)
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


  /**
   * creates ListElement subforms based on the passed values.
   * @param formValues
   */
  private void updateListElements(ZFormValues formValues)
  {
    Set<String> processedListNames = new HashSet<String>();
    try
    {
      String formPrefix = name.length() == 0 ? "" : name + PROP_SEPARATOR;
      int formPrefixIdx = formPrefix.length();
      for (Map.Entry<String, String[]> en : formValues.getValues().entrySet())
      {
        String propName = en.getKey();
        int idx1 = propName.indexOf(LIST_SEPARATOR, formPrefixIdx);
        if (idx1 < 0)
        {
          //not a list property
          continue;
        }
        String fieldName = propName.substring(formPrefixIdx, idx1);
        if (processedListNames.contains(fieldName))
        {
          continue;
        }
        int idx2 = propName.indexOf(LIST_SEPARATOR, idx1 + 1);
        int idx3 = propName.indexOf(PROP_SEPARATOR, idx2 + 1);
        String indexName = propName.substring(idx1 + 1, idx2);
        String sizeName = propName.substring(idx2 + 1, idx3);
        int size = Integer.valueOf(sizeName);
        Field field = ZReflectionUtil.getField(obj.getClass(), fieldName);
        List list = (List) field.get(obj);
        if (list.size() == size)
        {
          //list initialized by program
          processedListNames.add(fieldName);
          continue;
        }
        String methodName = ZReflectionUtil.computePrefixName("init", fieldName);
        try
        {
          String prefix = formPrefix + fieldName + LIST_SEPARATOR;
          List<ZFormWrapper> formList = new ArrayList<ZFormWrapper>(forms);
          for (ZFormWrapper fw : formList)
          {
            if (fw.getName().startsWith(prefix))
            {
              forms.remove(fw);
            }
          }
          Method listInitializer = obj.getClass().getMethod(methodName, int.class);
          list.clear();
          listInitializer.invoke(obj, size);
          list = (List) field.get(obj);
          List<ZFormWrapper> newForms = addList(obj, formPrefix, fieldName, list, processedListNames);
          for (ZFormWrapper fw : newForms)
          {
            fw.updateListElements(formValues);
          }
        }
        catch (NoSuchMethodException e)
        {
          //OK, need no initializer
        }
        processedListNames.add(fieldName);
      }
    }
    catch (Exception e)
    {
      throw new RuntimeException("cannot initialize lists " + obj, e);
    }
  }


  @Override
  public String toString()
  {
    return getClass().getName() + "+" + name;
  }


  public void writeToValues(ZFormValues formValues)
  {
    final HashMap<String, String[]> values = formValues.getValues();
    values.clear();

    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop)
      {
        if (!prop.getProperty().isEmpty())
        {
          values.put(prop.getProperty().getName(), prop.getProperty().getStringValues());
        }
      }


      public void visit(ZOperationWrapper op)
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


  public void visitDepthFirst(ZIFormVisitor vis)
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


  public void visitBreadthFirst(ZIFormVisitor vis)
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
  public ZFormMembers getFormMembers()
  {
    final List<ZProperty> properties = new ArrayList<ZProperty>();
    final List<ZOperation> operations = new ArrayList<ZOperation>();
    ZIFormVisitor vis = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop)
      {
        properties.add(prop.getProperty());
      }


      public void visit(ZOperationWrapper op)
      {
        operations.add(op.getOperation());
      }
    };
    this.visitDepthFirst(vis);
    ZFormMembers ret = new ZFormMembers(properties, operations);
    return ret;
  }


  public ZScriptDependency getJavaScriptDependency()
  {
    final ZScriptDependency ret = new ZScriptDependency();

    ZIFormVisitor vis = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop)
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


      public void visit(ZOperationWrapper op)
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


  private static <T extends Annotation> T getAnnotation(Class c, Class<T> annClass)
  {
    T ret = (T) c.getAnnotation(annClass);
    for (Class crtClass = c; ret == null && !Object.class.equals(crtClass); crtClass = crtClass.getSuperclass())
    {
      ret = (T) crtClass.getAnnotation(annClass);
    }
    return ret;
  }


  public void setWriteable(final boolean b)
  {
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop)
      {
        prop.getProperty().setWriteable(b);
      }


      public void visit(ZOperationWrapper op)
      {
        op.getOperation().setWriteable(b);
      }
    };
    this.visitDepthFirst(visitor);
  }


  public void setReadable(final boolean b)
  {
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop)
      {
        prop.getProperty().setReadable(b);
      }


      public void visit(ZOperationWrapper op)
      {
        op.getOperation().setReadable(b);
      }
    };
    this.visitDepthFirst(visitor);
  }


  public void setRequired(final boolean b)
  {
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZPropertyWrapper prop)
      {
        prop.getProperty().setRequired(b);
      }


      public void visit(ZOperationWrapper op)
      {
      }
    };
    this.visitDepthFirst(visitor);
  }
}
