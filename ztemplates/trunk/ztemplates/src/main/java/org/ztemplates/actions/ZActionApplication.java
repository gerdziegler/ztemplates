package org.ztemplates.actions;

import org.apache.log4j.Logger;
import org.zclasspath.ZIClassRepository;
import org.ztemplates.actions.urlhandler.tree.match.ZMatchTree;

public class ZActionApplication
{
  private static final Logger log = Logger.getLogger(ZActionApplication.class);

  private final ZIActionApplicationContext applicationContext;

  private final ZMatchTree matchTree;


  public ZActionApplication(ZIActionApplicationContext applicationContext, ZIClassRepository classRepository) throws Exception
  {
    this.applicationContext = applicationContext;
    this.matchTree = new ZMatchTree(classRepository);
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
}
