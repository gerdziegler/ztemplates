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

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

import org.zdependency.ZIDependencyContext;
import org.zdependency.ZIDependencyManaged;
import org.zdependency.impl.ZDependencyManager;
import org.zdependency.output.ZINodeLabeler;
import org.zdependency.output.graphml.ZDependencyGraphMLWriter;
import org.zdependency.output.xml.ZDependencyXMLWriter;
import org.ztemplates.property.ZProperty;

/**
 * form processing workflow with dependency management
 * 
 * @author www.gerdziegler.de
 */
public abstract class ZDependencyFormWorkflow<T extends ZIFormElement> extends ZFormWorkflow<T>
{
  private final ZDependencyManager<ZProperty> dependencyManager;

  private ZIDependencyContext<ZProperty> dependencyContext;


  public ZDependencyFormWorkflow(T form, ZDependencyManager<ZProperty> dependencyManager)
      throws Exception
  {
    super(form);
    this.dependencyManager = dependencyManager;
  }


  public ZDependencyFormWorkflow(T form) throws Exception
  {
    super(form);
    this.dependencyManager = new ZDependencyManager<ZProperty>((ZIDependencyManaged<ZProperty>) form);
  }


  public abstract Set<ZProperty> computeChangedProperties() throws Exception;


  public ZIDependencyContext<ZProperty> updateDependencies() throws Exception
  {
    Set<ZProperty> changed = computeChangedProperties();
    return dependencyManager.process(changed);
  }


  public void execute() throws Exception
  {
    assign();

    dependencyContext = updateDependencies();
    
    update();
    
    revalidate();

//    if (operation != null)
//    {
//      if (!getErrors().isEmpty())
//      {
//        //handle errors here
//        onErrors();
//      }
//      
//      //handle submit here, maybe navigate to next view
//      execCommand();
//      
//      //
//      showView(workflow);
//    }
//    else
//    {
//      execShow();
//      updateView();
//    }
  }


  public ZDependencyManager<ZProperty> getDependencyManager()
  {
    return dependencyManager;
  }


  public ZIDependencyContext<ZProperty> getDependencyContext()
  {
    return dependencyContext;
  }


  public void printRuntimeInfo() throws Exception
  {
    ZDependencyXMLWriter.toXML(dependencyContext, new ZINodeLabeler<ZProperty>()
    {
      public String getLabel(ZProperty node)
      {
        return node.getName();
      }
    }, new PrintWriter(System.out));
  }

  public void printStructureInfo() throws Exception
  {
    ZDependencyXMLWriter.toXML(dependencyManager, new ZINodeLabeler<ZProperty>()
    {
      public String getLabel(ZProperty node)
      {
        return node.getName();
      }
    }, new PrintWriter(System.out));
  }

  public void printInfo() throws Exception
  {
    ZDependencyXMLWriter.toXML(dependencyManager, dependencyContext, new ZINodeLabeler<ZProperty>()
    {
      public String getLabel(ZProperty node)
      {
        return node.getName();
      }
    }, new PrintWriter(System.out));
  }

  public void printGraphML(Writer w) throws Exception
  {
    ZDependencyGraphMLWriter.toGraphML(dependencyManager, dependencyContext, new ZINodeLabeler<ZProperty>()
    {
      public String getLabel(ZProperty node)
      {
        return node.getName();
      }
    }, w);
  }
}
