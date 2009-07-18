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
package org.ztemplates.render.velocity.codegen;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.apache.velocity.runtime.RuntimeInstance;
import org.apache.velocity.runtime.parser.node.SimpleNode;
import org.apache.velocity.runtime.visitor.NodeViewMode;
import org.ztemplates.render.velocity.ZVelocityRendererFactory;

public class ZVelocityCodeGenTest extends TestCase
{
  static final Logger log = Logger.getLogger(ZVelocityCodeGenTest.class);


  public void test1() throws Exception
  {
    RuntimeInstance ve = ZVelocityRendererFactory.createDefaultRuntimeInstance();
    InputStream in = getClass().getResourceAsStream("/"
        + getClass().getPackage().getName().replace('.', '/') + "/TarifWechselnView.vm");
    SimpleNode root = ve.parse(new InputStreamReader(in), "TarifWechselnView.vm");

//    GenVisitor vis = new GenVisitor();
    
    NodeViewMode vis = new NodeViewMode();

    Map<String, Object> data = new HashMap<String, Object>();
    vis.visit(root, data);
  }
}
