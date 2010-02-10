package org.ztemplates.property;

/**
 * callback listener to be called when operation is called in form submit, replaces call
 * to after() on action pojo if set on operation.
 * 
 * @author gerdziegler.de
 * 
 */
public interface ZIOperationCallback
{
  public void exec() throws Exception;
}
