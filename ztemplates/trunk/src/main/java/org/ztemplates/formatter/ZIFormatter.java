package org.ztemplates.formatter;


/**
 * formatter to provide display value for e.g. select boxes
 * 
 * @author gerdziegler.de
 *
 * @param <T>
 */
public interface ZIFormatter<T>
{
  public String computeDisplayValue(T value);
}
