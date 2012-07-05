package org.ztemplates.form.mirr;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.form.ZFormList;

public class ZFormListMirror implements ZIFormMirror, ZIFormContainer
{
  private final ZFormList map;

  private final List<ZFormMirror> forms = new ArrayList<ZFormMirror>();


  public <K> ZFormListMirror(ZFormList map)
  {
    super();
    this.map = map;
  }


  public ZFormList getMap()
  {
    return map;
  }


  public List<ZFormMirror> getForms()
  {
    return forms;
  }
}