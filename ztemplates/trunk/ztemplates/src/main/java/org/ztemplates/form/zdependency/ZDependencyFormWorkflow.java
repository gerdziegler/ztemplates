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
package org.ztemplates.form.zdependency;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.Set;

import org.zdependency.ZIDependencyContext;
import org.zdependency.ZIDependencyManaged;
import org.zdependency.impl.ZDependencyManager;
import org.zdependency.output.ZINodeLabeler;
import org.zdependency.output.graphml.ZDependencyGraphMLWriter;
import org.zdependency.output.xml.ZDependencyXMLWriter;
import org.ztemplates.form.ZFormWorkflow;
import org.ztemplates.form.ZIFormElement;
import org.ztemplates.property.ZProperty;

/**
 * form processing workflow with dependency management
 * 
 * @author www.gerdziegler.de
 */
public class ZDependencyFormWorkflow<T extends ZIFormElement> extends ZFormWorkflow<T>
{
  private final ZDependencyManager<ZProperty> dependencyManager;

  private ZIDependencyContext<ZProperty> dependencyContext;

  private final ZIChangedProperties inputProvider;


  public ZDependencyFormWorkflow(T form,
      ZDependencyManager<ZProperty> dependencyManager,
      ZIChangedProperties inputProvider) throws Exception
  {
    super(form);
    this.dependencyManager = dependencyManager;
    this.inputProvider = inputProvider;
  }


  public ZDependencyFormWorkflow(T form, String parameterName) throws Exception
  {
    super(form);
    this.dependencyManager = new ZDependencyManager<ZProperty>((ZIDependencyManaged<ZProperty>) form);
    this.inputProvider = new ZChangedPropertiesFromParameter(mirr, parameterName);
  }


  public ZIDependencyContext<ZProperty> updateDependencies() throws Exception
  {
    Set<ZProperty> changed = inputProvider.getChangedProperties();
    return dependencyManager.process(changed);
  }
  
  public void execIn()
  {
    // TODO Auto-generated method stub
    
  }

  public void execOut()
  {
    // TODO Auto-generated method stub
    
  }

  public void execute() throws Exception
  {
    assign();

    dependencyContext = updateDependencies();

    update();

    revalidate();
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
    ZDependencyGraphMLWriter.toGraphML(dependencyManager,
        dependencyContext,
        new ZINodeLabeler<ZProperty>()
        {
          public String getLabel(ZProperty node)
          {
            return node.getName();
          }
        },
        w);
  }
}
