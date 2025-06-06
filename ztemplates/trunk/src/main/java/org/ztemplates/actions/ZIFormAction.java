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
package org.ztemplates.actions;


/**
 * Tagging interface for form actions with default form, leads to parameter 
 * assignment to form properties without prefix. Although it is NOT required for a form
 * action to implement this interface, it is considered good style to do it, 
 * as it leads to better IDE support and standard names for your form properties.
 * 
 * @author www.gerdziegler.de
 */
public interface ZIFormAction extends ZIAction
{
  //  public void beforeForm() throws Exception;

  //  public T getForm() throws Exception;
}