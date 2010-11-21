package org.ztemplates.actions.urlhandler.tree.process;

import org.ztemplates.actions.urlhandler.tree.term.ZTreeNestedEnd;

public class ZEndNested implements ZIProcessingInstruction
{
  private final ZTreeNestedEnd nested;


  public ZEndNested(ZTreeNestedEnd nested)
  {
    super();
    this.nested = nested;
  }


  public ZTreeNestedEnd getNested()
  {
    return nested;
  }


  public Class getClazz()
  {
    return nested.getClazz();
  }


  public String getName()
  {
    return nested.getName();
  }

}
