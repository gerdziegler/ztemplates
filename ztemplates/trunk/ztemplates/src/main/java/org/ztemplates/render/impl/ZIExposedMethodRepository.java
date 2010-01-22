package org.ztemplates.render.impl;

import java.util.List;

public interface ZIExposedMethodRepository
{
  public List<ZExposedMethod> getExposedMethods(Class clazz) throws Exception;
}
