package org.ztemplates.actions.urlhandler.tree.process;

import org.ztemplates.actions.urlhandler.tree.term.ZTreeNestedBegin;

public class ZBeginNested implements ZIProcessingInstruction
{
  private final ZTreeNestedBegin nested;


  public ZBeginNested(ZTreeNestedBegin nestedBegin)
  {
    super();
    this.nested = nestedBegin;
  }


  public ZTreeNestedBegin getNested()
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


  public Class getNestedClass()
  {
    return nested.getNestedClass();
  }
}
