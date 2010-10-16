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
package org.ztemplates.test.scriptrepo.repo;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;
import junit.framework.TestCase;

import org.ztemplates.web.script.experimental.repo.ZJavaScriptId;
import org.ztemplates.web.script.experimental.repo.ZJavaScriptInfo;
import org.ztemplates.web.script.experimental.repo.impl.ZJavaScriptRepository;

public class ZScriptRepositoryTest extends TestCase
{
  public void test_jsInfoSort() throws Exception
  {
    ZJavaScriptRepository repo = new ZJavaScriptRepository();

    List<ZJavaScriptInfo> l = new ArrayList<ZJavaScriptInfo>();
    l.add(new ZJavaScriptInfo("2.3", new ZJavaScriptId("lib2", "3.0.0")));
    l.add(new ZJavaScriptInfo("2.1", new ZJavaScriptId("lib2", "1.0.0")));
    l.add(new ZJavaScriptInfo("2.2", new ZJavaScriptId("lib2", "2.0.0")));
    repo.sortJavaScriptInfoList(l);
  }


  public void test_jsUrlOverride() throws Exception
  {
    ZJavaScriptRepository repo = new ZJavaScriptRepository();
    String[] js1 = new String[]
    {
        "1", "2", "3"
    };
    String[] js2 = new String[]
    {
        "1", "2", "4", "3"
    };
    String[] js3 = new String[]
    {
        "1", "2ver2"
    };
    repo.addJavaScript(js1);
    repo.addJavaScript(js2);
    repo.addJavaScript(js3);
    repo.addJavaScriptInfo(new ZJavaScriptInfo("2", new ZJavaScriptId("lib2", "1.0.0")));
    repo.addJavaScriptInfo(new ZJavaScriptInfo("2ver2", new ZJavaScriptId("lib2", "2.0.0")));
    repo.init();
    List<String> used = new ArrayList<String>();
    used.add("4");
    used.add("3");
    used.add("2");
    used.add("1");
    used.add("2ver2");
    List<String> computed = repo.computeJavaScript(used);

    Assert.assertEquals(4, computed.size());
    Assert.assertEquals("1", computed.get(0));
    Assert.assertEquals("2ver2", computed.get(1));
    Assert.assertEquals("4", computed.get(2));
    Assert.assertEquals("3", computed.get(3));
  }


  public void test_jsOrder() throws Exception
  {
    ZJavaScriptRepository repo = new ZJavaScriptRepository();
    String[] js1 = new String[]
    {
        "1", "2", "3"
    };
    String[] js2 = new String[]
    {
        "1", "2", "4", "3"
    };
    repo.addJavaScript(js1);
    repo.addJavaScript(js2);
    repo.addJavaScriptInfo(new ZJavaScriptInfo("2", new ZJavaScriptId("2", "1.0.0"), true));
    repo.init();
    Assert.assertEquals(0, repo.getJavaScriptIndex("2"));
    Assert.assertEquals(1, repo.getJavaScriptIndex("1"));
    Assert.assertEquals(2, repo.getJavaScriptIndex("4"));
    Assert.assertEquals(3, repo.getJavaScriptIndex("3"));
  }

}
