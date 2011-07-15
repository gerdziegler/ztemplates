package org.ztemplates.web.script.zscript;

import java.io.Serializable;

import org.ztemplates.actions.util.impl.ZReflectionUtil;
import org.ztemplates.web.ZTemplates;

public class ZJavaScriptAnnotationDefinition implements Serializable, ZIJavaScriptDefinition
{
  private static final long serialVersionUID = 1L;

  private final String name;

  private final Class clazz;

  private final String encoding;


  public ZJavaScriptAnnotationDefinition(Class clazz,
      String encoding)
  {
    super();
    this.clazz = clazz;
    this.encoding = encoding;
    ZScriptDefinition def = (ZScriptDefinition) clazz.getAnnotation(ZScriptDefinition.class);
    this.name = def.value();
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.script.zscript.ZIJavaScriptDefinition#getName()
   */
  public String getName()
  {
    return name;
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.script.zscript.ZIJavaScriptDefinition#getContent()
   */
  public String getContent() throws Exception
  {
    /*
     * Object instance = clazz.newInstance(); Component comp = (Component)
     * clazz.getAnnotation(Component.class); if (comp != null) {
     * WebApplicationContext ctx = WebApplicationContextUtils
     * .getRequiredWebApplicationContext
     * (ZTemplates.getServletService().getRequest
     * ().getSession().getServletContext()); String name = comp.value(); if
     * (name.length() > 0) { return ctx.getBean(name, clazz); } else { return
     * ctx.getBean(clazz); }
     * 
     * } else { clazz.newInstance(); }
     */
    Object instance = ZReflectionUtil.newInstance(clazz);

    return ZTemplates.getRenderService().render(instance);
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.script.zscript.ZIJavaScriptDefinition#getEncoding()
   */
  public String getEncoding()
  {
    return encoding;
  }
}
