/*
 * 18.05.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

public class ZInfo extends ZState
{
  private static final long serialVersionUID = 1L;


  public ZInfo(String text)
  {
    super("info", text);
  }


  public ZInfo(Throwable t)
  {
    super("info", t.getLocalizedMessage());
  }
}
