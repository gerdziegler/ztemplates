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


/**
 * default form processing workflow. Lives for one thread/one request.
 * 
 * @author www.gerdziegler.de
 */
public class ZFormWorkflow extends ZFormWorkflowBase
{
  public ZFormWorkflow(ZIFormElement form) throws Exception
  {
    super(form);
  }


  public void process() throws Exception
  {
    init();

    assign();

    update();

    revalidate();
  }
}
