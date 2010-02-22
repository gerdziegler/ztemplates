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
package org.ztemplates.form;

import junit.framework.TestCase;

import org.ztemplates.actions.util.ZSerializeUtil;

public class ZSerializeUtilTest extends TestCase
{
  public void test1() throws Exception
  {
    String input = "das ist das Haus vom Nikölaüß";
    String ser = ZSerializeUtil.serialize(input);
    String deser = (String) ZSerializeUtil.deserialize(ser);
    assertEquals(input, deser);
  }


  public void test2() throws Exception
  {
    String text = "das ist das Haus vom Nikölaüß";
    ZFormUtilTestSerialize input = new ZFormUtilTestSerialize(text);
    String ser = ZSerializeUtil.serialize(input);
    ZFormUtilTestSerialize deser = (ZFormUtilTestSerialize) ZSerializeUtil.deserialize(ser);
    assertEquals(text, deser.getText());
  }
}
