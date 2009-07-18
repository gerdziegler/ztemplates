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
package org.ztemplates.web.ui.form.samples.actions;

import java.io.PrintWriter;
import java.util.List;

import org.apache.log4j.Logger;
import org.zdependency.ZIDependencyContext;
import org.zdependency.impl.ZDependencyManager;
import org.zdependency.output.ZINodeLabeler;
import org.zdependency.output.xml.ZDependencyXMLWriter;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.property.ZProperty;
import org.ztemplates.web.ZIFormService;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.ui.form.samples.id.ContinentId;
import org.ztemplates.web.ui.form.samples.id.CountryId;
import org.ztemplates.web.ui.form.samples.services.ITutorialService;
import org.ztemplates.web.ui.form.samples.services.TutorialServiceLocator;
import org.ztemplates.web.ui.form.samples.views.SampleFormElement;
import org.ztemplates.web.ui.form.samples.views.cascading.CascadingFormElementContext;
import org.ztemplates.web.ui.form.script.ZFormScript;

@ZMatch(value = "/ajax")
public class FormTutorialAJAX
{
  private static final Logger log = Logger.getLogger(FormTutorialAJAX.class);


  public static String createUrl()
  {
    FormTutorialAJAX act = new FormTutorialAJAX();
    return ZTemplates.getServletService().createUrl(act);
  }


  private FormTutorialAJAX()
  {
  }

  /**
   * This keeps the business logic. Access services to get your data, then create the view objects.
   * @throws Exception
   */
  public void after() throws Exception
  {    
    final ITutorialService service = TutorialServiceLocator.getService();
    
    CascadingFormElementContext cascadingFormElementContext = new CascadingFormElementContext()
    {
      @Override
      public List<CountryId> getCountries(ContinentId continent) throws Exception
      {
        return service.getCountries(continent);
      }
    };
    
    SampleFormElement form = new SampleFormElement(cascadingFormElementContext);
    ZIFormService formService = ZTemplates.getFormService();
    formService.process(form);    
   
    ZDependencyManager<ZProperty> dependencyManager = createDependencyManager(form);
    ZIDependencyContext<ZProperty> ctx = dependencyManager.process(ZFormScript.getChangedProperties(form));
    formService.update(form);
    ZFormScript.sendAjaxResponse(form);
    
    ZDependencyXMLWriter.toXML(dependencyManager, ctx, new ZINodeLabeler<ZProperty>() {
      @Override
      public String getLabel(ZProperty node)
      {
        return node.getName();
      }
    }, new PrintWriter(System.out));
  }


  public static ZDependencyManager<ZProperty> createDependencyManager(final SampleFormElement form) throws Exception
  {
    ZDependencyManager<ZProperty> dependencies = new ZDependencyManager<ZProperty>(form);    
    return dependencies;
  }
}
