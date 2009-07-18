/*
 * 17.07.2006
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import org.ztemplates.json.ZExposeJson;

public class ZOperation extends ZProperty<String>
{
  private String allowedValue;


  public ZOperation(String allowedValue)
  {
    super(allowedValue);
    this.allowedValue = allowedValue;    
  }


  public String parse(String formattedValue) throws Exception
  {
    return formattedValue;
  }


  public String format(String obj)
  {
    return obj;
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
