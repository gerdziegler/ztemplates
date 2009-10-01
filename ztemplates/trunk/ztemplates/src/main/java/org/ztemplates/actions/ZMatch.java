/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * @author www.gerdziegler.de
 */
package org.ztemplates.actions;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * maps a action pojo to a url <a
 * href="http://www.ztemplates.org/Wiki.jsp?page=ZMatch">See here for details.</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
{
  ElementType.TYPE
})
public @interface ZMatch
{
  /**
   * the path matched by this action. <a
   * href="http://www.ztemplates.org/Wiki.jsp?page=ZMatch">See here for details.</a>
   * 
   * @return
   */
  String value();


  /**
   * parameter names that are mapped to this action.
   * 
   * @return
   */
  String[] parameters() default {};


//  /**
//   * parameters are mapped by reflection to the form accessible through a bean
//   * property with this name. Example: form=xxx needs a getter getXxx()
//   * returning a instance of ZIForm
//   * 
//   * @return
//   */
//   String form() default "";

 
  /**
   * if true, the action handler will not be assigned to the parent action
   * handler variable. Intended to be used for transient events, like toggling
   * the state of tree nodes.
   */
  boolean consume() default false;
}
