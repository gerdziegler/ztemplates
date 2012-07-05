package org.ztemplates.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.apache.log4j.Logger;
import org.ztemplates.form.impl.ZFormWrapper;
import org.ztemplates.marshaller.ZIMarshaller;

/**
 * 
 * @author gerd
 *
 * @param <T>
 */
public class ZFormList<T extends ZIdForm<K>, K> implements List<T>, ZINamedFormElement
{
  static final Logger log = Logger.getLogger(ZFormList.class);

  private final List<T> forms = new ArrayList<T>();

  private String name;

  private boolean enforceNaming = true;

  private int keyWidth = 3;

  private ZIdFormFactory<T, K> factory;

  private final ZIMarshaller<K> marshaller;


  public ZFormList(String name,
      ZIdFormFactory<T, K> factory,
      ZIMarshaller<K> marshaller)
  {
    this.name = name;
    this.factory = factory;
    this.marshaller = marshaller;
  }


  public ZFormList(String name,
      ZIMarshaller<K> marshaller)
  {
    this(name, null, marshaller);
  }


  public ZFormList(ZIMarshaller<K> marshaller)
  {
    this(null, null, marshaller);
  }


  public ZFormList(ZIdFormFactory<T, K> factory,
      ZIMarshaller<K> marshaller)
  {
    this(null, factory, marshaller);
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


  public String createKey(Long index)
  {
    return createKey("", index);
  }


  public String createKey(String name, Integer index)
  {
    return name + String.format("%1$0" + keyWidth + "d", index);
  }


  public String createKey(String name, Long index)
  {
    return name + String.format("%1$0" + keyWidth + "d", index);
  }


  private void rename(Collection<? extends T> list)
  {
    if (name == null)
    {
      return;
    }
    for (T form : list)
    {
      rename(form);
    }
  }


  private void rename(T form)
  {
    if (name == null)
    {
      return;
    }
    String newPrefix = name + ZFormWrapper.PROP_SEPARATOR + marshaller.marshal(form.getId());
    ZFormWrapper wrapper = new ZFormWrapper(form, newPrefix, enforceNaming);
  }


  public T getFormWithId(K id)
  {
    for (T t : forms)
    {
      if (id.equals(t.getId()))
      {
        return t;
      }
    }
    return null;
  }


  public ZIdFormFactory<T, K> getFactory()
  {
    return factory;
  }


  public void setFactory(ZIdFormFactory<T, K> factory)
  {
    this.factory = factory;
  }


  public ZIMarshaller<K> getMarshaller()
  {
    return marshaller;
  }


  public boolean containsFormWithId(K id)
  {
    for (T form : forms)
    {
      if (id.equals(form.getId()))
      {
        return true;
      }
    }
    return false;
  }


  //---------------------------------------------------------------------------------------------------------------
  //--- LIST MODIFY -----------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------------------------

  public boolean add(T e)
  {
    boolean ret = forms.add(e);
    rename(e);
    return ret;
  }


  public void add(int index, T element)
  {
    forms.add(index, element);
    rename(element);
  }


  public T remove(int index)
  {
    T ret = forms.remove(index);
    return ret;
  }


  public boolean remove(Object o)
  {
    boolean ret = forms.remove(o);
    return ret;
  }


  public boolean addAll(Collection<? extends T> c)
  {
    boolean ret = forms.addAll(c);
    if (ret)
    {
      rename(c);
    }
    return ret;
  }


  public boolean addAll(int index, Collection<? extends T> c)
  {
    boolean ret = forms.addAll(index, c);
    if (ret)
    {
      rename(c);
    }
    return ret;
  }


  public boolean removeAll(Collection<?> c)
  {
    boolean ret = forms.removeAll(c);
    return ret;
  }


  public boolean retainAll(Collection<?> c)
  {
    boolean ret = forms.retainAll(c);
    return ret;
  }


  public void clear()
  {
    forms.clear();
  }


  public T set(int index, T element)
  {
    T ret = forms.set(index, element);
    rename(element);
    return ret;
  }


  //---------------------------------------------------------------------------------------------------------------
  //--- LIST READONLY ---------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------------------------
  public boolean containsAll(Collection<?> c)
  {
    return forms.containsAll(c);
  }


  public int size()
  {
    return forms.size();
  }


  public boolean isEmpty()
  {
    return forms.isEmpty();
  }


  public boolean contains(Object o)
  {
    return forms.contains(o);
  }


  public Iterator<T> iterator()
  {
    return forms.iterator();
  }


  public Object[] toArray()
  {
    return forms.toArray();
  }


  public <T> T[] toArray(T[] a)
  {
    return (T[]) forms.toArray();
  }


  public T get(int index)
  {
    return forms.get(index);
  }


  public int indexOf(Object o)
  {
    return forms.indexOf(o);
  }


  public int lastIndexOf(Object o)
  {
    return forms.lastIndexOf(o);
  }


  public ListIterator<T> listIterator()
  {
    return forms.listIterator();
  }


  public ListIterator<T> listIterator(int index)
  {
    return forms.listIterator(index);
  }


  public List<T> subList(int fromIndex, int toIndex)
  {
    return forms.subList(fromIndex, toIndex);
  }

}