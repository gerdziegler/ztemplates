/*
 * Copyright 2008 Gerd Ziegler (www.gerdziegler.de)
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
package org.ztemplates.render;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Annotation for specifiying informations available about a javascript resource
 * 
 * @return
 */
@Retention(RetentionPolicy.RUNTIME)
public @interface ZJavaScriptLocation
{
  /**
   * url as path relative to the contextPath or absolute beginning with http://
   * pointing to script
   */
  String url();


  /**
   * true means library is minified
   * 
   * @return
   */
  boolean minified() default false;


  /**
   * true means library is obfuscated
   * 
   * @return
   */
  boolean obfuscated() default false;

  // /**
  // * If set to true can be merged with other scripts if possible without
  // * changing the order. reasons for not merging could be resource paths.
  // *
  // * @return
  // */
  // boolean mergeable() default false;
}
