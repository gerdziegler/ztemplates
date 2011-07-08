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


  public ZOperation(String name, String allowedValue)
  {
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
    this.allowedValue = allowedValue;
  }


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
