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

package org.ztemplates.web;

import org.ztemplates.render.ZIRenderedObject;

public interface ZIRenderService extends ZIService
{
  /**
   * renders the object to a string
   * 
   * @param obj
   */
  public String render(Object obj) throws Exception;


  /**
   * renders a object to a object that can be cached, just expose the returned object with @ZExpose(render=true).
   * @param obj
   * @return
   * @throws Exception
   */
  public ZIRenderedObject prerender(Object obj) throws Exception;


  /**
   * renders ztemplates.css
   * 
   * @return
   * @throws Exception
   */
  public String renderZtemplatesCss() throws Exception;


  /**
   * creates a per request unique id for usage in javascript. 
   * Ids are guaranteed to be unique in request scope as well as for future requests of the same user,
   * so one can safely cache prerendered pojos. 
   */
  public String createJavaScriptId();


  /**
   * computes the cssId for the provided object
   * 
   * @param obj
   * @return
   */
  public String getCssId(Class clazz);

}
