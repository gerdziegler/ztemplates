package org.ztemplates.web.script.zscript;

import java.io.Serializable;

public class ZJavaScriptDefinition implements Serializable, ZIJavaScriptDefinition
{
  private static final long serialVersionUID = 1L;

  private final String name;

  private final String content;

  private final String encoding;


  public ZJavaScriptDefinition(String name,
      String content,
      String encoding)
  {
    super();
    this.name = name;
    this.content = content;
    this.encoding = encoding;
  }


  /* (non-Javadoc)
   * @see org.ztemplates.web.script.zscript.ZIJavaScriptDefinition#getName()
   */
  public String getName()
  {
    return name;
  }


  /* (non-Javadoc)
   * @see org.ztemplates.web.script.zscript.ZIJavaScriptDefinition#getContent()
   */
  public String getContent()
  {
    return content;
  }


  /* (non-Javadoc)
   * @see org.ztemplates.web.script.zscript.ZIJavaScriptDefinition#getEncoding()
   */
  public String getEncoding()
  {
    return encoding;
  }

}
