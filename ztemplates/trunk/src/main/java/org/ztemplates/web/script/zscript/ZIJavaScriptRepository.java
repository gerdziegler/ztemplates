package org.ztemplates.web.script.zscript;

import java.io.IOException;
import java.util.Map;

public interface ZIJavaScriptRepository
{
  public String getPrefix() throws IOException;


  public Map<String, ZIJavaScriptDefinition> getDefinitions();

}
