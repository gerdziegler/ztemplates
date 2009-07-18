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
 * 
 * @author www.gerdziegler.de
 */
package org.ztemplates.render;

import java.util.ArrayList;
import java.util.List;

/**
 * runtime equivalent to @ZScript, use to declare script dependencies at runtime
 * by exposing a property getter that returns this type and setting @ZScript.property to the name of the property.
 * 
 * <p>
 * 
 * <pre>
  @ZScript(property="script")
 
  public ZScriptDependency getScript() {
  ...
 }
 * </pre>
 * <p>
 * @author www.gerdziegler.de
 */
public class ZScriptDependency
{
  private final List<ZJavaScript> javaScript = new ArrayList<ZJavaScript>();

  private final List<ZCss> css = new ArrayList<ZCss>();


  public List<ZJavaScript> getJavaScript()
  {
    return javaScript;
  }


  public List<ZCss> getCss()
  {
    return css;
  }


  public void add(ZScriptDependency script)
  {
    css.addAll(script.getCss());
    javaScript.addAll(script.getJavaScript());
  }


  public void add(ZScript script)
  {
    if (script == null)
    {
      return;
    }

    for (ZCss crt : script.css())
    {
      css.add(crt);
    }

    for (ZJavaScript crt : script.javaScript())
    {
      javaScript.add(crt);
    }
  }
}
