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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Stack;

import org.apache.log4j.Logger;
import org.ztemplates.form.ZForm;
import org.ztemplates.form.ZFormList;
import org.ztemplates.form.ZFormMembers;
import org.ztemplates.form.ZFormValues;
import org.ztemplates.form.ZIForm;
import org.ztemplates.form.mirr.ZFormMirror;
import org.ztemplates.form.mirr.ZIFormMirror;
import org.ztemplates.form.visitor.ZAbstractFormVisitor;
import org.ztemplates.form.visitor.ZFormWalker;
import org.ztemplates.form.visitor.ZIFormVisitor;
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
public final class ZFormWrapper
{
  static final Logger log = Logger.getLogger(ZFormWrapper.class);

  public static final char LIST_SEPARATOR = '-';

  public static final char PROP_SEPARATOR = '_';

  //  private final boolean enforcePrefix;
  //
  private String name;

  //
  private final ZIForm obj;

  private ZFormMirror formMirror;

  private ZFormWalker walker = new ZFormWalker();


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


  public ZFormWrapper(ZIForm obj,
      String name,
      boolean enforcePrefix)
  {
    this.obj = obj;
    this.name = name;
    updateNames(name, enforcePrefix);
  }


  public void updateNames(final String name, final boolean enforcePrefix)
  {

    final Stack<String> prefixStack = new Stack<String>();
    prefixStack.push(name);

    final Stack<ZIFormMirror> mirrorStack = new Stack<ZIFormMirror>();
    formMirror = new ZFormMirror(obj);
    mirrorStack.push(formMirror);

    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void before(String fieldName, ZProperty prop)
      {
        String crtName = computeName(prop.getName(), prefixStack.peek(), fieldName, enforcePrefix);
        prefixStack.push(crtName);
      }


      public void visit(ZProperty prop)
      {
        prop.setName(prefixStack.peek());
      }


      public void after(String fieldName, ZProperty prop)
      {
        prefixStack.pop();
      }


      public void before(String fieldName, ZOperation op)
      {
        String crtName = computeName(op.getName(), prefixStack.peek(), fieldName, enforcePrefix);
        prefixStack.push(crtName);
      }


      public void visit(ZOperation op)
      {
        op.setName(prefixStack.peek());
      }


      public void after(String fieldName, ZOperation op)
      {
        prefixStack.pop();
      }


      public void before(String fieldName, ZIForm form)
      {
        String crtName = computeName(null, prefixStack.peek(), fieldName, enforcePrefix);
        prefixStack.push(crtName);
      }


      public void before(String fieldName, ZIForm form, int idx, int cnt)
      {
        String crtName = computeName(prefixStack.peek(), fieldName, idx, cnt);
        prefixStack.push(crtName);
      }


      public void visit(ZIForm form)
      {
      }


      public void after(String fieldName, ZIForm form, int idx, int cnt)
      {
        prefixStack.pop();
      }


      public void after(String fieldName, ZIForm form)
      {
        prefixStack.pop();
      }


      public void before(String fieldName, ZFormList<ZIForm> list)
      {
        String crtName = computeName(list.getName(), prefixStack.peek(), fieldName, enforcePrefix);
        prefixStack.push(crtName);
      }


      public void visit(ZFormList<ZIForm> list)
      {
        list.setName(prefixStack.peek());
      }


      public void after(String fieldName, ZFormList<ZIForm> list)
      {
        prefixStack.pop();
      }


      public void before(String fieldName, ZForm form)
      {
        String crtName = computeName(form.getName(), prefixStack.peek(), fieldName, enforcePrefix);
        prefixStack.push(crtName);
      }


      public void visit(ZForm form)
      {
        String name = prefixStack.peek();
        form.setName(name);
      }


      public void after(String fieldName, ZForm form)
      {
        prefixStack.pop();
      }
    };
    walker.visitDepthFirst(obj, visitor);
  }


  private static String computeName(String name, String prefix, String inferredName, boolean enforcePrefix)
  {
    if (prefix.length() > 0)
    {
      prefix = prefix + PROP_SEPARATOR;
    }
    else
    {
      prefix = "";
    }

    if (name == null)
    {
      return prefix + inferredName;
    }
    if (enforcePrefix && !name.startsWith(prefix))
    {
      int idx = name.lastIndexOf(PROP_SEPARATOR);
      if (idx > 0)
      {
        String nameOld = name.substring(idx + 1);
        String ret = prefix + nameOld;
        return ret;
      }
      return prefix + name;
    }
    return name;
  }


  private static String computeName(String prefix, String inferredName, int idx, int cnt)
  {
    return prefix + inferredName + LIST_SEPARATOR + idx + LIST_SEPARATOR + cnt;
  }


  public Set<ZProperty> getPropertiesByName(final Set<String> propNames)
  {
    final Set<ZProperty> ret = new HashSet<ZProperty>();
    ZIFormVisitor visitor = new ZAbstractFormVisitor()
    {
      public void visit(ZProperty prop)
      {
        String name = prop.getName();
        if (propNames.contains(name))
        {
          ret.add(prop);
        }
      }
    };

    walker.visitDepthFirst(obj, visitor);

    return ret;
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
    //    initFormLists(formValues);

    final List<ZOperation> operations = new ArrayList<ZOperation>();
    final List<ZProperty> properties = new ArrayList<ZProperty>();
    final List<ZFormList> lists = new ArrayList<ZFormList>();
    final Map<String, String[]> values = formValues.getValues();
    ZIFormVisitor visitor = new ZAbstractFormVisitor()
    {
      public void visit(ZProperty prop)
      {
        String name = prop.getName();
        String[] param = values.get(name);
        if (param != null)
        {
          prop.setStringValues(param);
          properties.add(prop);
        }
      }


      public void visit(ZOperation op)
      {
        String name = op.getName();
        String[] param = values.get(name);
        if (param != null)
        {
          op.setStringValues(param);
          operations.add(op);
        }
      }


      public void before(String fieldName, ZFormList<ZIForm> list)
      {
        String prefix = list.getName() + LIST_SEPARATOR;
        for (String key : values.keySet())
        {
          if (key.startsWith(prefix))
          {
            int idx1 = key.indexOf(LIST_SEPARATOR, prefix.length());
            if (idx1 < 0)
            {
              continue;
            }
            int idx2 = key.indexOf(PROP_SEPARATOR, idx1);
            if (idx2 < 0)
            {
              continue;
            }
            String idxTxt = key.substring(idx1 + 1, idx2);
            try
            {
              int size = Integer.parseInt(idxTxt);
              if (size < 0 || size > 1000)
              {
                //DOS Attack?
                continue;
              }
              if (list.size() != size)
              {
                list.clear();
                for (int i = 0; i < size; i++)
                {
                  list.add(list.createForm(i, size));
                }
              }
              break;
            }
            catch (NumberFormatException e)
            {

            }
          }
        }
        super.before(fieldName, list);
      }


      public void before(String fieldName, ZForm form)
      {
        if (form.getForm() != null)
        {
          return;
        }

        String prefix = form.getName() + PROP_SEPARATOR;
        for (String key : values.keySet())
        {
          if (key.startsWith(prefix))
          {
            form.setForm(form.createForm());
            break;
          }
        }

        super.before(fieldName, form);
      }
    };

    walker.visitDepthFirst(obj, visitor);

    return new ZFormMembers(properties, operations, lists);
  }


  //  /**
  //   * creates ListElement subforms based on the passed values.
  //   * @param formValues
  //   */
  //  private void initFormLists(ZFormValues formValues)
  //  {
  //    Set<String> processedListNames = new HashSet<String>();
  //    try
  //    {
  //      String formPrefix = name.length() == 0 ? "" : name + PROP_SEPARATOR;
  //      int formPrefixIdx = formPrefix.length();
  //      for (Map.Entry<String, String[]> en : formValues.getValues().entrySet())
  //      {
  //        String propName = en.getKey();
  //        int idx1 = propName.indexOf(LIST_SEPARATOR, formPrefixIdx);
  //        if (idx1 < 0)
  //        {
  //          //not a list property
  //          continue;
  //        }
  //        String fieldName = propName.substring(formPrefixIdx, idx1);
  //        if (processedListNames.contains(fieldName))
  //        {
  //          continue;
  //        }
  //        int idx2 = propName.indexOf(LIST_SEPARATOR, idx1 + 1);
  //        int idx3 = propName.indexOf(PROP_SEPARATOR, idx2 + 1);
  //        String indexName = propName.substring(idx1 + 1, idx2);
  //        String sizeName = propName.substring(idx2 + 1, idx3);
  //        int size = Integer.valueOf(sizeName);
  //        Field field = ZReflectionUtil.getField(obj.getClass(), fieldName);
  //        ZFormList<ZIForm> list = (ZFormList<ZIForm>) field.get(obj);
  //        //        if (list.size() == size)
  //        //        {
  //        //          //list initialized by program
  //        //          processedListNames.add(fieldName);
  //        //          continue;
  //        //        }
  //        String prefix = formPrefix + fieldName + LIST_SEPARATOR;
  //        List<ZFormWrapper> formList = new ArrayList<ZFormWrapper>(forms);
  //        for (ZFormWrapper fw : formList)
  //        {
  //          if (fw.getName().startsWith(prefix))
  //          {
  //            forms.remove(fw);
  //          }
  //        }
  //        list.clear();
  //        for (int i = 0; i < size; i++)
  //        {
  //          ZIForm element = list.createListElement();
  //          list.add(element);
  //        }
  //
  //        List<ZFormWrapper> newForms = addFormList(obj, formPrefix, fieldName, list, processedListNames);
  //        for (ZFormWrapper fw : newForms)
  //        {
  //          fw.updateListElements(formValues);
  //        }
  //        processedListNames.add(fieldName);
  //      }
  //    }
  //    catch (Exception e)
  //    {
  //      throw new RuntimeException("cannot initialize lists " + obj, e);
  //    }
  //  }

  @Override
  public String toString()
  {
    return "[" + getClass().getName() + " " + obj + "]";
  }


  public void writeToValues(ZFormValues formValues)
  {
    final HashMap<String, String[]> values = formValues.getValues();
    values.clear();

    ZIFormVisitor visitor = new ZAbstractFormVisitor()
    {
      public void visit(ZProperty prop)
      {
        if (!prop.isEmpty())
        {
          values.put(prop.getName(), prop.getStringValues());
        }
      }
    };

    walker.visitDepthFirst(obj, visitor);
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
    final List<ZFormList> lists = new ArrayList<ZFormList>();
    ZIFormVisitor vis = new ZAbstractFormVisitor()
    {
      public void visit(ZProperty prop)
      {
        properties.add(prop);
      }


      public void visit(ZOperation op)
      {
        operations.add(op);
      }


      public void visit(ZFormList list)
      {
        lists.add(list);
      }
    };
    walker.visitDepthFirst(obj, vis);
    ZFormMembers ret = new ZFormMembers(properties, operations, lists);
    return ret;
  }


  public ZScriptDependency getJavaScriptDependency()
  {
    final ZScriptDependency ret = new ZScriptDependency();

    ZIFormVisitor vis = new ZAbstractFormVisitor()
    {
      public void visit(ZProperty prop)
      {
        List<ZIValidator> validators = prop.getValidators();
        for (ZIValidator val : validators)
        {
          ZScript script = ZFormWrapper.getAnnotation(val.getClass(), ZScript.class);
          if (script != null)
          {
            ret.add(script);
          }
        }
      }


      public void visit(ZOperation op)
      {
        List<ZIValidator> validators = op.getValidators();
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

    walker.visitDepthFirst(obj, vis);
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
    ZIFormVisitor visitor = new ZAbstractFormVisitor()
    {
      public void visit(ZProperty prop)
      {
        prop.setWriteable(b);
      }


      public void visit(ZOperation op)
      {
        op.setWriteable(b);
      }
    };
    walker.visitDepthFirst(obj, visitor);
  }


  public void setReadable(final boolean b)
  {
    ZIFormVisitor visitor = new ZAbstractFormVisitor()
    {
      public void visit(ZProperty prop)
      {
        prop.setReadable(b);
      }


      public void visit(ZOperation op)
      {
        op.setReadable(b);
      }

    };
    walker.visitDepthFirst(obj, visitor);
  }


  public void setRequired(final boolean b)
  {
    ZIFormVisitor visitor = new ZAbstractFormVisitor()
    {
      public void visit(ZProperty prop)
      {
        super.visit(prop);
        prop.setRequired(b);
      }
    };
    walker.visitDepthFirst(obj, visitor);
  }
}
