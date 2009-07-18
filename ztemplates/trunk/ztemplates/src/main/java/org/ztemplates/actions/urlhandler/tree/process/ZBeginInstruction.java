package org.ztemplates.actions.urlhandler.tree.process;

public class ZBeginInstruction implements ZIProcessingInstruction
{
  private final Class clazz;


  public ZBeginInstruction(Class clazz)
  {
    super();
    this.clazz = clazz;
  }


  public Class getClazz()
  {
    return clazz;
  }
}
