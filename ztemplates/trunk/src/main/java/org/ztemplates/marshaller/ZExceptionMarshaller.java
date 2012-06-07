package org.ztemplates.marshaller;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;

/**
 * 
 * @author gerd
 *
 */
public class ZExceptionMarshaller implements ZIMarshaller<Throwable>
{
  public String marshal(Throwable e)
  {
    if (e == null)
    {
      return null;
    }

    ByteArrayOutputStream buff = new ByteArrayOutputStream();
    PrintWriter pw = new PrintWriter(buff);
    e.printStackTrace(pw);
    pw.flush();
    return buff.toString();
  }


  public Throwable unmarshal(String stringValue) throws ZMarshallerException
  {
    if (stringValue == null || stringValue.length() == 0)
    {
      return null;
    }
    throw new ZMarshallerException("exception parsing not available");
  }
}