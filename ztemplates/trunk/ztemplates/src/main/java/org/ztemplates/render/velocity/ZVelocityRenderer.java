/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de) Licensed under the Apache
 * License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0 Unless required by applicable law
 * or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the specific language
 * governing permissions and limitations under the License. @author
 * www.gerdziegler.de
 */

package org.ztemplates.render.velocity;

import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.ztemplates.render.ZIRenderApplicationContext;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.ZRenderApplication;

public class ZVelocityRenderer implements ZIRenderer
{
  private static final String GLOBAL_PROPERTY_CONFIGURATION = ZVelocityRenderer.class
      .getSimpleName()
      + ".CONFIGURATION";

  protected static Logger log = Logger.getLogger(ZVelocityRenderer.class);

  private ZRenderApplication application;

  private VelocityEngine velocityEngine;


  public static VelocityEngine getVelocityEngine(ZIRenderApplicationContext applicationContext)
      throws Exception
  {
    VelocityEngine cfg = (VelocityEngine) applicationContext
        .getAttribute(ZVelocityRenderer.GLOBAL_PROPERTY_CONFIGURATION);
    if (cfg == null)
    {
      throw new Exception("missing Velocity Configuration (global property '"
          + ZVelocityRenderer.GLOBAL_PROPERTY_CONFIGURATION + "')");
    }
    return cfg;
  }


  public static void setVelocityEngine(ZIRenderApplicationContext applicationContext,
      VelocityEngine cfg) throws Exception
  {
    applicationContext.setAttribute(ZVelocityRenderer.GLOBAL_PROPERTY_CONFIGURATION, cfg);
  }


  public void init(ZRenderApplication application) throws Exception
  {
    this.application = application;
    velocityEngine = getVelocityEngine(application.getApplicationContext());
  }


  public String render(Class clazz, Map<String, Object> values) throws Exception
  {
    String template = application.getTemplateNameRepository().getTemplateName(clazz) + ".vm";
    Template t = velocityEngine.getTemplate(template);
    if (t == null)
    {
      throw new Exception("velocity template not found: " + template);
    }

    VelocityContext vctx = new VelocityContext();
    for (Map.Entry<String, Object> entry : values.entrySet())
    {
      vctx.put(entry.getKey(), entry.getValue());
    }

    StringWriter pw = new StringWriter();
    try
    {
      t.merge(vctx, pw);
    }
    catch (Exception e)
    {
      log.error("error while rendering [" + template + "] - " + e.getMessage());
      throw e;
    }

    return pw.toString();
  }
}
