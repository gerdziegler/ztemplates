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

package org.ztemplates.render;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.ztemplates.web.script.css.ZDefaultCssPreprocessor;
import org.ztemplates.web.script.css.ZICssPreprocessor;
import org.ztemplates.web.script.javascript.ZDefaultJavaScriptGroupingStrategy;
import org.ztemplates.web.script.javascript.ZDefaultJavaScriptPreprocessor;
import org.ztemplates.web.script.javascript.ZIJavaScriptGroupingStrategy;
import org.ztemplates.web.script.javascript.ZIJavaScriptPreprocessor;

/**
 * controls the rendering of this object.
 * <p>
 * The following properties are exposed by default:
 * <p>
 * cssId: a id that can be used in css files, always
 * <p>
 * contextPath: the contextPath for creating urls, always
 * <p>
 * zscript: the css and javascript tags, only if you set zscript=true
 * <p>
 * 
 * 
 * @author gerdziegler
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
public @interface ZRenderer
{
  /**
   * class of renderer to use for rendering this object
   * 
   * @return
   */
  Class<? extends ZIRenderer> value();


  /**
   * encoding to use for rendering this object
   * 
   * @return
   */
  // "ISO-8859-1"
  String encoding() default "";


  /**
   * it set to true a property called "zscript" will be exposed to the template
   * containing the javascript and css html tags.
   * 
   * @return
   */
  boolean zscript() default false;


  /**
   * 
   * @return
   */
  Class<? extends ZIJavaScriptPreprocessor> zscriptJavaScriptPreprocessor() default ZDefaultJavaScriptPreprocessor.class;


  /**
   * 
   * @return
   */
  Class<? extends ZICssPreprocessor> zscriptCssPreprocessor() default ZDefaultCssPreprocessor.class;


  /**
   * 
   * @return
   */
  Class<? extends ZIJavaScriptGroupingStrategy> zscriptJavaScriptGroupingStrategy() default ZDefaultJavaScriptGroupingStrategy.class;

}
