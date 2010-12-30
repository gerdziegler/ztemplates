/*
 * 17.07.2006 @author www.gerdziegler.de
 */
package org.ztemplates.property;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Deprecated
public abstract class ZListProperty<T> extends ZProperty<List<T>>
{
  private final String separator;


  public ZListProperty()
  {
    this("-");
  }


  public ZListProperty(String separator)
  {
    this.separator = separator;
  }


  protected abstract String formatListElement(T obj);


  protected abstract T parseListElement(String stringValue) throws Exception;


  // @Override
  // public List<T> getValue() throws Exception
  // {
  // List<T> ret = super.getValue();
  // if (ret == null)
  // {
  // ret = new ArrayList<T>();
  // }
  // return ret;
  // }
  //
  //
  // @Override
  // public void setValue(List<T> val)
  // {
  // if (val == null || val.isEmpty())
  // {
  // super.setValue(null);
  // }
  // else
  // {
  // super.setValue(val);
  // }
  // }

  @Override
  public List<T> parse(String formattedValue) throws Exception
  {
    List<T> ret = new ArrayList<T>();
    if (formattedValue == null)
    {
      return ret;
    }
    String[] val = formattedValue.split(separator);
    for (String s : val)
    {
      T parsed = parseListElement(s);
      ret.add(parsed);
    }
    return Collections.unmodifiableList(ret);
  }


  @Override
  public String format(List<T> list)
  {
    if (list.isEmpty())
    {
      return null;
    }
    StringBuffer sb = new StringBuffer();
    boolean appendSeparator = false;
    for (T t : list)
    {
      String val = formatListElement(t);
      if (appendSeparator)
      {
        sb.append(separator);
      }
      else
      {
        appendSeparator = true;
      }
      sb.append(val);
    }
    return sb.toString();
  }


  public String getSeparator()
  {
    return separator;
  }
}
