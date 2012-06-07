package org.ztemplates.marshaller;

/**
 * 
 * @author gerd
 *
 */
public class ZBooleanMarshaller implements ZIMarshaller<Boolean>
{
  public String marshal(Boolean obj)
  {
    if (obj == null)
    {
      return null;
    }
    return obj.toString();
  }


  public Boolean unmarshal(String stringValue) throws ZMarshallerException
  {
    if (stringValue == null || stringValue.length() == 0)
    {
      return null;
    }
    try
    {
      Boolean i = Boolean.valueOf(stringValue);
      return i;
    }
    catch (NumberFormatException e)
    {
      throw new ZMarshallerException(stringValue, e);
    }
  }
}