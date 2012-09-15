package org.ztemplates.actions.urlhandler.tree.match;

import java.util.List;

import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.ZMatch;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTermFactory;
import org.ztemplates.actions.urlhandler.tree.term.ZTreeTermList;

public class ZMatchTreeFactory
{
  private final ZIClassRepository classRepository;


  public ZMatchTreeFactory(ZIClassRepository classRepository)
  {
    super();
    this.classRepository = classRepository;
  }


  public void addToMatchTree(ZMatchTree matchTree) throws Exception
  {
    ZTreeTermFactory factory = new ZTreeTermFactory();
    for (Class c : classRepository.getClassesAnnotatedWith(ZMatch.class))
    {
      ZMatch m = (ZMatch) c.getAnnotation(ZMatch.class);
      if (m.value().charAt(0) == '/')
      {
        List<ZTreeTermList> terms = factory.expand(classRepository, c);
        for (ZTreeTermList crt : terms)
        {
          matchTree.add(crt);
        }
      }
    }
    matchTree.sort();
  }
}