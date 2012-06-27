package org.ztemplates.form;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.ztemplates.form.impl.ZFormWrapper;

/**
 * 
 * @author gerd
 *
 * @param <T>
 */
public abstract class ZFormMap<T extends ZIForm> implements Map<String, T>, ZINamedFormElement
{
  private final Map<String, T> forms = new HashMap<String, T>();

  private String name;

  private final boolean enforceNaming;


  public abstract T createForm(String name);


  public ZFormMap(String name,
      boolean enforceNaming)
  {
    this.name = name;
    this.enforceNaming = enforceNaming;
  }


  public ZFormMap(String name)
  {
    this(name, true);
  }


  public ZFormMap()
  {
    this(null, true);
  }


  public ZFormMap(boolean enforceNaming)
  {
    this(null, enforceNaming);
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
}
