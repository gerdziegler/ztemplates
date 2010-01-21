/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * @author www.gerdziegler.de
 */
package org.ztemplates.render.impl;

import java.util.List;
import java.util.Map;
import java.util.SortedSet;

import org.ztemplates.render.ZIRenderEngine;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.script.ZCssExposed;
import org.ztemplates.render.script.ZJavaScriptExposed;

public interface ZIRenderContext
{
  public List<ZExposedMethod> getExposedMethods(Class clazz) throws Exception;


  public ZIRenderer getRenderer(Class<? extends ZIRenderer> clazz) throws Exception;


  public ZICssIdRepository getCssIdRepository();


  public String getContextPath();


  public String getScriptExposedBy();


  public void setScriptExposedBy(String scriptExposedBy);


  public String computeHtmlScriptTags() throws Exception;


  public void registerScripts(Object obj, Map<String, Object> exposed) throws Exception;


  public void incRenderCallCounter();


  public int getRenderCallCounter();


  public ZIRenderEngine getRenderEngine(Object obj) throws Exception;


  public SortedSet<ZJavaScriptExposed> getJavaScriptExposed();


  public SortedSet<ZCssExposed> getCssExposed();
}
