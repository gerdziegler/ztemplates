package org.ztemplates.test.scriptrepo.component;

import org.ztemplates.web.script.experimental.repo.ZJavaScriptDependencyInfo;
import org.ztemplates.web.script.experimental.repo.ZJavaScriptId;

public class JsDependency1_v2 extends ZJavaScriptDependencyInfo
{
  public static final String NAME = "org.ztemplates.test.scriptrepo.component.JavaScriptResource1";

  public static final String VERSION = "0.0.2";

  public static final ZJavaScriptId ID = new ZJavaScriptId(NAME, VERSION);


  public JsDependency1_v2()
  {
    super(ID);
  }
}
