package org.ztemplates.web.script.zscript;

import java.io.Serializable;

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
   * @see org.ztemplates.web.script.zscript.ZIJavaScriptDefinition#getEncoding()
   */
  public String getEncoding()
  {
    return encoding;
  }


  public Class getClazz()
  {
    return clazz;
  }
}
