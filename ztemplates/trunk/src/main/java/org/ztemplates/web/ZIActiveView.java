package org.ztemplates.web;

/**
 * interface for render pojos that need to do some processing before rendering.
 * Typically used for active render pojos that access services and contain logic.
 * Drawback: dependendcy to services and tied to logic.
 * Advantage: can be included in other pojos without need to change actions that
 * create surrounding pojo. 
 * @author gerdziegler.de
 *
 */
public interface ZIActiveView
{
  public void beforeRendering() throws Exception;
}
