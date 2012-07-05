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

import java.util.List;

import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;

public class ZFormMembers
{
  private final List<ZProperty> properties;

  private final List<ZOperation> operations;

  private final List<ZFormMap> lists;


  public ZFormMembers(List<ZProperty> properties,
      List<ZOperation> operations,
      List<ZFormMap> lists)
  {
    super();
    this.properties = properties;
    this.operations = operations;
    this.lists = lists;
  }


  public List<ZProperty> getProperties()
  {
    return properties;
  }


  public List<ZOperation> getOperations()
  {
    return operations;
  }


  public List<ZFormMap> getLists()
  {
    return lists;
  }

}
