package org.ztemplates.marshaller;

/**
 * marshal/unmarshal values to String.
 * 
 * @author gerd
 *
 * @param <T>
 */
public interface ZIMarshaller<T>
{
  public String marshal(T obj);


  public T unmarshal(String stringValue) throws ZMarshallerException;
}