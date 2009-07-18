/*
 * 17.07.2006 @author www.gerdziegler.de
 */
package org.ztemplates.property;

public class ZIntListProperty extends ZListProperty<Integer>
{

  public ZIntListProperty(String separator)
  {
    super(separator);
  }


  public ZIntListProperty()
  {
  }


  @Override
  protected Integer parseListElement(String formattedValue) throws Exception
  {
    Integer i = Integer.parseInt(formattedValue);
    return i;
  }


  @Override
  protected String formatListElement(Integer obj)
  {
    return obj.toString();
  }
}
