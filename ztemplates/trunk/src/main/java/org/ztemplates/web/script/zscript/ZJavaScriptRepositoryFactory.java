package org.ztemplates.web.script.zscript;

import org.apache.log4j.Logger;
import org.apache.log4j.lf5.util.StreamUtils;
import org.zclasspath.ZClassPathClass;
import org.zclasspath.ZClassPathScanner;
import org.zclasspath.ZIClassPathElement;
import org.zclasspath.ZIClassPathVisitor;

public class ZJavaScriptRepositoryFactory
{
  private static final Logger log = Logger.getLogger(ZJavaScriptRepositoryFactory.class);


  public ZIJavaScriptRepository createJavaScriptRepository(ZClassPathScanner scanner, final String encoding) throws Exception
  {
    final ZIJavaScriptRepository ret = new ZJavaScriptRepository();
    scanner.setVisitor(new ZIClassPathVisitor()
    {
      public void visitClass(ZClassPathClass classPathClass)
      {
      }


      public void visitResource(ZIClassPathElement elem)
      {
        try
        {
          String suffix = "-zscript.js";
          String elemName = elem.getName();
          if (elemName.endsWith(suffix))
          {
            int idx = elemName.lastIndexOf('/');
            if (idx < 0)
            {
              idx = 0;
            }
            String name = elemName.substring(idx + 1, elemName.length() - suffix.length());
            byte[] bytes = StreamUtils.getBytes(elem.getInputStream());
            String content = new String(bytes, encoding);
            ZJavaScriptDefinition def = new ZJavaScriptDefinition(name, content, encoding);
            ret.getDefinitions().put(name, def);
            // int idx = content.indexOf("@ztemplates");
            // if (idx >= 0)
            // {
            // int varIdx = content.indexOf("var ", idx);
            // if (varIdx >= 0)
            // {
            // varIdx += 4;
            // }
            // int endIdx = content.indexOf("=", varIdx);
            // String name = content.substring(varIdx, endIdx).trim();
            // }
          }
        }
        catch (Exception e)
        {
          log.error(elem, e);
        }
      }
    });

    scanner.scan();

    return ret;
  }

}
