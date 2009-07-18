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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation for specifiying &lt;sript&gt;> tags that are needed by the
 * template, especially JavaScript needed by Ajax applications. ztemplates
 * collects all declarations and makes them available as "$script" to the
 * template. Duplicates are removed and the order from the ZScript annotations
 * is preserved.
 * 
 * @return
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
{
  ElementType.TYPE
})
public @interface ZScript
{
  /**
   * one or many paths relative to the contextPath or absolute pointing to
   * scripts needed by this view
   */
  ZJavaScript[] javaScript() default {};


  /**
   * one or many paths relative to the contextPath or absolute pointing to
   * scripts needed by this view
   */
  ZCss[] css() default {};


  /**
   * the name of a property of type ZScriptDependency that declares needed scripts at runtime.
   * <br> 
   * example:
   * <p>@ZScript( property="xxx")
   * 
   * <p>public ZScriptDependency getXxx() 
   */
  String property() default "";
}
