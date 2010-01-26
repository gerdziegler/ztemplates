/*
 * Copyright 2009 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * 
 * @author www.gerdziegler.de
 */
package org.ztemplates.examples.formprocessing.layers.passive.ui.views.menu;

import org.ztemplates.jquery.menu.JMenuBar;
import org.ztemplates.jquery.menu.JMenuItem;
import org.ztemplates.render.ZExpose;
import org.ztemplates.render.ZRenderer;
import org.ztemplates.render.velocity.ZVelocityRenderer;

@ZRenderer(value=ZVelocityRenderer.class, zscript=true)
public final class MenuView
{
  private final JMenuBar menuBar;


  public MenuView() throws Exception
  {
    menuBar = new JMenuBar();
    for (int i = 0; i < 10; i++)
    {
      JMenuItem item = new JMenuItem("item " + i);
      if (i % 2 == 0)
      {
        item.getMenuItems().add(new JMenuItem("nested"));
      }
      menuBar.getMenuItems().add(item);
    }
  }

  @ZExpose(render=true)
  public JMenuBar getMenuBar()
  {
    return menuBar;
  }
  
  
}
