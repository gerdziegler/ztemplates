package org.ztemplates.form.mirr;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.form.ZForm;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;

public class ZFormHolderMirror implements ZIFormMirror, ZIPropertyContainer, ZIOperationContainer, ZIFormContainer, ZIFormHolderContainer
{
  private final ZForm formHolder;

  private final List<ZProperty> properties = new ArrayList<ZProperty>();

  private final List<ZOperation> operations = new ArrayList<ZOperation>();

  private final List<ZFormMirror> forms = new ArrayList<ZFormMirror>();

  private final List<ZFormHolderMirror> formHolders = new ArrayList<ZFormHolderMirror>();


  //  private final List<ZFormListMirror> formLists = new ArrayList<ZFormListMirror>();

  public ZFormHolderMirror(ZForm formHolder)
  {
    super();
    this.formHolder = formHolder;
  }


  public List<ZProperty> getProperties()
  {
    return properties;
  }


  public List<ZOperation> getOperations()
  {
    return operations;
  }


  //
  //  public List<ZFormListMirror> getFormLists()
  //  {
  //    return formLists;
  //  }

  public List<ZFormHolderMirror> getFormHolders()
  {
    return formHolders;
  }


  public List<ZFormMirror> getForms()
  {
    return forms;
  }


  public ZForm getFormHolder()
  {
    return formHolder;
  }
}