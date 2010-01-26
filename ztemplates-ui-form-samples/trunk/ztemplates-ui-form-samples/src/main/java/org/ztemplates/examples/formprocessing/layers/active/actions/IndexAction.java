package org.ztemplates.examples.formprocessing.layers.active.actions;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIAction;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.web.ZTemplates;

/**
 * 
 * @author www.gerdziegler.de
 */
@ZMatch(value = "/")
public class IndexAction implements ZIAction
{
  static final Logger log = Logger.getLogger(IndexAction.class);


  /**
   * private constructor, as there is no need to instantiate this class from 
   * application code, only ztemplates does it.
   */
  private IndexAction()
  {
  }


  /**
   * to create url to this action define one or many static createUrl methods here.  
   * @throws Exception 
   */
  public static String createUrl() throws Exception
  {
    IndexAction act = new IndexAction();
    return ZTemplates.getActionService().createUrl(act);
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
    String url = SampleFormAction.createUrl();
    ZTemplates.getServletService().sendRedirect(url);
  }
}
