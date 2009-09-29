package org.ztemplates.examples.formprocessing.layers.active.actions;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIFormAction;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.examples.formprocessing.layers.active.form.SampleFormController;
import org.ztemplates.examples.formprocessing.layers.active.logic.ViewFactory;
import org.ztemplates.examples.formprocessing.layers.passive.ui.views.SampleFormModel;
import org.ztemplates.web.ZTemplates;

/**
 * @author www.gerdziegler.de
 */
@ZMatch(value = "/", form = "form")
public class SampleFormAction implements ZIFormAction<SampleFormModel>
{
  static final Logger log = Logger.getLogger(SampleFormAction.class);

  private SampleFormModel form;


  public static String createUrl()
  {
    SampleFormAction act = new SampleFormAction();
    return ZTemplates.getServletService().createUrl(act);
  }


  private SampleFormAction()
  {
  }


  public void after() throws Exception
  {
    if (!form.getSubmit().isEmpty())
    {
      onSubmit();
    }
    else
    {
      onNoSubmit();
    }
  }


  private void onNoSubmit() throws Exception
  {
    ViewFactory views = new ViewFactory();

    SampleFormController controller = new SampleFormController(form);

    //transaction begin
    controller.loadInitialData();
    //transaction end

    controller.update();
    views.showSampleForm(form, controller.getAjaxProperties());
  }


  private void onSubmit() throws Exception
  {
    ViewFactory views = new ViewFactory();
    SampleFormController controller = new SampleFormController(form);
    controller.adjust();
    controller.validate();
    if (!ZTemplates.getFormService().getPropertiesWithError(form).isEmpty())
    {
      //transaction begin
      //handle errors here
      //transaction end

      controller.update();
      views.showSampleForm(form, controller.getAjaxProperties());
    }
    else
    {
      //transaction begin
      controller.updateValues();
      //transaction end

      controller.update();
      views.showSampleFormConfirm();
    }
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
