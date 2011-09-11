package org.ztemplates.web.script.zscript;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ZJavaScriptRepository implements Serializable, ZIJavaScriptRepository
{
  private static final long serialVersionUID = 1L;

  private final Map<String, ZIJavaScriptDefinition> definitions = new HashMap<String, ZIJavaScriptDefinition>();


  public Map<String, ZIJavaScriptDefinition> getDefinitions()
  {
    return definitions;
  }
}