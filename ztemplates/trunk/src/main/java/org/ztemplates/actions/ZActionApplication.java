package org.ztemplates.actions;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTreeFactory;
import org.ztemplates.web.request.impl.ZSecureUrlDecoratorImpl;

public class ZActionApplication
{
  private static final Logger log = Logger.getLogger(ZActionApplication.class);

  private final ZIActionApplicationContext applicationContext;

  private final ZMatchTree matchTree;

  private final ZISecureUrlDecorator secureUrlDecorator;


  public ZActionApplication(ZIActionApplicationContext applicationContext,
      ZIClassRepository classRepository) throws Exception
  {
    this.applicationContext = applicationContext;
    ZMatchTreeFactory matchTreeFactory = new ZMatchTreeFactory();
    this.matchTree = matchTreeFactory.createMatchTree(classRepository);
    this.secureUrlDecorator = new ZSecureUrlDecoratorImpl();
    log.info(matchTree.toConsoleString());
  }


  public ZIActionApplicationContext getApplicationContext()
  {
    return applicationContext;
  }


  public ZMatchTree getMatchTree()
  {
    return matchTree;
  }


  public ZISecureUrlDecorator getSecureUrlDecorator()
  {
    return secureUrlDecorator;
  }
}
