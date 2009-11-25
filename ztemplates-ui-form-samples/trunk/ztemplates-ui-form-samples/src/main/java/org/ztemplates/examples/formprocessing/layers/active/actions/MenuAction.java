package org.ztemplates.examples.formprocessing.layers.active.actions;

import org.apache.log4j.Logger;
import org.ztemplates.actions.ZIAction;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.examples.formprocessing.layers.passive.ui.views.menu.MenuView;
import org.ztemplates.web.ZTemplates;

/**
 * 
 * @author www.gerdziegler.de
 */
@ZMatch(value = "/menu")
public class MenuAction implements ZIAction
{
  static final Logger log = Logger.getLogger(MenuAction.class);


  /**
   * private constructor, as there is no need to instantiate this class from 
   * application code, only ztemplates does it.
   */
  private MenuAction()
  {
  }


  /**
   * to create url to this action define one or many static createUrl methods here.  
   */
  public static String createUrl()
  {
    MenuAction act = new MenuAction();
    return ZTemplates.getServletService().createUrl(act);
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
    MenuView menuView = new MenuView();
    ZTemplates.getServletService().render(menuView);
  }
}
