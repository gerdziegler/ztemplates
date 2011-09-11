package org.ztemplates.test.render.script.duplicates;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZScript;

@ZScript(javaScript = @ZJavaScript("http://maps.google.com/maps?file=api&v=2&key=${apiKey}"))
public class Nested1
{
  @ZExpose
  public String getApiKey()
  {
    return "katze";
  }
}
