/*
 * Copyright 2007 Gerd Ziegler (www.gerdziegler.de)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific
 * language governing permissions and limitations under the License.
 * @author www.gerdziegler.de
 */
package org.ztemplates.render.impl;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.render.ZRenderApplication;
import org.ztemplates.render.ZRenderer;

/**
 * 
 * 
 */
public class ZCssEngine
{
  private static Logger log = Logger.getLogger(ZCssEngine.class);

  private String css;

  private String cssDigest;

  private final ZRenderApplication application;


  public ZCssEngine(ZRenderApplication application) throws Exception
  {
    this.application = application;
    if (!application.getApplicationContext().isDevMode())
    {
      refreshCss();
      refreshCssDigest();
    }
  }


  private void refreshCss() throws Exception
  {
    ZIClassRepository classRepository = application.getClassRepository();
    StringBuffer buff = new StringBuffer();
    for (Class c : classRepository.getClassesAnnotatedWith(ZRenderer.class))
    {
      String css = renderCss(c);

      if (css != null)
      {
        buff.append(css);
        buff.append('\n');
      }
    }
    css = buff.toString();
  }


  private void refreshCssDigest() throws Exception
  {
    MessageDigest md = MessageDigest.getInstance("MD5");
    byte[] digestBytes = md.digest(css.getBytes());
    cssDigest = Base64.encodeBase64String(digestBytes);
  }


  /*
   * (non-Javadoc)
   * 
   * @see org.ztemplates.web.impl.ZICssService#getCss()
   */
  public String getCss() throws Exception
  {
    if (application.getApplicationContext().isDevMode())
    {
      refreshCss();
    }
    return css;
  }


  public String getCssDigest() throws Exception
  {
    if (application.getApplicationContext().isDevMode())
    {
      if (css == null)
      {
        refreshCss();
      }
      refreshCssDigest();
    }
    return cssDigest;
  }


  private String renderCss(Class clazz) throws Exception
  {
    String template = "/" + application.getTemplateNameRepository().getTemplateName(clazz) + ".css";

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    InputStream in = clazz.getResourceAsStream(template);
    if (in == null)
    {
      // log.debug("-- css: " + template);
      // Ok, css not required
      return null;
    }
    log.debug("++ css " + template);
    try
    {
      ZCopyUtil.copy(in, out);
    }
    finally
    {
      in.close();
    }
    out.flush();

    Map<String, Object> map = new HashMap<String, Object>();
    String cssId = application.getCssIdRepository().getCssId(clazz);
    map.put("cssId", cssId);

    String s = out.toString();
    StringBuffer sb = new StringBuffer(s);
    ZReplaceUtil.merge(sb, map);
    return sb.toString();
  }
}
