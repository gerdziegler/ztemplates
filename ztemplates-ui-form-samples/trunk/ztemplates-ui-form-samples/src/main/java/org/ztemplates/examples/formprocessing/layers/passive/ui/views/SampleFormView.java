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
package org.ztemplates.examples.formprocessing.layers.passive.ui.views;

import java.util.Set;

import org.ztemplates.examples.formprocessing.layers.passive.ui.views.cascading.CascadingFormPanel;
import org.ztemplates.examples.formprocessing.layers.passive.ui.views.person.PersonPanel;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ui.form.ZFormSubmit;
import org.ztemplates.web.ui.form.script.ZFormScript;
import org.ztemplates.web.ui.form.state.ZFormInputState;
import org.ztemplates.web.ui.form.state.ZFormState;
import org.ztemplates.web.ui.form.state.ZFormStateHighlight;

@ZRenderer(value = ZVelocityRenderer.class, zscript = true)
public final class SampleFormView
{
  private final String formId = "sampleForm";

  private final PersonPanel person;

  private final CascadingFormPanel cascading;

  private final ZFormSubmit submit;

  private final ZFormInputState submitState;

  private final ZFormScript formScript;

  private final ZFormState formState;

  private final ZFormStateHighlight formStateHighlight;


  public SampleFormView(SampleForm form, String submitUrl, String ajaxUrl, Set<String> ajaxPropertyNames, String autocompleteQueryUrl) throws Exception
  {
    super();
    form.getPerson().getEnabled().setValue(Boolean.TRUE);
    person = new PersonPanel(form.getPerson(), autocompleteQueryUrl);
    cascading = new CascadingFormPanel(form.getCascading());
    submit = new ZFormSubmit(form.getSubmit());
    submitState = new ZFormInputState(form.getSubmit());
    formScript = new ZFormScript(formId, form, submitUrl, ajaxUrl, ajaxPropertyNames);
    formScript.setBeforeunloadMessage("There are unsubmitted changes.");
    formState = new ZFormState(formId, "formStateDisplay");
    formStateHighlight = new ZFormStateHighlight(formState);
  }


  @ZExpose(render = true)
  public ZFormScript getFormScript()
  {
    return formScript;
  }


  @ZExpose(render = true)
  public ZFormState getFormState()
  {
    return formState;
  }


  @ZExpose(render = true)
  public PersonPanel getPerson()
  {
    return person;
  }


  @ZExpose(render = true)
  public CascadingFormPanel getCascading()
  {
    return cascading;
  }


  @ZExpose(render = true)
  public ZFormSubmit getSubmit()
  {
    return submit;
  }


  @ZExpose(render = true)
  public ZFormInputState getSubmitState() throws Exception
  {
    return submitState;
  }


  @ZExpose
  public String getFormId()
  {
    return formId;
  }


  @ZExpose(render = true)
  public ZFormStateHighlight getFormStateHighlight()
  {
    return formStateHighlight;
  }
}
