package org.ztemplates.render;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Map;

import org.ztemplates.render.impl.ZReplaceUtil;

/**
 * Simple, very fast barebones renderer
 * 
 * syntax: 
 * ${exposedPropertyName} 
 * #if(${exposedPropertyName}) 
 * #else 
 * #end
 * #foreach(${name} in ${prop}) 
 * #end
 * 
 * @author www.gerdziegler.de
 * 
 */
public class ZTemplatesRenderer implements ZIRenderer
{
  private ZITemplateNameRepository templateNameRepository;


  public void init(ZIRenderApplicationContext applicationContext, ZITemplateNameRepository templateNameRepository)
  {
    this.templateNameRepository = templateNameRepository;
  }


  public String render(Class clazz, Map<String, Object> values) throws Exception
  {
    String template = templateNameRepository.getTemplateName(clazz) + ".zt";

    StringBuilder sb = new StringBuilder();
    InputStream inStream = clazz.getResourceAsStream(template);
    if (inStream == null && !template.startsWith("/"))
    {
      inStream = clazz.getResourceAsStream("/" + template);
    }
    if (inStream == null)
    {
      throw new Exception("resource not found: " + template);
    }
    try
    {
      Reader in = new BufferedReader(new InputStreamReader(inStream), 4096);
      try
      {
        char[] buff = new char[4096];
        for (int c = in.read(buff); c >= 0; c = in.read(buff))
        {
          sb.append(buff);
        }
      }
      finally
      {
        in.close();
      }
    }
    finally
    {
      inStream.close();
    }
    ZReplaceUtil.merge(sb, values);
    return sb.toString();
  }
}
