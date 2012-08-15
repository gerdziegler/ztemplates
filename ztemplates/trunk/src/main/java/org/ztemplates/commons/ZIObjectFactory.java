package org.ztemplates.commons;

/**
 * 
 * @author gerd
 *
 */
public interface ZIObjectFactory
{
  public <T> T newInstance(Class<T> clazz) throws Exception;
}
