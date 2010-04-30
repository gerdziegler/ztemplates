/*
 * Copyright 2006 Gerd Ziegler (www.gerdziegler.de)
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
 *
 * www.gerdziegler.de
 */
package org.ztemplates.form;

/**
 * Forms are required to implement this.
 * Forms are passive objects, they do not access services.
 * Forms keep the user input and validation state.
 * Never keep forms in the servlet session. 
 * If you need to keep the user input between calls use the ZIFormService
 * obtained with ZTemplates.getFormService() to extract the values into a ZFormValues object.
 * 
 * @author www.gerdziegler.de
 */
public interface ZIForm
{
}
