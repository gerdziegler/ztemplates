package org.ztemplates.form;

public interface ZIdFormFactory<T extends ZIdForm<K>, K>
{
  public T createForm(K id);
}
