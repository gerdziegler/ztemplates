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
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marker for exposed bean properties. Can be applied to public bean getters and
 * public fields. If render=true the value will be rendered and exposed as a
 * String under the name of the bean property. A second value with name
 * propertyName + "Bean" will be exposed containing the original (unrendered)
 * bean property.
 * 
 * Allowed methods must have one of the following signatures
 * <ul>
 * <li>public void myOperationName(void) throws Exception <br>
 * This kind of operation will be wrapped internally to a ZOperation. Will
 * expose a property with name "myOperationName" and value a ZOperation object.</li>
 * <li>public SomeClass getSomePropertyName(void) <br>
 * Exposes a instance of ZIProperty with name "somePropertyName" to the context.
 * If SomeClass is a ZIProperty the instance returned by the getter call will be
 * used, else a internal wrapper will be created and exposed to the context.</li>
 * <li>public ZOperation getMyOperationName(void) <br>
 * Exposes a ZOperation object with name "myOperationName" to the context.</li>
 * </ul>
 * <p>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(
{
    ElementType.METHOD, ElementType.FIELD
})
public @interface ZExpose
{
  /**
   * set this to true to expose the rendered value to the renderer
   * 
   * @return
   */
  boolean render() default false;


  Class<? extends ZIRenderDecorator> decorator() default ZIRenderDecorator.class;
}
