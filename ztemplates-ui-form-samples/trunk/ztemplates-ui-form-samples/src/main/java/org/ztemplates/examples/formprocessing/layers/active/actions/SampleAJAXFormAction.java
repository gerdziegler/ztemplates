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
package org.ztemplates.examples.formprocessing.layers.active.actions;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIFormAction;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.examples.formprocessing.layers.active.form.SampleFormController;
import org.ztemplates.examples.formprocessing.layers.passive.ui.views.SampleFormModel;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.ui.form.script.ZFormScript;

@ZMatch(value = "/ajax", form = "form")
public class SampleAJAXFormAction implements ZIFormAction<SampleFormModel>
{
  private static final Logger log = Logger.getLogger(SampleAJAXFormAction.class);

  private SampleFormModel form;


  public static String createUrl()
  {
    SampleAJAXFormAction act = new SampleAJAXFormAction();
    return ZTemplates.getServletService().createUrl(act);
  }


  private SampleAJAXFormAction()
  {
  }


  /**
   * This keeps the business logic. Access services to get your data, then create the view objects.
   * @throws Exception
   */
  public void after() throws Exception
  {
    SampleFormController controller = new SampleFormController(form);

    controller.adjust();
    controller.validate();
    //begin transaction
    controller.updateValues();
    //end transaction
    controller.update();

    ZFormScript.sendAjaxResponse(form);
  }


  /**
   * always create the form
   * in this callback, so it does
   * not get created in createUrl(); 
   * @throws Exception
   */
  @Override
  public void beforeForm() throws Exception
  {
    form = new SampleFormModel();
  }


  @Override
  public SampleFormModel getForm()
  {
    return form;
  }
}
