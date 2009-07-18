package org.ztemplates.test.render.script.duplicates;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZJavaScript;
import org.ztemplates.render.ZScript;

@ZScript(javaScript = @ZJavaScript(value = "http://maps.google.com/maps?file=api&v=2&key=${googleApiKey}", standalone = true))
public class Nested
{
  @ZExpose
  public String getGoogleApiKey()
  {
    return "katze";
  }

}
