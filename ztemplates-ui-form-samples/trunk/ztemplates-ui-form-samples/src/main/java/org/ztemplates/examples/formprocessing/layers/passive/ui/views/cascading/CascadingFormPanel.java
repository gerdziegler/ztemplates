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
package org.ztemplates.examples.formprocessing.layers.passive.ui.views.cascading;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.web.ui.form.ZFormSelect;
import org.ztemplates.web.ui.form.ZFormText;
import org.ztemplates.web.ui.form.state.ZFormInputState;

@ZRenderer(value = ZVelocityRenderer.class)
public final class CascadingFormPanel
{
  private final ZFormSelect continent;

  private final ZFormInputState continentState;

  private final ZFormSelect country;

  private final ZFormInputState countryState;

  private final ZFormText city;

  private final ZFormInputState cityState;

  private final ZFormInputState continentCountryState;


  public CascadingFormPanel(CascadingForm data)
  {
    this.continent = new ZFormSelect(data.getContinent());
    this.continentState = new ZFormInputState(data.getContinent());
    this.country = new ZFormSelect(data.getCountry());
    this.countryState = new ZFormInputState(data.getCountry());
    this.city = new ZFormText(data.getCity());
    this.cityState = new ZFormInputState(data.getCity());
    this.continentCountryState = new ZFormInputState(data.getContinent(), data.getCountry());
  }


  @ZExpose(render = true)
  public ZFormSelect getContinent() throws Exception
  {
    return continent;
  }


  @ZExpose(render = true)
  public ZFormInputState getContinentState() throws Exception
  {
    return continentState;
  }


  @ZExpose(render = true)
  public ZFormSelect getCountry() throws Exception
  {
    return country;
  }


  @ZExpose(render = true)
  public ZFormInputState getCountryState() throws Exception
  {
    return countryState;
  }


  @ZExpose(render = true)
  public ZFormText getCity()
  {
    return city;
  }


  @ZExpose(render = true)
  public ZFormInputState getCityState() throws Exception
  {
    return cityState;
  }


  @ZExpose(render = true)
  public ZFormInputState getContinentCountryState() throws Exception
  {
    return continentCountryState;
  }

}
