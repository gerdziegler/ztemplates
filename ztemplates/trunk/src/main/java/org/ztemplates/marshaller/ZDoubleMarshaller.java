package org.ztemplates.marshaller;

/**
 * 
 * @author gerd
 *
 */
public class ZDoubleMarshaller implements ZIMarshaller<Double>
{
  public String marshal(Double obj)
  {
    if (obj == null)
    {
      return null;
    }
    return obj.toString();
  }


  public Double unmarshal(String stringValue) throws ZMarshallerException
  {
    if (stringValue == null || stringValue.length() == 0)
    {
      return null;
    }
    try
    {
      Double i = Double.valueOf(stringValue);
      return i;
    }
    catch (NumberFormatException e)
    {
      throw new ZMarshallerException(stringValue, e);
    }
  }
}