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
    @ZCss(value = ZYuiLoaderAction.FONTS_MIN_CSS),
    @ZCss(ZAccordionLoaderAction.LOADER_URL_PREFIX + "/accordion-menu-v2-g.css")
}, javaScript =
{
    @ZJavaScript(ZYuiLoaderAction.YAHOO_DOM_EVENT_JS),
    @ZJavaScript(ZYuiLoaderAction.ANIMATION_MIN_JS),
    @ZJavaScript(ZAccordionLoaderAction.LOADER_URL_PREFIX + "/accordion-menu-v2-g.js")
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
