package org.ztemplates.form.mirr;

import java.util.ArrayList;
import java.util.List;

import org.ztemplates.form.ZFormMap;

public class ZFormMapMirror implements ZIFormMirror, ZIFormContainer
{
  private final ZFormMap map;

  private final List<ZFormMirror> forms = new ArrayList<ZFormMirror>();


  public <K> ZFormMapMirror(ZFormMap map)
  {
    super();
    this.map = map;
  }


  public ZFormMap getMap()
  {
    return map;
  }


  public List<ZFormMirror> getForms()
  {
    return forms;
  }
}