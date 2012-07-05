package org.ztemplates.form;

import org.ztemplates.property.ZPropertyException;

public interface ZIdForm<K> extends ZIForm
{
  public K getId() throws ZPropertyException;
}
