/*
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property.deprecated;

import java.math.BigDecimal;

@Deprecated
public class ZBigDecimalListProperty extends ZListProperty<BigDecimal>
{
  public ZBigDecimalListProperty()
  {
  }


  public ZBigDecimalListProperty(String separator)
  {
    super(separator);
  }


  @Override
  protected BigDecimal parseListElement(String formattedValue) throws Exception
  {
    BigDecimal i = new BigDecimal(formattedValue);
    return i;
  }


  @Override
  protected String formatListElement(BigDecimal obj)
  {
    return obj == null ? null : obj.toString();
  }
}
