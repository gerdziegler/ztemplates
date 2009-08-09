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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.ztemplates.actions.util.ZReflectionUtil;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;
import org.ztemplates.property.ZState;
import org.ztemplates.property.validator.ZIStringValidator;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.ZScriptDependency;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.ZTemplates;

public class ZFormElementMirror implements ZIFormVisitable
{
  static final Logger log = Logger.getLogger(ZFormElementMirror.class);

  private final ZIFormElement formElement;

  private final List<ZProperty> properties = new ArrayList<ZProperty>();

  private final List<ZOperation> operations = new ArrayList<ZOperation>();

  private final List<ZFormElementMirror> formElements = new ArrayList<ZFormElementMirror>();


  public ZFormElementMirror(ZIFormElement obj) throws Exception
  {
    this(obj, "");
  }


  private ZFormElementMirror(ZIFormElement obj, String prefix) throws Exception
  {
    super();
    this.formElement = obj;

    for (Method m : obj.getClass().getMethods())
    {
      if (m.getName().startsWith("get") && m.getParameterTypes().length == 0)
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
          if (op.getName() == null)
          {
            String opName = ZReflectionUtil.removePrefixName("get", m.getName());
            op.setName(prefix + opName);
          }
          operations.add(op);
        }
        // second
        else if (ZProperty.class.isAssignableFrom(returnType))
        {
          ZProperty prop = (ZProperty) m.invoke(obj);
          if (prop.getName() == null)
          {
            String propName = ZReflectionUtil.removePrefixName("get", m.getName());
            prop.setName(prefix + propName);
          }
          properties.add(prop);
        }
        // third
        else if (ZIFormElement.class.isAssignableFrom(returnType))
        {
          ZIFormElement fe = (ZIFormElement) m.invoke(obj);
          if (fe == null)
          {
            throw new Exception("null formelement " + m);
          }
          String feName = ZReflectionUtil.removePrefixName("get", m.getName());
          //          initPropertyNames(formData, prefix + propName + ".");
          formElements.add(new ZFormElementMirror(fe, prefix + feName + "."));
        }
      }
    }
  }


  public Set<ZProperty> getPropertiesByName(Set<String> propNames) throws Exception
  {
    Set<ZProperty> ret = new HashSet<ZProperty>();
    for (String propName : propNames)
    {
      Object prop = ZReflectionUtil.getObjectByBeanPath(formElement, propName);
      ret.add((ZProperty) prop);
    }
    return ret;
  }


  //  private static void initPropertyNames(ZIFormElement formElement, String prefix) throws Exception
  //  {
  //    for (Method m : formElement.getClass().getMethods())
  //    {
  //      if (m.getName().startsWith("get") && m.getParameterTypes().length == 0)
  //      {
  //        Class type = m.getReturnType();
  //
  //        if (ZOperation.class.isAssignableFrom(type))
  //        {
  //          ZOperation op = (ZOperation) m.invoke(formElement);
  //          String opName = ZReflectionUtil.removePrefixName("get", m.getName());
  //          op.setName(prefix + opName);
  //        }
  //        else if (ZProperty.class.isAssignableFrom(type))
  //        {
  //          ZProperty prop = (ZProperty) m.invoke(formElement);
  //          String propName = ZReflectionUtil.removePrefixName("get", m.getName());
  //          prop.setName(prefix + propName);
  //        }
  //        else if (ZIFormElement.class.isAssignableFrom(type))
  //        {
  //          ZIFormElement formData = (ZIFormElement) m.invoke(formElement);
  //          String propName = ZReflectionUtil.removePrefixName("get", m.getName());
  //          initPropertyNames(formData, prefix + propName + ".");
  //        }
  //        //        else if (List.class.isAssignableFrom(type))
  //        //        {
  //        //          List list = (List) m.invoke(form);
  //        //          if (list != null && !list.isEmpty())
  //        //          {
  //        //            Object first = list.get(0);
  //        //            if (first instanceof ZIFormElement)
  //        //            {
  //        //              String propName = ZReflectionUtil.removePrefixName("get", m.getName());
  //        //              for (int i = 0; i < list.size(); i++)
  //        //              {
  //        //                initFormPropertyNames((ZIFormElement) list.get(i), prefix + propName + "[" + i
  //        //                    + "].");
  //        //              }
  //        //            }
  //        //          }
  //        //        }
  //        //        else if (type.isArray() && ZIFormElement.class.isAssignableFrom(type.getComponentType()))
  //        //        {
  //        //          Object arr = m.invoke(form);
  //        //          if (arr != null)
  //        //          {
  //        //            String propName = ZReflectionUtil.removePrefixName("get", m.getName());
  //        //            for (int i = 0; i < Array.getLength(arr); i++)
  //        //            {
  //        //              initFormPropertyNames((ZIFormElement) Array.get(arr, i), prefix + propName + "[" + i
  //        //                  + "].");
  //        //            }
  //        //          }
  //        //        }
  //      }
  //    }
  //  }

  public void readParameters() throws Exception
  {
    ZIServletService ss = ZTemplates.getServletService();
    final Map<String, String[]> parameters = new HashMap<String, String[]>(ss.getRequest()
        .getParameterMap());

    final List<ZOperation> operations = new ArrayList<ZOperation>();

    ZIFormVisitor visitor = new ZIFormVisitor()
    {
      public void visit(ZProperty prop) throws Exception
      {
        String name = prop.getName();
        String[] param = parameters.get(name);
        if (param != null)
        {
          prop.setStringValue(param[0]);
          parameters.remove(name);
        }
      }


      public void visit(ZOperation op) throws Exception
      {
        String name = op.getName();
        String[] param = parameters.get(name);
        if (param != null)
        {
          operations.add(op);
        }
      }
    };

    visitDepthFirst(visitor);
    revalidate();

    if (operations.size() > 1)
    {
      throw new Exception("Only one operation call per request allowed: " + operations);
    }
    else if (operations.size() == 1)
    {
      ZOperation op = operations.get(0);
      String name = op.getName();
      String[] param = parameters.get(name);
      op.setStringValue(param[0]);
      parameters.remove(name);
    }
  }


  public HashMap<String, String> getStringValues() throws Exception
  {
    ZFormMembers members = getFormMembers();
    HashMap<String, String> values = new HashMap<String, String>();
    for (ZProperty prop : members.getProperties())
    {
      if (!prop.isEmpty())
      {
        values.put(prop.getName(), prop.getStringValue());
      }
    }
    return values;
  }


  //  public void readParameters() throws Exception
  //  {
  //    ZIServletService ss = ZTemplates.getServletService();
  //    Map<String, String[]> parameters = new HashMap<String, String[]>(ss.getRequest()
  //        .getParameterMap());
  //
  //    ZOperation operation = null;
  //
  //    List<Map.Entry<String, String[]>> remove = new ArrayList<Map.Entry<String, String[]>>();
  //    for (Map.Entry<String, String[]> en : parameters.entrySet())
  //    {
  //      String name = en.getKey();
  //      String[] value = en.getValue();
  //      try
  //      {
  //        ZProperty assignedProp = setParameter(formElement, name, value);
  //        if (assignedProp instanceof ZOperation)
  //        {
  //          if (operation != null)
  //          {
  //            throw new Exception("Only one operation call per request allowed: " + operation + " "
  //                + assignedProp);
  //          }
  //          operation = (ZOperation) assignedProp;
  //        }
  //        remove.add(en);
  //      }
  //      catch (Exception ex)
  //      {
  //        // OK, parameter must not be assignable to this object
  //      }
  //    }
  //    // eat the parameters
  //    parameters.entrySet().removeAll(remove);
  //
  ////    String propertyChanged = getNameOfPropertyChangedAndRemoveFromMap(parameters);
  ////    if (propertyChanged != null)
  ////    {
  ////      fireAjaxChangeListener(propertyChanged);
  ////    }
  //  }

  //  private static final Object PROPERTY_CHANGED = "ztemplates.onChange";
  //
  //
  //  private static String getNameOfPropertyChangedAndRemoveFromMap(Map<String, String[]> parameters)
  //  {
  //    String[] changed = parameters.get(PROPERTY_CHANGED);
  //    if (changed != null)
  //    {
  //      parameters.remove(PROPERTY_CHANGED);
  //      return changed[0];
  //    }
  //    else
  //    {
  //      return null;
  //    }
  //  }

  private static ZProperty setParameter(ZIFormElement obj, String paramName, String[] paramValue)
      throws Exception
  {
    // get form if existent
    Object crtObj;
    String crtName;
    int idx = paramName.lastIndexOf('.');
    if (idx >= 0)
    {
      crtName = paramName.substring(idx + 1);
      String beanPath = paramName.substring(0, idx);
      crtObj = ZReflectionUtil.getObjectByBeanPath(obj, beanPath);
      idx = crtName.indexOf('.');
    }
    else
    {
      crtObj = obj;
      crtName = paramName;
    }

    return ZReflectionUtil.callParameterSetter(crtObj, crtName, paramValue);
  }


  //  public void fireAjaxChangeListener(String propertyChanged) throws Exception
  //  {
  //    ZIFormVisitor visitor = new ZIFormVisitor()
  //    {
  //      public void visit(ZProperty prop) throws Exception
  //      {
  //        prop.setEnableChangeListeners(true);
  //      }
  //
  //
  //      public void visit(ZOperation op) throws Exception
  //      {
  //        op.setEnableChangeListeners(true);
  //      }
  //    };
  //    visitDepthFirst(visitor);
  //
  //    ZProperty prop = (ZProperty) ZReflectionUtil.getObjectByBeanPath(formElement, propertyChanged);
  //    prop.fireChangeListeners();
  //  }

  public void revalidate() throws Exception
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


  /**
   * 
   * @param formElement
   * @throws Exception
   */
  public ZFormState process() throws Exception
  {
    //    ZFormElementMirror.initPropertyNames(formElement, "");
    //validates required
    //revalidate();
    readParameters();

    update();

    List<ZState> errors = new ArrayList<ZState>();
    return new ZFormState(errors);
  }


  public List<ZFormElementMirror> getFormElements()
  {
    return formElements;
  }


  public List<ZOperation> getOperations()
  {
    return operations;
  }


  public List<ZProperty> getProperties()
  {
    return properties;
  }


  public ZIFormElement getFormElement()
  {
    return formElement;
  }


  public void visitDepthFirst(ZIFormVisitor vis) throws Exception
  {
    for (ZFormElementMirror formElem : formElements)
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


  //  public void revalidateAndUpdate() throws Exception
  //  {
  //    revalidate();
  //    update();
  //  }

  /**
   */
  public void update() throws Exception
  {
    //depth first
    for (ZFormElementMirror formElem : formElements)
    {
      formElem.update();
    }
    formElement.update();
  }


  //  public void revalidate() throws Exception
  //  {
  //    //depth first
  //    for (ZFormElementMirror formElem : formElements)
  //    {
  //      formElem.revalidate();
  //    }
  //    for (ZProperty prop : properties)
  //    {
  //      prop.revalidate();
  //    }
  //    //depth first
  //    formElement.update();
  //    //operations last
  //    for (ZOperation op : operations)
  //    {
  //      op.revalidate();
  //    }
  //  }

  //  public JSONObject computeJson() throws Exception
  //  {
  //    JSONObject formJson = ZJsonUtil.computeJSON(formElement);
  //    return formJson;
  //  }

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
