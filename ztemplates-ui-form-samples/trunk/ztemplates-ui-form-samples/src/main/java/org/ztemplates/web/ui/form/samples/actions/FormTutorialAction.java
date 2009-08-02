package org.ztemplates.web.ui.form.samples.actions;

import java.io.FileWriter;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.json.JSONObject;
import org.zdependency.ZIDependencyManager;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.form.ZDependencyFormWorkflow;
import org.ztemplates.property.ZProperty;
import org.ztemplates.web.ZTemplates;
import org.ztemplates.web.ui.form.samples.id.ContinentId;
import org.ztemplates.web.ui.form.samples.id.CountryId;
import org.ztemplates.web.ui.form.samples.services.ITutorialService;
import org.ztemplates.web.ui.form.samples.services.TutorialServiceLocator;
import org.ztemplates.web.ui.form.samples.views.SampleFormElement;
import org.ztemplates.web.ui.form.samples.views.SampleFormView;
import org.ztemplates.web.ui.form.samples.views.cascading.CascadingFormElementContext;
import org.ztemplates.web.ui.form.script.ZFormScript;

@ZMatch(value = "/")
public class FormTutorialAction
{
  static final Logger log = Logger.getLogger(FormTutorialAction.class);


  public void after() throws Exception
  {
    final ITutorialService service = TutorialServiceLocator.getService();

    //provide infrastructure to the form
    final CascadingFormElementContext cascadingFormElementContext = new CascadingFormElementContext()
    {
      @Override
      public List<CountryId> getCountries(ContinentId continent) throws Exception
      {
        return service.getCountries(continent);
      }
    };

    SampleFormElement form = new SampleFormElement(cascadingFormElementContext);
    //init your form here    
    ZDependencyFormWorkflow<SampleFormElement> workflow = ZFormScript
        .createDependencyFormWorkflow(form);
    workflow.printGraphML(new FileWriter("c:/tmp/test.graphml"));
    workflow.execute();
    workflow.printRuntimeInfo();

    if (form.getSubmit() == workflow.getOperation())
    {
      if (!workflow.getErrors().isEmpty())
      {
        //handle errors here
        log.error("ERRORS");
      }

      //handle submit here, maybe navigate to next view
      showView(workflow);
    }
    else
    {
      //no submit
      showView(workflow);
    }
  }


  private void showView(ZDependencyFormWorkflow<SampleFormElement> workflow) throws Exception
  {
    String ajaxUrl = FormTutorialAJAX.createUrl();
    String autocompleteQueryUrl = AutocompleteAction.createUrl();
    JSONObject autocompleteQuerySchema = AutocompleteAction.getSchema();
    ZIDependencyManager<ZProperty> dependencyManager = workflow.getDependencyManager();
    Set<String> ajaxPropertyNames = ZFormScript.getPropertyNames(dependencyManager
        .getTriggerNodes());
    SampleFormView view = new SampleFormView(workflow.getForm(),
        ajaxUrl,
        ajaxPropertyNames,
        autocompleteQueryUrl,
        autocompleteQuerySchema);
    ZTemplates.getServletService().render(view);
  }
}
