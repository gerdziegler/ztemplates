/*
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

public class ZExceptionProperty extends ZProperty<Throwable>
{
  public ZExceptionProperty()
  {
  }


  public String format(Throwable t)
  {
    ByteArrayOutputStream buff = new ByteArrayOutputStream();
    PrintWriter pw = new PrintWriter(buff);
    t.printStackTrace(pw);
    pw.flush();
    return buff.toString();
  }


  @Override
  public Throwable parse(String stringValue) throws Exception
  {
    throw new Exception("exception parsing not available");
  }
}
