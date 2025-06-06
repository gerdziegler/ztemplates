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

import org.ztemplates.examples.formprocessing.layers.passive.ids.GenderId;
import org.ztemplates.jquery.menu.JQueryAutocomplete;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.ui.form.ZFormBooleanCheckbox;
import org.ztemplates.web.ui.form.ZFormPassword;
import org.ztemplates.web.ui.form.ZFormRadio;
import org.ztemplates.web.ui.form.ZFormText;
import org.ztemplates.web.ui.form.ZFormTextArea;
import org.ztemplates.web.ui.form.state.ZFormInputState;

@ZRenderer(value = ZVelocityRenderer.class)
public final class PersonPanel
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

  private final JQueryAutocomplete occupationAutocomplete;

  private final ZFormText taxRate;

  private final ZFormInputState taxRateState;

  private final ZFormInputState marriedState;

  private final ZFormBooleanCheckbox married;

  private final ZFormBooleanCheckbox noAjax;

  private final ZFormInputState noAjaxState;

  private final ZFormInputState occupationState;


  public PersonPanel(PersonForm data, String autocompleteQueryUrl)
      throws Exception
  {
    contentId = ZTemplates.getRenderService().createJavaScriptId();
    occupationInput = new ZFormText( data.getOccupation());
    nameInput = new ZFormText( data.getName());
    nameState = new ZFormInputState( data.getName());
    prenameInput = new ZFormText( data.getSurname());
    prenameState = new ZFormInputState( data.getSurname());
    dateFromInput = new ZFormText( data.getDateFrom());
    dateFromState = new ZFormInputState( data.getDateFrom());
    dateToInput = new ZFormText( data.getDateTo());
    dateToState = new ZFormInputState(data.getDateTo());
    passwordInput = new ZFormPassword(data.getPassword());
    textInput = new ZFormTextArea(data.getText());
    genderInput = new ZFormRadio<GenderId>(data.getGender());
    genderState = new ZFormInputState(data.getGender());
    occupationState = new ZFormInputState(data.getOccupation());
    occupationAutocomplete = new JQueryAutocomplete(occupationInput.getId(), autocompleteQueryUrl);
    occupationAutocomplete.getProperties().put(JQueryAutocomplete.PROP_minChars_number, 1);
    occupationAutocomplete.getProperties().put(JQueryAutocomplete.PROP_delay_number, 200);
    occupationAutocomplete.getProperties().put(JQueryAutocomplete.PROP_max_number, 12);
    occupationAutocomplete.getProperties().put(JQueryAutocomplete.PROP_scroll_boolean, Boolean.FALSE);
    taxRate = new ZFormText( data.getTaxRate());
    taxRateState = new ZFormInputState(data.getTaxRate());
    married = new ZFormBooleanCheckbox( data.getMarried());
    marriedState = new ZFormInputState(data.getMarried());
    noAjax = new ZFormBooleanCheckbox(data.getNoAjax());
    noAjaxState = new ZFormInputState(data.getNoAjax());
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
  public JQueryAutocomplete getOccupationAutocomplete() throws Exception
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
  public ZFormBooleanCheckbox getMarried()
  {
    return married;
  }


  @ZExpose(render = true)
  public ZFormInputState getMarriedState() throws Exception
  {
    return marriedState;
  }


  @ZExpose(render = true)
  public ZFormBooleanCheckbox getNoAjax()
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
