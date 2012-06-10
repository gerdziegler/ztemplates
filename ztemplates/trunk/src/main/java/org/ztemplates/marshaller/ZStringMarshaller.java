package org.ztemplates.marshaller;

/**
 * 
 * @author gerd
 *
 */
public class ZStringMarshaller implements ZIMarshaller<String>
{
  public String marshal(String obj)
  {
    return obj;
  }


  public String unmarshal(String stringValue) throws ZMarshallerException
  {
    return stringValue;
  }
}