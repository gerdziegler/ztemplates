package org.ztemplates.render.script;

import java.util.SortedSet;

public interface ZICssProcessor
{
  public String computeHtmlTags(SortedSet<ZCssExposed> cssExposed) throws Exception;
}
