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

package org.ztemplates.render.freemarker;

import java.io.StringWriter;
import java.util.Map;

import org.apache.log4j.Logger;
import org.ztemplates.render.ZIRenderApplicationContext;
import org.ztemplates.render.ZIRenderer;
import org.ztemplates.render.ZITemplateNameRepository;

import freemarker.template.Configuration;
import freemarker.template.Template;

public class ZFreeMarkerRenderer implements ZIRenderer
{
  protected static Logger log = Logger.getLogger(ZFreeMarkerRenderer.class);

  private static final String GLOBAL_PROPERTY_CONFIGURATION = ZFreeMarkerRenderer.class
      .getSimpleName()
      + ".CONFIGURATION";

  
  private ZITemplateNameRepository templateNameRepository;

  private Configuration cfg;


  public static Configuration getConfiguration(ZIRenderApplicationContext applicationContext)
      throws Exception
  {
    Configuration cfg = (Configuration) applicationContext
        .getAttribute(ZFreeMarkerRenderer.GLOBAL_PROPERTY_CONFIGURATION);
    if (cfg == null)
    {
      throw new Exception("missing FreeMarker Configuration (global property '"
          + ZFreeMarkerRenderer.GLOBAL_PROPERTY_CONFIGURATION + "')");
    }
    return cfg;
  }


  public static void setConfiguration(ZIRenderApplicationContext applicationContext,
      Configuration cfg) throws Exception
  {
    applicationContext.setAttribute(ZFreeMarkerRenderer.GLOBAL_PROPERTY_CONFIGURATION, cfg);
  }


  public void init(ZIRenderApplicationContext applicationContext, ZITemplateNameRepository templateNameRepository) throws Exception
  {
    this.templateNameRepository = templateNameRepository;
    cfg = ZFreeMarkerRenderer.getConfiguration(applicationContext);
  }


  public String render(Class clazz, Map<String, Object> values) throws Exception
  {

    String template = templateNameRepository.getTemplateName(clazz) + ".ftl";
    Template t = cfg.getTemplate(template);
    if (t == null)
    {
      throw new Exception("FreeMarker template not found: " + template);
    }

    StringWriter pw = new StringWriter();
    try
    {
      t.process(values, pw);
    }
    catch (Exception e)
    {
      log.error("error while rendering [" + template + "] - " + e.getMessage());
      throw e;
    }

    return pw.toString();
  }
}
