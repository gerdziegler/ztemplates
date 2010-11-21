package org.ztemplates.render.impl;

import java.util.List;

public interface ZIExposedMethodRepository
{
  public List<ZIExposedValue> getExposedValues(Class clazz) throws Exception;
}
