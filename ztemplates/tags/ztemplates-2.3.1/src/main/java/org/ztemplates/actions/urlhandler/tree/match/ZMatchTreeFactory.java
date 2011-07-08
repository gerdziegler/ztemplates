package org.ztemplates.actions.urlhandler.tree.match;

import java.util.List;

import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTermFactory;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTermList;

public class ZMatchTreeFactory
{
  public ZMatchTree createMatchTree(ZIClassRepository classRepository) throws Exception
  {
    ZMatchTree ret = new ZMatchTree();
    for (Class c : classRepository.getClassesAnnotatedWith(ZMatch.class))
    {
      ZMatch m = (ZMatch) c.getAnnotation(ZMatch.class);
      if (m.value().charAt(0) == '/')
      {
        List<ZTreeTermList> terms = ZTreeTermFactory.expand(classRepository, c);
        for (ZTreeTermList crt : terms)
        {
          ret.add(crt);
        }
      }
    }
    ret.sort();
    return ret;
  }
}