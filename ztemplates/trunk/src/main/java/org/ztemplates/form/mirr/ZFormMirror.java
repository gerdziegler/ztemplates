package org.ztemplates.form.mirr;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.form.ZIForm;
import org.ztemplates.property.ZOperation;
import org.ztemplates.property.ZProperty;

public class ZFormMirror implements ZIFormMirror, ZIPropertyContainer, ZIOperationContainer, ZIFormContainer, ZIFormHolderContainer,
    ZIFormMapContainer
{
  private final ZIForm form;

  private final List<ZProperty> properties = new ArrayList<ZProperty>();

  private final List<ZOperation> operations = new ArrayList<ZOperation>();

  private final List<ZFormMirror> forms = new ArrayList<ZFormMirror>();

  private final List<ZFormHolderMirror> formHolders = new ArrayList<ZFormHolderMirror>();

  private final List<ZFormMapMirror> formMaps = new ArrayList<ZFormMapMirror>();


  public ZFormMirror(ZIForm form)
  {
    super();
    this.form = form;
  }


  public ZIForm getForm()
  {
    return form;
  }


  public List<ZProperty> getProperties()
  {
    return properties;
  }


  public List<ZOperation> getOperations()
  {
    return operations;
  }


  public List<ZFormMapMirror> getFormMaps()
  {
    return formMaps;
  }


  public List<ZFormHolderMirror> getFormHolders()
  {
    return formHolders;
  }


  public List<ZFormMirror> getForms()
  {
    return forms;
  }
}