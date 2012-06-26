package org.ztemplates.form.mirr;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.form.ZFormList;
import org.ztemplates.form.ZIForm;

public class ZFormListMirror implements ZIFormMirror, ZIFormContainer
{
  private final ZFormList<ZIForm> list;

  private final List<ZFormMirror> forms = new ArrayList<ZFormMirror>();


  public ZFormListMirror(ZFormList<ZIForm> list)
  {
    super();
    this.list = list;
  }


  public ZFormList<ZIForm> getList()
  {
    return list;
  }


  public List<ZFormMirror> getForms()
  {
    return forms;
  }
}