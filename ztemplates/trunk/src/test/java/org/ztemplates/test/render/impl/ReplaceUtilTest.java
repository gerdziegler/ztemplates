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
 * 09.03.2008
 * @author www.gerdziegler.de
 */
package org.ztemplates.test.render.impl;

import java.util.HashMap;
import java.util.Map;

import junit.framework.TestCase;

import org.ztemplates.render.impl.ZReplaceUtil;

public class ReplaceUtilTest extends TestCase
{
  public void test1() throws Exception
  {
    StringBuilder in = new StringBuilder("${var}");
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("var", "katzeklo");
    String ret = ZReplaceUtil.replace(in, map);
    assertEquals(ret, "katzeklo", ret);
  }


  public void test1_3() throws Exception
  {
    StringBuilder in = new StringBuilder("${var}");
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("var", "k");
    String ret = ZReplaceUtil.replace(in, map);
    assertEquals(ret, "k", ret);
  }


  public void test1_1() throws Exception
  {
    StringBuilder in = new StringBuilder("${var}");
    Map<String, Object> map = new HashMap<String, Object>();
    String ret = ZReplaceUtil.replace(in, map);
    assertEquals(ret, "${var}", ret);
  }


  public void test12() throws Exception
  {
    StringBuilder in = new StringBuilder("${}");
    Map<String, Object> map = new HashMap<String, Object>();
    String ret = ZReplaceUtil.replace(in, map);
    assertEquals(ret, "${}", ret);
  }


  public void test2() throws Exception
  {
    StringBuilder in = new StringBuilder("--$--${var}--$-}");
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("var", "katzeklo");
    String ret = ZReplaceUtil.replace(in, map);
    assertEquals(ret, "--$--katzeklo--$-}", ret);
  }


  public void test3() throws Exception
  {
    StringBuilder in = new StringBuilder("--$--$-{var}--$-}");
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("var", "katzeklo");
    String ret = ZReplaceUtil.replace(in, map);
    assertEquals(ret, "--$--$-{var}--$-}", ret);
  }


  public void test4() throws Exception
  {
    StringBuilder in = new StringBuilder("--$--${var{--$-{");
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("var", "katzeklo");
    String ret = ZReplaceUtil.replace(in, map);
    assertEquals(ret, "--$--${var{--$-{", ret);
  }


  public void test5() throws Exception
  {
    StringBuilder in = new StringBuilder("--$--${var{--$-}");
    Map<String, Object> map = new HashMap<String, Object>();
    String ret = ZReplaceUtil.replace(in, map);
    assertEquals(ret, "--$--${var{--$-}", ret);
  }
}
