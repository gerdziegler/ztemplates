package org.ztemplates.form;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

import org.ztemplates.form.impl.ZFormWrapper;

/**
 * 
 * @author gerd
 *
 * @param <T>
 */
public abstract class ZFormMap<T extends ZIForm> implements SortedMap<String, T>, ZINamedFormElement
{
  private final SortedMap<String, T> forms;

  private String name;

  private boolean enforceNaming = true;

  private int keyWidth = 3;


  public abstract T createForm(String name);


  public ZFormMap(String name,
      final Comparator<T> valueComparator)
  {
    this.name = name;
    if (valueComparator == null)
    {
      this.forms = new TreeMap<String, T>();
    }
    else
    {
      Comparator<String> keyComp = new Comparator<String>()
      {
        @Override
        public int compare(String o1, String o2)
        {
          T val1 = get(o1);
          T val2 = get(o1);
          return valueComparator.compare(val1, val2);
        }
      };
      this.forms = new TreeMap<String, T>(keyComp);
    }
  }


  public ZFormMap(String name)
  {
    this(name, null);
  }


  public ZFormMap()
  {
    this(null, null);
  }


  public ZFormMap(Comparator<T> valueComparator)
  {
    this(null, valueComparator);
  }


  public String getName()
  {
    return name;
  }


  public void setName(String name)
  {
    this.name = name;
    rename(forms);
  }


  public int getKeyWidth()
  {
    return keyWidth;
  }


  public void setKeyWidth(int keyWidth)
  {
    this.keyWidth = keyWidth;
  }


  public void setEnforceNaming(boolean enforceNaming)
  {
    this.enforceNaming = enforceNaming;
  }


  public boolean isEnforceNaming()
  {
    return enforceNaming;
  }


  /**
   * utility for creating sortable indexes, default width
   * @param name
   * @param index
   * @return
   */
  public String createKey(Integer index)
  {
    return createKey("", index);
  }


  public T putCreateKey(Integer index, T value)
  {
    return put(createKey(index), value);
  }


  public String createKey(Long index)
  {
    return createKey("", index);
  }


  public T putCreateKey(Long index, T value)
  {
    return put(createKey(index), value);
  }


  /**
   * utility for creating sortable indexes
   * @param name
   * @param index
   * @return
   */
  public String createKey(String name, Integer index)
  {
    return name + String.format("%1$0" + keyWidth + "d", index);
  }


  public T putCreateKey(String name, Integer index, T value)
  {
    return put(createKey(name, index), value);
  }


  public String createKey(String name, Long index)
  {
    return name + String.format("%1$0" + keyWidth + "d", index);
  }


  public T putCreateKey(String name, Long index, T value)
  {
    return put(createKey(name, index), value);
  }


  private void rename(Map<? extends String, ? extends T> m)
  {
    if (name == null)
    {
      return;
    }
    for (Map.Entry<? extends String, ? extends T> en : m.entrySet())
    {
      String key = en.getKey();
      T form = en.getValue();
      rename(key, form);
    }
  }


  private void rename(String key, T form)
  {
    if (name == null)
    {
      return;
    }
    String newPrefix = name + ZFormWrapper.PROP_SEPARATOR + key;
    ZFormWrapper wrapper = new ZFormWrapper(form, newPrefix, enforceNaming);
  }


  //---------------------------------------------------------------------------------------------------------------
  //--- MAP MODIFY -----------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------------------------

  @Override
  public T put(String key, T value)
  {
    rename(key, value);
    return forms.put(key, value);
  }


  @Override
  public T remove(Object key)
  {
    return forms.remove(key);
  }


  @Override
  public void putAll(Map<? extends String, ? extends T> m)
  {
    rename(m);
    forms.putAll(m);
  }


  @Override
  public void clear()
  {
    forms.clear();
  }


  //---------------------------------------------------------------------------------------------------------------
  //--- MAP READ -------------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------------------------

  @Override
  public int size()
  {
    return forms.size();
  }


  @Override
  public boolean isEmpty()
  {
    return forms.isEmpty();
  }


  @Override
  public boolean containsKey(Object key)
  {
    return forms.containsKey(key);

  }


  @Override
  public boolean containsValue(Object value)
  {
    return forms.containsValue(value);
  }


  @Override
  public T get(Object key)
  {
    return forms.get(key);
  }


  @Override
  public Set<String> keySet()
  {
    return forms.keySet();
  }


  @Override
  public Collection<T> values()
  {
    return forms.values();
  }


  @Override
  public Set<java.util.Map.Entry<String, T>> entrySet()
  {
    return forms.entrySet();
  }


  @Override
  public Comparator<? super String> comparator()
  {
    return forms.comparator();
  }


  @Override
  public SortedMap<String, T> subMap(String fromKey, String toKey)
  {
    return forms.subMap(fromKey, toKey);
  }


  @Override
  public SortedMap<String, T> headMap(String toKey)
  {
    return forms.headMap(toKey);
  }


  @Override
  public SortedMap<String, T> tailMap(String fromKey)
  {
    return forms.tailMap(fromKey);
  }


  @Override
  public String firstKey()
  {
    return firstKey();
  }


  @Override
  public String lastKey()
  {
    return lastKey();
  }
}
