/*
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import java.math.BigDecimal;

import org.ztemplates.web.ZTemplates;

public class ZBigDecimalProperty extends ZProperty<BigDecimal>
{
  private int scale = -1;

  private int roundingMode = BigDecimal.ROUND_HALF_UP;


  public ZBigDecimalProperty()
  {
  }


  public ZBigDecimalProperty(String label)
  {
    super(label);
  }


  public ZBigDecimalProperty(int scale, int roundingMode)
  {
    this(null, scale, roundingMode);
  }


  public ZBigDecimalProperty(String label, int scale, int roundingMode)
  {
    super(label);
    this.scale = scale;
    this.roundingMode = roundingMode;
  }


  public ZBigDecimalProperty(BigDecimal val)
  {
    setValue(val);
  }


  public BigDecimal parse(String formattedValue) throws Exception
  {
    try
    {
      BigDecimal i = new BigDecimal(formattedValue);
      if (scale >= 0)
      {
        i.setScale(scale, roundingMode);
      }
      return i;
    }
    catch (NumberFormatException e)
    {
      throw new Exception(ZTemplates.getMessageService().getMessage(ZBigDecimalProperty.class
          .getName(),
          "ParseException"));
    }
  }


  public String format(BigDecimal obj)
  {
    return obj == null ? null : obj.toString();
  }


  public int getScale()
  {
    return scale;
  }
}
