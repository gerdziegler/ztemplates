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
 * href="http://www.ztemplates.org/Wiki.jsp?page=ZMatch">See here for
 * details.</a>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
{
  ElementType.TYPE
})
public @interface ZMatch
{
  public enum Protocol
  {
    HTTP("ztemplates.url.prefix.http"), HTTPS("ztemplates.url.prefix.https"), DEFAULT("");
    private String contextParameterName;


    private Protocol(String contextParameterName)
    {
      this.contextParameterName = contextParameterName;
    }


    public String getContextParameterName()
    {
      return contextParameterName;
    }
  }


  // public static final String METHOD_GET = "GET";
  //
  // public static final String METHOD_HEAD = "HEAD";
  //
  // public static final String METHOD_POST = "POST";
  //
  // public static final String METHOD_PUT = "PUT";
  //
  // public static final String METHOD_DELETE = "DELETE";
  //
  // public static final String METHOD_TRACE = "TRACE";
  //
  // public static final String METHOD_CONNECT = "CONNECT";

  /**
   * the path matched by this action. <a
   * href="http://www.ztemplates.org/Wiki.jsp?page=ZMatch">See here for
   * details.</a>
   * 
   * @return
   */
  String value();


  /**
   * parameter names that are mapped to this action.
   * 
   * @return
   */
  String[] parameters() default
  {};


  /**
   * if set to a protocol creating urls will honor this, 
   * also there will be redirects if url is called from another
   * protocol   
   * @return
   */
  Protocol requiresProtocol() default Protocol.DEFAULT;


  /**
   * Request parameters are mapped by reflection to the form object of type
   * ZIForm accessible through a bean property with name specified in this
   * property. Example: form="xxx" needs a getter getXxx() in the action-pojo
   * returning a instance of type ZIForm.
   * <p>
   * Alternatively implement ZIFormAction in your action-pojo and leave this
   * value blank.
   * <p>
   * You could also do both, implement ZIFormAction and set form="form"
   * <p>
   * Implementing ZIFormAction is NOT required but good style.
   * 
   * @return
   */
  String form() default "";


  /**
   * The HTTP methods this handler should handle. Default is all.
   * 
   * @return
   */
  //  String[] methods() default
  //  {};

  /**
   * if true, the action handler will not be assigned to the parent action
   * handler variable. Intended to be used for transient events, like toggling
   * the state of tree nodes.
   */
  boolean consume() default false;
}
