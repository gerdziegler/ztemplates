package org.ztemplates.examples.formprocessing.layers.active.actions;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIFormAction;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.examples.formprocessing.layers.active.form.SampleFormController;
import org.ztemplates.examples.formprocessing.layers.active.logic.ViewFactory;
import org.ztemplates.examples.formprocessing.layers.passive.ui.views.SampleForm;
import org.ztemplates.web.ZTemplates;

/**
 * implements ZIFormAction, to enable form processing.
 * 
 * @author www.gerdziegler.de
 */
@ZMatch(value = "/form/show")
public class ShowSampleFormAction implements ZIFormAction<SampleForm>
{
  static final Logger log = Logger.getLogger(ShowSampleFormAction.class);

  private SampleForm form;


  /**
   * private constructor, as there is no need to instantiate this class from 
   * application code, only ztemplates does it.
   */
  private ShowSampleFormAction()
  {
  }


  /**
   * to create url to this action define one or many static createUrl methods here.  
   * @throws Exception 
   */
  public static String createUrl() throws Exception
  {
    ShowSampleFormAction act = new ShowSampleFormAction();
    return ZTemplates.getActionService().createUrl(act);
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
    form = new SampleForm();
  }


  @Override
  public SampleForm getForm()
  {
    return form;
  }


  /**
   * the main callback, do the logic here, form has already been assigned.
   *  
   * 
   * @see org.ztemplates.actions.ZIAction#after()
   */
  @Override
  public void after() throws Exception
  {
    SampleFormController controller = new SampleFormController(form);
    controller.loadInitialData();
    controller.updateRequired();
    controller.updateForView();
    ViewFactory views = new ViewFactory();
    views.showSampleForm(form, controller.getAjaxProperties());
  }
}
