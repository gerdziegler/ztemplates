package org.ztemplates.formatter;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author gerdziegler.de
 *
 * @param <T>
 */
public class ZMapFormatter<T> implements ZIFormatter<T>
{
  private final Map<T, String> displayValues = new HashMap<T, String>();

  private String notFoundValue = null;


  public String computeDisplayValue(T value)
  {
    String ret = displayValues.get(value);
    if (ret != null)
    {
      return ret;
    }
    if (!displayValues.containsKey(value))
    {
      return notFoundValue;
    }
    return ret;
  }


  public Map<T, String> getDisplayValues()
  {
    return displayValues;
  }


  public String getNotFoundValue()
  {
    return notFoundValue;
  }


  public void setNotFoundValue(String notFound)
  {
    this.notFoundValue = notFound;
  }

}