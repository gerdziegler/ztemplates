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
 * by exposing a property getter that returns this type and setting
 * 
 * @ZScript.property to the name of the property.
 * 
 *                   <p>
 * 
 *                   <pre>
 * @ZScript(property="script")
 *  
 *   public ZScriptDependency getScript() {
 *   ...
 *  }
 * </pre>
 *                   <p>
 * 
 * @author www.gerdziegler.de
 */
public class ZScriptDependency {
	private final List<List<String>> javaScript = new ArrayList<List<String>>();

	private final List<List<String>> css = new ArrayList<List<String>>();

	public List<List<String>> getJavaScript() {
		return javaScript;
	}

	public List<List<String>> getCss() {
		return css;
	}

	public void add(ZScriptDependency script) {
		css.addAll(script.getCss());
		javaScript.addAll(script.getJavaScript());
	}

	public void add(ZScript script) {
		if (script == null) {
			return;
		}

		List<String> cssList = new ArrayList<String>();
		for (ZCss crt : script.css()) {
			cssList.add(crt.value());
		}
		if (!cssList.isEmpty()) {
			css.add(cssList);
		}
		List<String> javaScriptList = new ArrayList<String>();
		for (ZJavaScript crt : script.javaScript()) {
			javaScriptList.add(crt.value());
		}
		if (!javaScriptList.isEmpty()) {
			javaScript.add(javaScriptList);
		}
	}
}
