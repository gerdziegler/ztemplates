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
public @interface ZJavaScriptLibrary
{
  /**
   * id of this library, identifies the library regardless of the version
   * 
   * @return
   */
  ZJavaScriptId id();


  /**
   * locations where this library can be found
   * 
   * @return
   */
  ZJavaScriptLocation[] locations();


  /**
   * If set to true will be considerered as having no dependency to other
   * javascript files, so order is not important
   * 
   * @return
   */
  boolean standalone() default false;


  /**
   * libraries this lib depends on, if known
   * 
   * @return
   */
  ZJavaScriptId[] dependsOn() default
  {};
}
