/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.json.ZExposeJson;
import org.ztemplates.marshaller.ZStringMarshaller;

public class ZOperation extends ZProperty<String>
{
  private String allowedValue;

  private ZIOperationListener operationListener;


  public ZOperation()
  {
    super(new ZStringMarshaller());
  }


  public ZOperation(String name,
      String allowedValue)
  {
    super(new ZStringMarshaller());
    setName(name);
    this.allowedValue = allowedValue;
  }


  /**
   * To avoid refactoring errors specify the name as you can now use the
   * after[OperationName] callback in actions
   * 
   * @param allowedValue
   */
  @Deprecated
  public ZOperation(String allowedValue)
  {
    super(new ZStringMarshaller());
    this.allowedValue = allowedValue;
  }


  public ZIOperationListener getOperationListener()
  {
    return operationListener;
  }


  public void setOperationListener(ZIOperationListener callback)
  {
    this.operationListener = callback;
  }


  @ZExposeJson
  public String getAllowedValue()
  {
    return allowedValue;
  }


  public void setAllowedValue(String allowedValue)
  {
    this.allowedValue = allowedValue;
  }
}
