package org.ztemplates.test.scriptrepo.component;

import org.ztemplates.web.script.experimental.repo.ZJavaScriptDependencyInfo;
import org.ztemplates.web.script.experimental.repo.ZJavaScriptId;

public class JsDependency2_v1 extends ZJavaScriptDependencyInfo
{
  public static final String NAME = "org.ztemplates.test.scriptrepo.component.JavaScriptResource2";

  public static final String VERSION = "0.0.1";

  public static final ZJavaScriptId ID = new ZJavaScriptId(NAME, VERSION);


  public JsDependency2_v1()
  {
    super(ID);
    getDependsOn().add(JsDependency1_v1.ID);
  }
}
