package org.ztemplates.commons;

import java.lang.reflect.Constructor;

public class ZObjectFactory implements ZIObjectFactory
{
  @Override
  public <T> T newInstance(Class<T> clazz) throws Exception
  {
    Constructor<T> constr = clazz.getDeclaredConstructor();
    T ret;
    if (!constr.isAccessible())
    {
      // disable ReflectPermission("suppressAccessChecks") for this to work
      constr.setAccessible(true);
      ret = constr.newInstance();
    }
    else
    {
      ret = constr.newInstance();
    }
    return ret;
  }
}