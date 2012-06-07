package org.ztemplates.marshaller;

/**
 * exception that is related to marshalling/unmarshalling of property values
 * 
 * @author gerd
 *
 */
public class ZMarshallerException extends Exception
{

  /**
   * 
   */
  private static final long serialVersionUID = 1L;


  public ZMarshallerException()
  {
    super();
  }


  public ZMarshallerException(String message,
      Throwable cause)
  {
    super(message, cause);
  }


  public ZMarshallerException(String message)
  {
    super(message);
  }


  public ZMarshallerException(Throwable cause)
  {
    super(cause);
  }
}