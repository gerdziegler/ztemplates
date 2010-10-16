package org.ztemplates.form;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Apply this to a form getter in a action pojo to persist the form state (the
 * String values) to the http session under the specified key.
 * 
 * @author gerdziegler.de
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
{
  ElementType.METHOD
})
public @interface ZFormStateManaged
{
  String sessionKey();
}
