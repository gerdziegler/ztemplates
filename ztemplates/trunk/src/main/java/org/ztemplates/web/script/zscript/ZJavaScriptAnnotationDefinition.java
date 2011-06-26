package org.ztemplates.web.script.zscript;

import java.io.Serializable;

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
    Object instance = clazz.newInstance();
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
