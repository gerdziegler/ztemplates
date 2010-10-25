package org.ztemplates.render.impl;

public interface ZIExposedValue
{

  public abstract String getName();


  public abstract Object getValue(Object obj) throws Exception;


  public abstract boolean isRender();

}