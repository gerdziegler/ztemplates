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
package org.ztemplates.form;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ztemplates.actions.util.ZReflectionUtil;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;
import org.ztemplates.property.validator.ZIStringValidator;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.ZScriptDependency;

/**
 * dynamic form that can be assembled at runtime in the beforeForm method
 * <b>should not be modified after that</b>.
 * 
 * @author gerdziegler.de
 * 
 */
public class ZDynamicFormModel implements ZIFormVisitable
{
  static final Logger log = Logger.getLogger(ZDynamicFormModel.class);

  private String name;

  private final List<ZDynamicFormModel> formModels = new ArrayList<ZDynamicFormModel>();

  private final List<ZProperty> properties = new ArrayList<ZProperty>();

  private final List<ZOperation> operations = new ArrayList<ZOperation>();


  /**
   * creates form model with name "" (empty String)
   * 
   * @param obj
   * @throws Exception
   */
  public ZDynamicFormModel(ZIFormModel obj) throws Exception
  {
    this(obj, "");
  }


  /**
   * creates form Model with specified name
   * 
   * @param obj
   * @param name
   * @throws Exception
   */
  protected ZDynamicFormModel(ZIFormModel obj, String name) throws Exception
  {
    this.name = name;
    String prefix;
    if (name.length() > 0)
    {
      prefix = name + ".";
    }
    else
    {
      prefix = "";
    }
    for (Method m : obj.getClass().getMethods())
    {
      if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && Modifier.isPublic(m.getModifiers()) && !Modifier.isStatic(m.getModifiers())
          && !m.getName().equals("getClass"))
      {
        Class returnType = m.getReturnType();
        /*
         * if (ZForm.class.isAssignableFrom(returnType)) { ZForm form = (ZForm)
         * m.invoke(obj); forms.add(form); } else
         */
        // ORDER given by extends relation
        // first
        if (ZOperation.class.isAssignableFrom(returnType))
        {
          ZOperation op = (ZOperation) m.invoke(obj);
          if (op == null)
          {
            throw new Exception("null op returned from " + m.getName());
          }
          if (op.getName() == null)
          {
            String opName = prefix + ZReflectionUtil.removePrefixName("get", m.getName());
            op.setName(opName);
          }
          operations.add(op);
        }
        // second
        else if (ZProperty.class.isAssignableFrom(returnType))
        {
          ZProperty prop = (ZProperty) m.invoke(obj);
          if (prop == null)
          {
            throw new Exception("null prop returned from " + m.getName());
          }
          if (prop.getName() == null)
          {
            String propName = prefix + ZReflectionUtil.removePrefixName("get", m.getName());
            prop.setName(propName);
          }
          properties.add(prop);
        }
        // third
        else if (ZDynamicFormModel.class.isAssignableFrom(returnType))
        {
          ZDynamicFormModel fe = (ZDynamicFormModel) m.invoke(obj);
          if (fe == null)
          {
            throw new Exception("null dynamic form model returned from " + m.getName());
          }
          if (fe.getName() == null)
          {
            String feName = prefix + ZReflectionUtil.removePrefixName("get", m.getName());
            fe.setName(feName);
          }
          formModels.add(fe);
        }
        // fourth
        else if (ZIFormModel.class.isAssignableFrom(returnType))
        {
          ZIFormModel fe = (ZIFormModel) m.invoke(obj);
          if (fe == null)
          {
            throw new Exception("null form model returned from " + m.getName());
          }
          String feName = prefix + ZReflectionUtil.removePrefixName("get", m.getName());
          formModels.add(new ZDynamicFormModel(fe, feName));
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
      public void visit(ZProperty prop) throws Exception
      {
        String name = prop.getName();
        if (propNames.contains(name))
        {
          ret.add(prop);
        }
      }


      public void visit(ZOperation op) throws Exception
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


  
  public ZFormMembers setFormValues(ZFormValues formValues) throws Exception
  {
    final List<ZOperation> operations = new ArrayList<ZOperation>();
    final List<ZProperty> properties = new ArrayList<ZProperty>();
    final Map<String, String[]> values = formValues.getValues();
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZProperty prop) throws Exception
      {
        String name = prop.getName();
        String[] param = values.get(name);
        if (param != null)
        {
          prop.setStringValue(param[0]);
          properties.add(prop);
        }
      }


      public void visit(ZOperation op) throws Exception
      {
        String name = op.getName();
        String[] param = values.get(name);
        if (param != null)
        {
          op.setStringValue(param[0]);
          operations.add(op);
        }
      }
    };

    visitDepthFirst(visitor);

    return new ZFormMembers(properties, operations);
  }


  public ZFormValues getFormValues() throws Exception
  {
    ZFormValues ret = new ZFormValues();
    ret.readFromForm(this);
    return ret;
  }


  public void validate() throws Exception
  {
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZProperty prop) throws Exception
      {
        prop.revalidate();
      }


      public void visit(ZOperation op) throws Exception
      {
        op.revalidate();
      }
    };
    visitDepthFirst(visitor);
  }


  public List<ZDynamicFormModel> getFormModels()
  {
    return formModels;
  }


  public List<ZOperation> getOperations()
  {
    return operations;
  }


  public List<ZProperty> getProperties()
  {
    return properties;
  }


  public void visitDepthFirst(ZIFormVisitor vis) throws Exception
  {
    for (ZDynamicFormModel formElem : formModels)
    {
      formElem.visitDepthFirst(vis);
    }
    for (ZProperty prop : properties)
    {
      vis.visit(prop);
    }
    for (ZOperation op : operations)
    {
      vis.visit(op);
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
      public void visit(ZProperty prop) throws Exception
      {
        properties.add(prop);
      }


      public void visit(ZOperation op) throws Exception
      {
        operations.add(op);
      }
    };
    this.visitDepthFirst(vis);
    ZFormMembers ret = new ZFormMembers(properties, operations);
    return ret;
  }


  public List<ZProperty> getPropertiesWithError() throws Exception
  {
    final List<ZProperty> properties = new ArrayList<ZProperty>();
    ZIFormVisitor vis = new ZIFormVisitor()
    {
      public void visit(ZProperty prop) throws Exception
      {
        if (prop.isError())
        {
          properties.add(prop);
        }
      }


      public void visit(ZOperation op) throws Exception
      {
        if (op.isError())
        {
          properties.add(op);
        }
      }
    };
    this.visitDepthFirst(vis);
    return properties;
  }


  public ZScriptDependency getJavaScriptDependency() throws Exception
  {
    final ZScriptDependency ret = new ZScriptDependency();

    ZIFormVisitor vis = new ZIFormVisitor()
    {
      public void visit(ZProperty prop) throws Exception
      {
        List<ZIStringValidator> validators = prop.getStringValidators();
        for (ZIStringValidator val : validators)
        {
          ZScript script = ZReflectionUtil.getAnnotation(val.getClass(), ZScript.class);
          if (script != null)
          {
            ret.add(script);
          }
        }
      }


      public void visit(ZOperation op) throws Exception
      {
        List<ZIStringValidator> validators = op.getStringValidators();
        for (ZIStringValidator val : validators)
        {
          ZScript script = ZReflectionUtil.getAnnotation(val.getClass(), ZScript.class);
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


  public void setWriteable(final boolean b) throws Exception
  {
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZProperty prop) throws Exception
      {
        prop.setWriteable(b);
      }


      public void visit(ZOperation op) throws Exception
      {
        op.setWriteable(b);
      }
    };
    this.visitDepthFirst(visitor);
  }


  public void setReadable(final boolean b) throws Exception
  {
    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZProperty prop) throws Exception
      {
        prop.setReadable(b);
      }


      public void visit(ZOperation op) throws Exception
      {
        op.setReadable(b);
      }
    };
    this.visitDepthFirst(visitor);
  }
}
