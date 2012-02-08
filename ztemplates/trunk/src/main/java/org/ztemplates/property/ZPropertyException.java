package org.ztemplates.property;

/**
 * exception that is related to zproperties 
 * 
 * @author gerd
 *
 */
public class ZPropertyException extends Exception
{
  private final ZProperty[] properties;


  public ZPropertyException(ZProperty... properties)
  {
    super();
    this.properties = properties;
  }


  public ZPropertyException(String message,
      Throwable cause,
      ZProperty... properties)
  {
    super(message, cause);
    this.properties = properties;
  }


  public ZPropertyException(
      String message,
      ZProperty... properties)
  {
    super(message);
    this.properties = properties;
  }


  public ZPropertyException(Throwable cause,
      ZProperty... properties)
  {
    super(cause);
    this.properties = properties;
  }


  public ZProperty[] getProperties()
  {
    return properties;
  }
}
