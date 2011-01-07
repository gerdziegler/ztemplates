package org.ztemplates.actions.urlhandler.tree.process;

public class ZAfterInstruction implements ZIProcessingInstruction
{
  private final Class clazz;


  public ZAfterInstruction(Class clazz)
  {
    super();
    this.clazz = clazz;
  }


  public Class getClazz()
  {
    return clazz;
  }
}
