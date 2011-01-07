package org.ztemplates.test.render.script.duplicates;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZScript;

@ZScript(javaScript = @ZJavaScript("http://maps.google.com/maps?file=api&v=2&key=${googleApiKey}"))
public class Nested
{
  @ZExpose
  public String getGoogleApiKey()
  {
    return "katze";
  }

}
