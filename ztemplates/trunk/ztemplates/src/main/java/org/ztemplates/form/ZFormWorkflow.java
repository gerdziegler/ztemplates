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
package org.ztemplates.form;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ztemplates.property.ZError;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;
import org.ztemplates.web.ZIServletService;
import org.ztemplates.web.ZTemplates;

/**
 * infrastructure for form processing workflows. extend to create your own workflow.
 * 
 * @author www.gerdziegler.de
 */
public abstract class ZFormWorkflow<T extends ZIFormElement> implements ZIFormWorkflow<T>
{
  protected final T form;

  protected ZFormElementMirror mirr;

  protected final List<ZError> errors = new ArrayList<ZError>();
  
  protected ZOperation operation;
  

  public ZFormWorkflow(T form) throws Exception
  {
    this.form = form;
    this.mirr = new ZFormElementMirror(form);
  }



  public void update() throws Exception
  {
    mirr.update();
  }


  public void revalidate() throws Exception
  {
    mirr.revalidate();
    List<ZProperty> errProps = mirr.getPropertiesWithError();
    for (ZProperty prop : errProps)
    {
      errors.add((ZError) prop.getState());
    }
  }


  public ZOperation assign() throws Exception
  {
    ZIServletService ss = ZTemplates.getServletService();
    final Map<String, String[]> parameters = new HashMap<String, String[]>(ss.getRequest().getParameterMap());
    
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

    mirr.visitDepthFirst(visitor);

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
      this.operation = op;      
    }
    else
    {
      this.operation = null;
    }
    
    return operation;
  }

  public void beforeUpdate()
  {
  }


  public void afterUpdate()
  {
  }

  public void beforeRevalidate()
  {
  }


  public void afterRevalidate()
  {
  }


  public T getForm()
  {
    return form;
  }


  public List<ZError> getErrors()
  {
    return errors;
  }



  public ZOperation getOperation()
  {
    return operation;
  }
}
