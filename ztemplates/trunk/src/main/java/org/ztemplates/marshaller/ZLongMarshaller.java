package org.ztemplates.marshaller;

public class ZLongMarshaller implements ZIMarshaller<Long>
{
  public String marshal(Long obj)
  {
    if (obj == null)
    {
      return null;
    }
    return obj.toString();
  }


  public Long unmarshal(String stringValue) throws ZMarshallerException
  {
    if (stringValue == null || stringValue.length() == 0)
    {
      return null;
    }
    try
    {
      Long i = Long.valueOf(stringValue);
      return i;
    }
    catch (NumberFormatException e)
    {
      throw new ZMarshallerException(stringValue, e);
    }
  }
}