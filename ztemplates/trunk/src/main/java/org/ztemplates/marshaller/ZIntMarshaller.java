package org.ztemplates.marshaller;

public class ZIntMarshaller implements ZIMarshaller<Integer>
{
  public String marshal(Integer obj)
  {
    if (obj == null)
    {
      return null;
    }
    return obj.toString();
  }


  public Integer unmarshal(String stringValue) throws ZMarshallerException
  {
    if (stringValue == null || stringValue.length() == 0)
    {
      return null;
    }
    try
    {
      Integer i = Integer.valueOf(stringValue);
      return i;
    }
    catch (NumberFormatException e)
    {
      throw new ZMarshallerException(stringValue, e);
    }
  }
}