/*
 * 18.05.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

public class ZWarn extends ZState
{
  private static final long serialVersionUID = 1L;


  public ZWarn(String text)
  {
    super("warn", text);
  }


  public ZWarn(Throwable t)
  {
    super("warn", t.getLocalizedMessage());
  }
}
