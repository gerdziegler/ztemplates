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
package org.ztemplates.form.visitor;

import org.ztemplates.form.ZForm;
import org.ztemplates.form.ZFormList;
import org.ztemplates.form.ZFormMap;
import org.ztemplates.form.ZIForm;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;

public interface ZIFormVisitor
{
  public void before(String fieldName, ZProperty prop);


  public void after(String fieldName, ZProperty prop);


  public void before(String fieldName, ZOperation op);


  public void after(String fieldName, ZOperation op);


  public void before(String fieldName, ZIForm form);


  public void before(String fieldName, ZIForm form, int idx, int cnt);


  public void before(String fieldName, ZForm form);


  public void before(String fieldName, ZFormList<ZIForm> list);


  public void before(String fieldName, ZFormMap<ZIForm> map);


  public void after(String fieldName, ZIForm form, int idx, int cnt);


  public void after(String fieldName, ZIForm form);


  public void after(String fieldName, ZForm form);


  public void after(String fieldName, ZFormList<ZIForm> list);


  public void after(String fieldName, ZFormMap<ZIForm> map);

}