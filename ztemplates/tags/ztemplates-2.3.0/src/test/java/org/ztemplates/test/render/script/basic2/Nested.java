package org.ztemplates.test.render.script.basic2;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZScript;

@ZScript(javaScript =
{
    @ZJavaScript("first.js"),
    @ZJavaScript("nested.js?key=${key}")
})
public class Nested
{
  @ZExpose
  public String getKey()
  {
    return "value2";
  }
}
