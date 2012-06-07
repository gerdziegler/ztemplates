package org.ztemplates.marshaller;

import java.math.BigDecimal;

/**
 * 
 * @author gerd
 *
 */
public class ZBigDecimalMarshaller implements ZIMarshaller<BigDecimal>
{
  private int scale = -1;

  private int roundingMode = BigDecimal.ROUND_HALF_UP;


  public ZBigDecimalMarshaller(int scale,
      int roundingMode)
  {
    this.scale = scale;
    this.roundingMode = roundingMode;
  }


  public String marshal(BigDecimal obj)
  {
    if (obj == null)
    {
      return null;
    }
    obj = obj.setScale(scale, roundingMode);
    return obj.toString();
  }


  public BigDecimal unmarshal(String stringValue) throws ZMarshallerException
  {
    if (stringValue == null || stringValue.length() == 0)
    {
      return null;
    }
    try
    {
      BigDecimal i = new BigDecimal(stringValue);
      if (scale >= 0)
      {
        i.setScale(scale, roundingMode);
      }
      return i;
    }
    catch (NumberFormatException e)
    {
      throw new ZMarshallerException(stringValue, e);
    }
  }
}