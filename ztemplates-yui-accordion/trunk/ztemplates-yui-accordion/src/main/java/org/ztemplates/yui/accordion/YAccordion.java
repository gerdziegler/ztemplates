package org.ztemplates.yui.accordion;

import java.util.LinkedList;
import java.util.List;

import org.ztemplates.render.ZCss;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.ZScript;
import org.ztemplates.render.velocity.ZVelocityRenderer;
import org.ztemplates.yui.ZYuiLoaderAction;
import org.ztemplates.yui.accordion.assets.ZAccordionLoaderAction;

@ZRenderer(ZVelocityRenderer.class)
@ZScript(css =
{
    @ZCss(prefix = ZYuiLoaderAction.LOADER_URL_PREFIX, value = ZYuiLoaderAction.FONTS_MIN_CSS),
    @ZCss(prefix = ZAccordionLoaderAction.LOADER_URL_PREFIX, value = "/accordion-menu-v2-g.css")
}, javaScript =
{
    @ZJavaScript(prefix = ZYuiLoaderAction.LOADER_URL_PREFIX, value = ZYuiLoaderAction.YAHOO_DOM_EVENT_JS),
    @ZJavaScript(prefix = ZYuiLoaderAction.LOADER_URL_PREFIX, value = ZYuiLoaderAction.ANIMATION_MIN_JS),
    @ZJavaScript("/accordionloader/accordion-menu-v2-g.js")
})
public class YAccordion
{
  private final List<YAccordionPanel> panels = new LinkedList<YAccordionPanel>();


  public YAccordion()
  {
    super();
  }


  @ZExpose
  public List<YAccordionPanel> getPanels()
  {
    return panels;
  }
}
