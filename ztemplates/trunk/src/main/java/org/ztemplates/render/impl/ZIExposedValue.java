package org.ztemplates.render.impl;

import org.ztemplates.render.ZIRenderDecorator;

public interface ZIExposedValue
{

  public abstract String getName();


  public abstract Object getValue(Object obj) throws Exception;


  public abstract boolean isRender();


  public abstract Class<? extends ZIRenderDecorator> getDecorator();

}