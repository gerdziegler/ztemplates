package org.ztemplates.actions.urlhandler.tree.process;

public class ZSetVariable implements ZIProcessingInstruction
{
  final Class clazz;

  final String name;

  final String value;


  public ZSetVariable(Class clazz, String name, String value)
  {
    super();
    this.clazz = clazz;
    this.name = name;
    this.value = value;
  }


  public Class getClazz()
  {
    return clazz;
  }


  public String getName()
  {
    return name;
  }


  public String getValue()
  {
    return value;
  }
}
