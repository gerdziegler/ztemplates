/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.json.ZExposeJson;
import org.ztemplates.message.ZMessages;
import org.ztemplates.validation.ZValidator;

public class ZOperation extends ZProperty<String>
{
  private String allowedValue;

  private ZIOperationListener operationListener;


  /**
   * validates the attached validators
   * 
   * @return
   * @throws Exception
   */
  public final ZMessages validate() throws Exception
  {
    ZValidator val = new ZValidator();
    val.getValidators().addAll(getValidators());
    return val.validate();
  }


  public ZOperation(String allowedValue)
  {
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


  public String parse(String s) throws Exception
  {
    return s;
  }


  public String format(String s)
  {
    return s;
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
