package org.ztemplates.form;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import org.ztemplates.form.impl.ZFormWrapper;

/**
 * 
 * @author gerd
 *
 * @param <T>
 */
public abstract class ZFormList<T extends ZIForm> implements List<T>, ZINamedFormElement
{
  private List<T> forms = new ArrayList<T>();

  private String name;

  private final boolean enforceNaming;


  public abstract T createForm(int idx, int size);


  public ZFormList(String name,
      boolean enforceNaming)
  {
    this.name = name;
    this.enforceNaming = enforceNaming;
  }


  public ZFormList(String name)
  {
    this(name, true);
  }


  public ZFormList()
  {
    this(null, true);
  }


  public ZFormList(boolean enforceNaming)
  {
    this(null, enforceNaming);
  }


  //  static final char LIST_SEPARATOR = '-';
  //
  //  static final char PROP_SEPARATOR = '_';

  public String getName()
  {
    return name;
  }


  public void setName(String name)
  {
    //    this.lastName = this.name == null ? name : this.name;
    this.name = name;
    rename();
  }


  private void rename()
  {
    if (name == null)
    {
      return;
    }
    int size = forms.size();
    for (int i = 0; i < forms.size(); i++)
    {
      String newPrefix = name + ZFormWrapper.LIST_SEPARATOR + i + ZFormWrapper.LIST_SEPARATOR + size;
      T form = forms.get(i);
      ZFormWrapper wrapper = new ZFormWrapper(form, newPrefix, enforceNaming);

      //      ZFormWrapper wrapper = new ZFormWrapper(form);
      //      ZFormMembers members = wrapper.getFormMembers();
      //      for (ZOperation op : members.getOperations())
      //      {
      //        rename(op, i, size);
      //      }
      //      for (ZProperty prop : members.getProperties())
      //      {
      //        rename(prop, i, size);
      //      }
      //      for (ZFormList formList : members.getLists())
      //      {
      //        rename(formList, i, size);
      //      }
    }
  }


  //  private void rename(ZINamedFormElement prop, int i, int size)
  //  {
  //    String newPrefix = name + ZFormWrapper.LIST_SEPARATOR + i + ZFormWrapper.LIST_SEPARATOR + size + ZFormWrapper.PROP_SEPARATOR;
  //    String oldPropName = prop.getName();
  //    String oldPrefix = lastName + ZFormWrapper.LIST_SEPARATOR;
  //    if (oldPropName.startsWith(oldPrefix))
  //    {
  //      int idx1 = oldPropName.indexOf(ZFormWrapper.LIST_SEPARATOR, oldPrefix.length());
  //      int idx2 = oldPropName.indexOf(ZFormWrapper.LIST_SEPARATOR, idx1);
  //      int idx3 = oldPropName.indexOf(ZFormWrapper.PROP_SEPARATOR, idx2);
  //      oldPropName = oldPropName.substring(idx3 + 1);
  //    }
  //    String newPropName = newPrefix + oldPropName;
  //    prop.setName(newPropName);
  //    return;
  //  }

  //---------------------------------------------------------------------------------------------------------------
  //--- LIST MODIFY -----------------------------------------------------------------------------------------------
  //---------------------------------------------------------------------------------------------------------------

  public boolean add(T e)
  {
    boolean ret = forms.add(e);
    rename();
    return ret;
  }


  public void add(int index, T element)
  {
    forms.add(index, element);
    rename();
  }


  public T remove(int index)
  {
    T ret = forms.remove(index);
    rename();
    return ret;
  }


  public boolean remove(Object o)
  {
    boolean ret = forms.remove(o);
    if (ret)
    {
      rename();
    }
    return ret;
  }


  public boolean addAll(Collection<? extends T> c)
  {
    boolean ret = forms.addAll(c);
    if (ret)
    {
      rename();
    }
    return ret;
  }


  public boolean addAll(int index, Collection<? extends T> c)
  {
    boolean ret = forms.addAll(index, c);
    if (ret)
    {
      rename();
    }
    return ret;
  }


  public boolean removeAll(Collection<?> c)
  {
    boolean ret = forms.removeAll(c);
    if (ret)
    {
      rename();
    }
    return ret;
  }


  public boolean retainAll(Collection<?> c)
  {
    boolean ret = forms.retainAll(c);
    if (ret)
    {
      rename();
    }
    return ret;
  }


  public void clear()
  {
    forms.clear();
  }


  public T set(int index, T element)
  {
    T ret = forms.set(index, element);
    rename();
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
