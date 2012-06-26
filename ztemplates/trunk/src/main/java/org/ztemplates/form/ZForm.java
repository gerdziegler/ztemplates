package org.ztemplates.form;

import org.ztemplates.form.impl.ZFormWrapper;

public abstract class ZForm<T extends ZIForm> implements ZINamedFormElement
{
  private String name;

  private T form;

  private final boolean enforceNaming;


  public abstract T createForm();


  public ZForm(String name,
      T form,
      boolean enforceNaming)
  {
    this.name = name;
    this.form = form;
    this.enforceNaming = enforceNaming;
    rename();
  }


  public ZForm(String name,
      boolean enforceNaming)
  {
    this(name, null, true);
  }


  public ZForm(boolean enforceNaming)
  {
    this(null, null, true);
  }


  public ZForm(String name)
  {
    this(name, null, true);
  }


  public ZForm(String name,
      T form)
  {
    this(name, form, true);
  }


  public ZForm()
  {
    this(null, null, true);
  }


  private void rename()
  {
    if (name == null || form == null)
    {
      return;
    }
    ZFormWrapper wrapper = new ZFormWrapper(form, name, enforceNaming);
  }


  public String getName()
  {
    return name;
  }


  public void setName(String name)
  {
    this.name = name;
    rename();
  }


  public T getForm()
  {
    return form;
  }


  public void setForm(T form)
  {
    this.form = form;
    rename();
  }
}