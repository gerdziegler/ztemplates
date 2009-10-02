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

import org.ztemplates.form.ZIForm;

/**
 * Tagging interface for form actions, makes it more improbable to forget the right way of doing this.
 * It is NOT required for an action to implement this, but its easier to understand 
 * your code if actions implement it.
 * @author www.gerdziegler.de
 */
public interface ZIFormAction<T extends ZIForm> extends ZIAction
{
  public T getForm() throws Exception;


  public void beforeForm() throws Exception;
}
