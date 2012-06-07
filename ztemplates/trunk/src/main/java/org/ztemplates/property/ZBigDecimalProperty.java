/*
 * 11.12.2005
 * @author www.gerdziegler.de
 */
package org.ztemplates.property;

import java.math.BigDecimal;

import org.ztemplates.marshaller.ZBigDecimalMarshaller;

public class ZBigDecimalProperty extends ZProperty<BigDecimal>
{
  public ZBigDecimalProperty()
  {
    super(new ZBigDecimalMarshaller(-1, BigDecimal.ROUND_HALF_UP));
  }


  public ZBigDecimalProperty(String name)
  {
    super(new ZBigDecimalMarshaller(-1, BigDecimal.ROUND_HALF_UP));
    setName(name);
  }


  public ZBigDecimalProperty(int scale,
      int roundingMode)
  {
    super(new ZBigDecimalMarshaller(scale, roundingMode));
  }


  public ZBigDecimalProperty(BigDecimal val)
  {
    super(new ZBigDecimalMarshaller(val.scale(), BigDecimal.ROUND_HALF_UP));
    setValue(val);
  }
}