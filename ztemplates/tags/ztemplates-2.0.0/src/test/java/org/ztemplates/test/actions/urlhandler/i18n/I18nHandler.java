package org.ztemplates.test.actions.urlhandler.i18n;

import org.ztemplates.actions.ZMatch;

/**
 */
@ZMatch(value = "/test[/${prop}]", parameters =
{
  "param"
})
public class I18nHandler
{
  private String prop;

  private String param;


  public String getProp()
  {
    return prop;
  }


  public void setProp(String param1)
  {
    this.prop = param1;
  }


  public String getParam()
  {
    return param;
  }


  public void setParam(String param)
  {
    this.param = param;
  }
}
