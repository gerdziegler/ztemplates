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
package org.ztemplates.examples.formprocessing.layers.active.logic;

import java.util.Set;

import org.json.JSONObject;
import org.ztemplates.examples.formprocessing.layers.active.actions.AutocompleteAction;
import org.ztemplates.examples.formprocessing.layers.active.actions.SampleAJAXFormAction;
import org.ztemplates.examples.formprocessing.layers.passive.ui.views.SampleForm;
import org.ztemplates.examples.formprocessing.layers.passive.ui.views.SampleFormView;
import org.ztemplates.examples.formprocessing.layers.passive.ui.views.confirm.ConfirmView;
import org.ztemplates.property.ZProperty;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.ui.form.script.ZFormScript;

public class ViewFactory
{
  public void showSampleForm(SampleForm form, Set<ZProperty> ajaxProperties) throws Exception
  {
    String ajaxUrl = SampleAJAXFormAction.createUrl();
    Set<String> ajaxPropertyNames = ZFormScript.getPropertyNames(ajaxProperties);
    String autocompleteQueryUrl = AutocompleteAction.createUrl();
    JSONObject autocompleteQuerySchema = AutocompleteAction.getSchema();
    SampleFormView view = new SampleFormView(form,
        ajaxUrl,
        ajaxPropertyNames,
        autocompleteQueryUrl,
        autocompleteQuerySchema);

    ZTemplates.getServletService().render(view);
  }


  public void showSampleFormConfirm() throws Exception
  {
    ConfirmView confirmView = new ConfirmView();
    ZTemplates.getServletService().render(confirmView);
  }
}
