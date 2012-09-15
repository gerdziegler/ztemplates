package org.ztemplates.test.render.maps;

import java.util.SortedMap;
import java.util.TreeMap;

import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(ZVelocityRenderer.class)
public class MapTestPojo
{
  @ZExpose(render = true)
  final SortedMap<String, Object> articleMap = new TreeMap<String, Object>();

}