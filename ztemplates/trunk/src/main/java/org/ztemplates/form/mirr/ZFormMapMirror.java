package org.ztemplates.form.mirr;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.form.ZFormMap;
import org.ztemplates.form.ZIForm;

public class ZFormMapMirror implements ZIFormMirror, ZIFormContainer
{
  private final ZFormMap<ZIForm> map;

  private final List<ZFormMirror> forms = new ArrayList<ZFormMirror>();


  public ZFormMapMirror(ZFormMap<ZIForm> map)
  {
    super();
    this.map = map;
  }


  public ZFormMap<ZIForm> getMap()
  {
    return map;
  }


  public List<ZFormMirror> getForms()
  {
    return forms;
  }
}