package org.ztemplates.web.ui.form.samples.actions;

import java.util.List;
import java.util.Set;

import org.json.JSONObject;
import org.zdependency.ZIDependencyManager;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.property.ZInfo;
import org.ztemplates.property.ZProperty;
import org.ztemplates.web.ZIFormService;
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
    if (!form.getSubmit().isEmpty())
    {
      onSubmit(form);
    }
    else
    {
      showView(form);
    }
  }


  private void onSubmit(SampleFormElement form) throws Exception
  {
    if (form.getSubmit().isError())
    {
      //maybe do some error handling here
      showView(form);
      return;
    }

    // do submit here, maybe navigate to next view
    form.getSubmit().setState(new ZInfo("OK"));
    showView(form);
  }


  private void showView(SampleFormElement form) throws Exception
  {
    String ajaxUrl = FormTutorialAJAX.createUrl();
    String autocompleteQueryUrl = AutocompleteAction.createUrl();
    JSONObject autocompleteQuerySchema = AutocompleteAction.getSchema();
    ZIDependencyManager<ZProperty> dependencyManager = FormTutorialAJAX
        .createDependencyManager(form);
    Set<String> ajaxPropertyNames = ZFormScript.getPropertyNames(dependencyManager.getTriggerNodes());
    SampleFormView view = new SampleFormView(form,
        ajaxUrl,
        ajaxPropertyNames,
        autocompleteQueryUrl,
        autocompleteQuerySchema);
    ZTemplates.getServletService().render(view);
  }
}
