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
package org.ztemplates.examples.formprocessing.layers.passive.ui.views.person;

import org.json.JSONObject;
import org.ztemplates.examples.formprocessing.layers.passive.ids.GenderId;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.ui.form.ZFormCheckbox;
import org.ztemplates.web.ui.form.ZFormPassword;
import org.ztemplates.web.ui.form.ZFormRadio;
import org.ztemplates.web.ui.form.ZFormText;
import org.ztemplates.web.ui.form.ZFormTextArea;
import org.ztemplates.web.ui.form.state.ZFormInputState;
import org.ztemplates.yui.autocomplete.ZFormAutocomplete;

@ZRenderer(value = ZVelocityRenderer.class)
public class PersonPanel
{
  private final String contentId;

  private final ZFormText occupationInput;

  private final ZFormText nameInput;

  private final ZFormInputState nameState;

  private final ZFormText prenameInput;

  private final ZFormInputState prenameState;

  private final ZFormText dateFromInput;

  private final ZFormInputState dateFromState;

  private final ZFormText dateToInput;

  private final ZFormInputState dateToState;

  private final ZFormPassword passwordInput;

  private final ZFormTextArea textInput;

  private final ZFormRadio<GenderId> genderInput;

  private final ZFormInputState genderState;

  private final ZFormAutocomplete occupationAutocomplete;

  private final ZFormText taxRate;

  private final ZFormInputState taxRateState;

  private final ZFormInputState marriedState;

  private final ZFormCheckbox married;

  private final ZFormCheckbox noAjax;

  private final ZFormInputState noAjaxState;

  private final ZFormInputState occupationState;


  public PersonPanel(String formName, PersonForm data, String autocompleteQueryUrl, JSONObject autocompleteQuerySchema) throws Exception
  {
    contentId = ZTemplates.getRenderService().createJavaScriptId();
    occupationInput = new ZFormText(formName, data.getOccupation());
    nameInput = new ZFormText(formName, data.getName());
    nameState = new ZFormInputState(formName, data.getName());
    prenameInput = new ZFormText(formName, data.getSurname());
    prenameState = new ZFormInputState(formName, data.getSurname());
    dateFromInput = new ZFormText(formName, data.getDateFrom());
    dateFromState = new ZFormInputState(formName, data.getDateFrom());
    dateToInput = new ZFormText(formName, data.getDateTo());
    dateToState = new ZFormInputState(formName, data.getDateTo());
    passwordInput = new ZFormPassword(formName, data.getPassword());
    textInput = new ZFormTextArea(formName, data.getText());
    genderInput = new ZFormRadio<GenderId>(formName, data.getGender());
    genderState = new ZFormInputState(formName, data.getGender());
    occupationState = new ZFormInputState(formName, data.getOccupation());
    occupationAutocomplete = new ZFormAutocomplete(occupationInput.getInputId(),
        autocompleteQueryUrl,
        autocompleteQuerySchema);
    occupationAutocomplete.getProperties().put(ZFormAutocomplete.PROP_minQueryLength_int, 3);
    occupationAutocomplete.getProperties().put(ZFormAutocomplete.PROP_forceSelection_bool, true);
    taxRate = new ZFormText(formName, data.getTaxRate());
    taxRateState = new ZFormInputState(formName, data.getTaxRate());
    married = new ZFormCheckbox(formName, data.getMarried());
    marriedState = new ZFormInputState(formName, data.getMarried());
    noAjax = new ZFormCheckbox(formName, data.getNoAjax());
    noAjaxState = new ZFormInputState(formName, data.getNoAjax());
  }


  @ZExpose
  public String getContentId()
  {
    return contentId;
  }


  @ZExpose(render = true)
  public ZFormText getNameInput()
  {
    return nameInput;
  }


  @ZExpose(render = true)
  public ZFormInputState getNameState() throws Exception
  {
    return nameState;
  }


  @ZExpose(render = true)
  public ZFormText getPrenameInput()
  {
    return prenameInput;
  }


  @ZExpose(render = true)
  public ZFormInputState getPrenameState() throws Exception
  {
    return prenameState;
  }


  @ZExpose(render = true)
  public ZFormText getDateFromInput()
  {
    return dateFromInput;
  }


  @ZExpose(render = true)
  public ZFormInputState getDateFromState() throws Exception
  {
    return dateFromState;
  }


  @ZExpose(render = true)
  public ZFormPassword getPasswordInput()
  {
    return passwordInput;
  }


  @ZExpose(render = true)
  public ZFormTextArea getTextInput()
  {
    return textInput;
  }


  @ZExpose(render = true)
  public ZFormRadio<GenderId> getGenderInput() throws Exception
  {
    return genderInput;
  }


  @ZExpose(render = true)
  public ZFormInputState getGenderState() throws Exception
  {
    return genderState;
  }


  @ZExpose(render = true)
  public ZFormText getOccupationInput() throws Exception
  {
    return occupationInput;
  }


  @ZExpose(render = true)
  public ZFormAutocomplete getOccupationAutocomplete() throws Exception
  {
    return occupationAutocomplete;
  }


  @ZExpose(render = true)
  public ZFormInputState getOccupationState() throws Exception
  {
    return occupationState;
  }


  @ZExpose(render = true)
  public ZFormText getTaxRate()
  {
    return taxRate;
  }


  @ZExpose(render = true)
  public ZFormInputState getTaxRateState() throws Exception
  {
    return taxRateState;
  }


  @ZExpose(render = true)
  public ZFormCheckbox getMarried()
  {
    return married;
  }


  @ZExpose(render = true)
  public ZFormInputState getMarriedState() throws Exception
  {
    return marriedState;
  }


  @ZExpose(render = true)
  public ZFormCheckbox getNoAjax()
  {
    return noAjax;
  }


  @ZExpose(render = true)
  public ZFormInputState getNoAjaxState()
  {
    return noAjaxState;
  }


  @ZExpose(render = true)
  public ZFormText getDateToInput()
  {
    return dateToInput;
  }


  @ZExpose(render = true)
  public ZFormInputState getDateToState()
  {
    return dateToState;
  }
}
